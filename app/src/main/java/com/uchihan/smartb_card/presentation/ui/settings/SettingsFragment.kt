package com.uchihan.smartb_card.presentation.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.uchihan.smartb_card.common.Constants
import com.uchihan.smartb_card.common.Utils
import com.uchihan.smartb_card.databinding.FragmentSettingsBinding
import com.uchihan.smartb_card.presentation.ui.registration.RegistrationActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fbShare.setOnClickListener {
            Utils.shareApp(requireContext(), Constants.APP_LINK)
        }

        binding.fbEdit.setOnClickListener {
            var editIntent = Intent(requireContext(), RegistrationActivity::class.java)
            editIntent.putExtra(Constants.IS_EDIT, true)
            startActivity(editIntent)
        }

        binding.swAllowPublish.isChecked =
            sharedPreferences.getBoolean(Constants.ALLOW_PUBLISH, true)

        binding.swAllowPublish.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                sharedPreferences.edit().putBoolean(Constants.ALLOW_PUBLISH, true).apply()
            } else {
                sharedPreferences.edit().putBoolean(Constants.ALLOW_PUBLISH, false).apply()

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}