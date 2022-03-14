package com.uchihan.smartb_card.presentation.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.nearby.messages.Strategy
import com.uchihan.smartb_card.databinding.FragmentHomeBinding
import com.google.android.gms.nearby.messages.Message
import com.google.android.gms.nearby.messages.MessageListener
import com.uchihan.smartb_card.BuildConfig
import com.uchihan.smartb_card.common.DeviceMessage
import com.google.android.gms.nearby.Nearby
import com.google.android.material.snackbar.Snackbar
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.gms.nearby.messages.PublishCallback

import com.google.android.gms.nearby.messages.PublishOptions

import com.google.android.gms.nearby.messages.SubscribeCallback

import com.google.android.gms.nearby.messages.SubscribeOptions
import com.google.gson.Gson
import com.uchihan.smartb_card.R
import com.uchihan.smartb_card.common.Constants
import com.uchihan.smartb_card.common.Utils
import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.data.models.UserModel
import com.uchihan.smartb_card.domain.use_case.NearbyUseCase
import com.uchihan.smartb_card.presentation.adapters.CardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, CardAdapter.CardsCallbacks {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val cardsList = ArrayList<UserModel>()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var gson: Gson

    private val TAG = HomeFragment::class.java.simpleName

    private val TTL_IN_SECONDS = 3 * 60 // Three minutes.

    private val PUB_SUB_STRATEGY: Strategy = Strategy.Builder()
        .setTtlSeconds(TTL_IN_SECONDS).build()
    private var mGoogleApiClient: GoogleApiClient? = null
    lateinit var savedNearby: ArrayList<RoomUserModel>


    /**
     * The [Message] object used to broadcast information about the device to nearby devices.
     */
    private var mPubMessage: Message? = null

    @Inject
    lateinit var nearbyUseCase: NearbyUseCase

    /**
     * A [MessageListener] for processing messages from nearby devices.
     */
    private var mMessageListener: MessageListener? = null
    private lateinit var cardAdapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var homeViewModelFactory = HomeViewModelFactory(nearbyUseCase)

        homeViewModel =
            ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        GlobalScope.launch {
            savedNearby = homeViewModel.getAllNearby() as ArrayList<RoomUserModel>
            withContext(Dispatchers.Main) {
                cardAdapter =
                    CardAdapter(requireContext(), cardsList, savedNearby, this@HomeFragment)
                binding.rvCards.layoutManager = LinearLayoutManager(requireContext())
                binding.rvCards.adapter = cardAdapter
            }

        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPubMessage = DeviceMessage.newNearbyMessage(
            homeViewModel.getUUID(
                requireContext().getSharedPreferences(
                    BuildConfig.APPLICATION_ID,
                    Context.MODE_PRIVATE
                )
            )!!, sharedPreferences
        )

        mMessageListener = object : MessageListener() {
            override fun onFound(message: Message) {
                // Called when a new message is found.
                Log.i("New Device Found", DeviceMessage.fromNearbyMessage(message).messageBody!!)
                cardsList.forEach { card ->
                    if (card.mobile == gson.fromJson(
                            DeviceMessage.fromNearbyMessage(message).messageBody!!,
                            UserModel::class.java
                        ).mobile
                    ) {
                        return
                    }

                }
                binding.tvNoNear.visibility = View.GONE
                addUSerToList(
                    gson.fromJson(
                        DeviceMessage.fromNearbyMessage(message).messageBody!!,
                        UserModel::class.java
                    )
                )

            }

            override fun onLost(message: Message) {
                // Called when a message is no longer detectable nearby.
                Log.i("Device Lost", DeviceMessage.fromNearbyMessage(message).messageBody!!)

            }
        }

        buildGoogleApiClient()

    }

    private fun addUSerToList(userModel: UserModel) {

        cardsList.add(userModel)
        cardAdapter.notifyDataSetChanged()

    }


    private fun buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            return
        }
        mGoogleApiClient = GoogleApiClient.Builder(requireActivity())
            .addApi(Nearby.MESSAGES_API)
            .addConnectionCallbacks(this)
            .enableAutoManage(requireActivity(), this)
            .build()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

        logAndShowSnackbar(
            "Exception while connecting to Google Play services: " +
                    connectionResult.errorMessage
        )
    }

    override fun onStop() {
        super.onStop()
        mGoogleApiClient?.stopAutoManage(requireActivity());
        mGoogleApiClient?.disconnect();

    }

    override fun onConnectionSuspended(i: Int) {
        logAndShowSnackbar("Connection suspended. Error code: $i")
    }

    override fun onConnected(@Nullable bundle: Bundle?) {
        Log.i(TAG, "GoogleApiClient connected")
        // We use the Switch buttons in the UI to track whether we were previously doing pub/sub (
        // switch buttons retain state on orientation change). Since the GoogleApiClient disconnects
        // when the activity is destroyed, foreground pubs/subs do not survive device rotation. Once
        // this activity is re-created and GoogleApiClient connects, we check the UI and pub/sub
        // again if necessary.
        publish()
        subscribe()

    }

    /**
     * Subscribes to messages from nearby devices and updates the UI if the subscription either
     * fails or TTLs.
     */
    private fun subscribe() {
        Log.i(TAG, "Subscribing")
        val options = SubscribeOptions.Builder()
            .setStrategy(PUB_SUB_STRATEGY)
            .setCallback(object : SubscribeCallback() {
                override fun onExpired() {
                    super.onExpired()
                    Log.i(TAG, "No longer subscribing")
                    requireActivity().runOnUiThread(Runnable {
                    })
                }
            }).build()
        Nearby.Messages.subscribe(mGoogleApiClient!!, mMessageListener!!, options)
            .setResultCallback { status ->
                if (status.isSuccess) {
                    Log.i(TAG, "Subscribed successfully.")
                } else {
                    logAndShowSnackbar("Could not subscribe, status = $status")
                }
            }
    }

    /**
     * Publishes a message to nearby devices and updates the UI if the publication either fails or
     * TTLs.
     */
    private fun publish() {
        if (!sharedPreferences.getBoolean(Constants.ALLOW_PUBLISH, true))
            return
        Log.i(TAG, "Publishing")
        val options = PublishOptions.Builder()
            .setStrategy(PUB_SUB_STRATEGY)
            .setCallback(object : PublishCallback() {
                override fun onExpired() {
                    super.onExpired()
                    Log.i(TAG, "No longer publishing")
                }
            }).build()
        Nearby.Messages.publish(mGoogleApiClient!!, mPubMessage!!, options)
            .setResultCallback { status ->
                if (status.isSuccess) {
                    Log.i(TAG, "Published successfully.")
                } else {
                    logAndShowSnackbar("Could not publish, status = $status")
                }
            }
    }

    /**
     * Stops subscribing to messages from nearby devices.
     */
    private fun unsubscribe() {
        Log.i(TAG, "Unsubscribing.")
        Nearby.Messages.unsubscribe(mGoogleApiClient!!, mMessageListener!!)
    }

    /**
     * Stops publishing message to nearby devices.
     */
    private fun unpublish() {
        Log.i(TAG, "Unpublishing.")
        Nearby.Messages.unpublish(mGoogleApiClient!!, mPubMessage!!)
    }

    /**
     * Logs a message and shows a [Snackbar] using `text`;
     *
     * @param text The text used in the Log message and the SnackBar.
     */
    private fun logAndShowSnackbar(text: String) {
        Log.w(TAG, text)
        val container: View = binding.fragmentHomeContainer
        Snackbar.make(container, text, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun saveToApp(userModel: RoomUserModel) {
        Log.d("user", userModel.name)
        GlobalScope.launch {
            homeViewModel.saveNearby(userModel)
        }

        Snackbar.make(binding.root, getString(R.string.add_to_contacts), Snackbar.LENGTH_SHORT)
            .setAction(getString(R.string.add), View.OnClickListener {
                Utils.addToContacts(requireContext(), userModel)
            })
            .show()
    }


}