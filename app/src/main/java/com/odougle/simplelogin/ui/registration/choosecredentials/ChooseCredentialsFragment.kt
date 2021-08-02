package com.odougle.simplelogin.ui.registration.choosecredentials

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.odougle.simplelogin.R
import com.odougle.simplelogin.extensions.dismissError
import com.odougle.simplelogin.ui.login.LoginViewModel
import com.odougle.simplelogin.ui.registration.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_choose_credentials.*
import kotlinx.android.synthetic.main.fragment_profile_data.*

class ChooseCredentialsFragment : Fragment() {

    private val args: ChooseCredentialsFragmentArgs by navArgs()
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_credentials, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        //setando o nome recebido do fragment anterior
        textChooseCredentialsName.text = getString(R.string.choose_credentials_text_name, args.name)

        val invalidFields = initValidationFields()
        listenToRegistrationStateEvent(invalidFields)
        registrationViewListeners()
        registerDeviceBackStack()
    }

    private fun registrationViewListeners() {
        buttonChooseCredentialNext.setOnClickListener {
            val username = inputChooseCredentialsUsername.text.toString()
            val password = inputChooseCredentialsPassword.text.toString()

            registrationViewModel.createCredentials(username, password)
        }

        inputChooseCredentialsUsername.addTextChangedListener {
            inputLayoutChooseCredentialsUsername.dismissError()
        }

        inputChooseCredentialsPassword.addTextChangedListener {
            inputLayoutChooseCredentialsPassword.dismissError()
        }
    }

    private fun registerDeviceBackStack() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            cancelRegistration()
        }
    }

    private fun cancelRegistration() {
        registrationViewModel.userCancelledRegistration()
        navController.popBackStack(R.id.loginFragment, false)
    }

    private fun initValidationFields() = mapOf(
        RegistrationViewModel.INPUT_USERNAME.first to inputLayoutChooseCredentialsUsername,
        RegistrationViewModel.INPUT_PASSWORD.first to inputLayoutChooseCredentialsPassword,
    )

    private fun listenToRegistrationStateEvent(validationFields: Map<String, TextInputLayout>){
        registrationViewModel.registrationStateEvent.observe(this, Observer { registrationState ->
            when(registrationState){
                is RegistrationViewModel.RegistrationState.RegistrationCompleted ->{
                    val token = registrationViewModel.authToken
                    val username = inputChooseCredentialsUsername.text.toString()

                    loginViewModel.authenticateToken(token, username)
                    navController.popBackStack(R.id.profileFragment, false)
                }
                is RegistrationViewModel.RegistrationState.InvalidCredentials -> {
                    registrationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        cancelRegistration()
        return super.onOptionsItemSelected(item)
    }

}