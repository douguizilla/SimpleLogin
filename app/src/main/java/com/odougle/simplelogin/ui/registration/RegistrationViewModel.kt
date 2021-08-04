package com.odougle.simplelogin.ui.registration

import androidx.lifecycle.*
import com.odougle.simplelogin.R
import com.odougle.simplelogin.data.reposity.UserRepository
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    lateinit var registrationViewParams: RegistrationViewParams

    //para proteger o livedata e ser usado apenas nesta classe
    private val _registrationStateEvent = MutableLiveData<RegistrationState>(RegistrationState.CollectProfileData)
    val registrationStateEvent: LiveData<RegistrationState>
        get() =_registrationStateEvent

    var authToken = ""
        private set

    fun colletProfileData(name: String, bio: String){
        if(isValidProfileData(name, bio)){
            //persist data
            _registrationStateEvent.value = RegistrationState.CollectCredentials
        }
    }

    private fun isValidProfileData(name: String, bio: String): Boolean {
        val invalidFields = arrayListOf<Pair<String,Int>>()
        if(name.isEmpty()){
            invalidFields.add(INPUT_NAME)
        }
        if(bio.isEmpty()){
            invalidFields.add(INPUT_BIO)
        }

        if(invalidFields.isNotEmpty()){
            _registrationStateEvent.value = RegistrationState.InvalidProfileData(invalidFields)
            return false
        }
        return true
    }

    fun createCredentials(username: String, password: String){
        if(isValidCredentials(username, password)){
            viewModelScope.launch {
                registrationViewParams.username = username
                registrationViewParams.password = password

                userRepository.createUser(registrationViewParams)

                this@RegistrationViewModel.authToken = "token"
                _registrationStateEvent.value = RegistrationState.RegistrationCompleted
            }
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        val invalidFields = arrayListOf<Pair<String,Int>>()
        if(username.isEmpty()){
            invalidFields.add(INPUT_USERNAME)
        }
        if(password.isEmpty()){
            invalidFields.add(INPUT_PASSWORD)
        }

        if(invalidFields.isNotEmpty()){
            _registrationStateEvent.value = RegistrationState.InvalidCredentials(invalidFields)
            return false
        }
        return true
    }

    fun userCancelledRegistration() : Boolean{
        authToken = ""
        _registrationStateEvent.value = RegistrationState.CollectProfileData
        return true
    }

    sealed class RegistrationState{
        object CollectProfileData: RegistrationState()
        object CollectCredentials: RegistrationState()
        object RegistrationCompleted: RegistrationState()
        class InvalidProfileData(val fields: List<Pair<String, Int>>): RegistrationState()
        class InvalidCredentials(val fields: List<Pair<String, Int>>): RegistrationState()
    }

    //criando uma factory para sinalizar para o viewmodel que o respositorio
    //passado como parametro é o que quero usar
    class RegistrationViewModelFactory(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RegistrationViewModel(userRepository) as T
        }

    }


    companion object{
        val INPUT_NAME = "INPUT_NAME" to R.string.profile_data_input_layout_error_invalid_name
        val INPUT_BIO = "INPUT_BIO" to R.string.profile_data_input_layout_error_invalid_bio
        val INPUT_USERNAME = "INPUT_USERNAME" to R.string.choose_credentials_input_layout_error_invalid_username
        val INPUT_PASSWORD = "INPUT_PASSWORD" to R.string.choose_credentials_input_layout_error_invalid_password
    }
}