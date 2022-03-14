package com.uchihan.smartb_card.presentation.ui.all_cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.databinding.FragmentAllCardsBinding
import com.uchihan.smartb_card.domain.use_case.NearbyUseCase
import com.uchihan.smartb_card.presentation.adapters.AllCardsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class AllCardsFragment : Fragment(), AllCardsAdapter.AllCardsCallbacks {

    private lateinit var cardAdapter: AllCardsAdapter
    private val TAG = "HOME FRAGMENT"
    private lateinit var allCardsViewModel: AllCardsViewModel
    private var _binding: FragmentAllCardsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var nearbyUseCase: NearbyUseCase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var allCardsFactory = AllCardsViewModelFactory(nearbyUseCase)

        allCardsViewModel =
            ViewModelProvider(this, allCardsFactory)[AllCardsViewModel::class.java]

        _binding = FragmentAllCardsBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllCards()

    }

    private fun getAllCards() {
        GlobalScope.launch {
            var all = allCardsViewModel.getAllNearby()

            withContext(Dispatchers.Main) {
                if (all.size > 0)
                    binding.tvNoNear.visibility = View.GONE
                cardAdapter = AllCardsAdapter(requireContext(), all, this@AllCardsFragment)
                binding.rvAllCards.layoutManager = LinearLayoutManager(requireContext())
                binding.rvAllCards.adapter = cardAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun remove(userModel: RoomUserModel) {
        GlobalScope.launch {
            allCardsViewModel.deleteNearby(userModel)
        }
    }
}