package com.uchihan.smartb_card.presentation.ui.registration

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.uchihan.smartb_card.MainActivity
import com.uchihan.smartb_card.R
import com.uchihan.smartb_card.common.Constants
import com.uchihan.smartb_card.data.models.UserModel
import com.uchihan.smartb_card.databinding.ActivityRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var gson: Gson
    private var _binding: ActivityRegistrationBinding? = null
    lateinit var registerViewModel: RegistrationViewModel
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var registrationviewmodelFactory = RegisterViewModelFactory(sharedPreferences, gson)

        registerViewModel =
            ViewModelProvider(this, registrationviewmodelFactory)[RegistrationViewModel::class.java]


        checkIsEdit()

        binding.registerButton.setOnClickListener {

            checkValidity()
        }

    }

    private fun checkIsEdit() {
        if (intent.extras != null && intent.extras!!.containsKey(Constants.IS_EDIT)) {
            var user = registerViewModel.getUserPrefs()
            Log.d("Current user", user.name)
            binding.editTextAddress.setText(user.address)
            binding.editTextEmail.setText(user.email)
            binding.editTextWork.setText(user.work)
            binding.editTextMobile.setText(user.mobile)
            binding.editTextName.setText(user.name)
            binding.swGender.selectedTab = user.gender

        }
    }

    private fun checkValidity() {
        val valid = isInputsValid(
            binding.editTextName.text.toString(),
            binding.editTextMobile.text.toString(),
            binding.editTextEmail.text.toString(),
            binding.editTextWork.text.toString(),
            binding.editTextAddress.text.toString()
        )
        if (valid) {
            binding.registerButton.stopAnimation()

            registerViewModel.saveUserPrefs(
                binding.editTextName.text.toString(),
                binding.editTextMobile.text.toString(),
                binding.editTextEmail.text.toString(),
                binding.editTextWork.text.toString(),
                binding.editTextAddress.text.toString(),
                binding.swGender.selectedTab
            )
            finish()
            startActivity(Intent(this, MainActivity::class.java))

        } else {
            binding.registerButton.stopAnimation()

        }
    }


    private fun isInputsValid(
        name: String,
        mobile: String,
        email: String,
        work: String,
        address: String
    ): Boolean {
        when {
            name.isEmpty() -> {
                binding.textInputName.error = "Please Enter your name"
                return false
            }
            mobile.isEmpty() -> {
                binding.textInputMobile.error = "Please Enter your mobile"
                return false
            }
            email.isEmpty() -> {
                binding.textInputEmail.error = "Please Enter your email"
                return false
            }
            work.isEmpty() -> {
                binding.textInputWork.error = "Please Enter your work"
                return false
            }
            address.isEmpty() -> {
                binding.textInputAddress.error = "Please Enter your Address"
                return false
            }
            else -> return true
        }
    }

}