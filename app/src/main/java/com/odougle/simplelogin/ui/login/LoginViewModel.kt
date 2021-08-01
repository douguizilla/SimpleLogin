package com.odougle.simplelogin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.odougle.simplelogin.R

class LoginViewModel : ViewModel() {
    sealed class AuthenticationState{
        object Authenticated: AuthenticationState()
        object Unauthenticated: AuthenticationState()
        class InvalidAuthentication(val fields: List<Pair<String,Int>>): AuthenticationState()
    }

    val authenticationsStateEvent = MutableLiveData<AuthenticationState>()

    init {
        refuseAuthentication()
    }

    fun refuseAuthentication(){
        authenticationsStateEvent.value = AuthenticationState.Unauthenticated
    }

    fun authentication(username: String, password: String){
        if(isValidForm(username, password)){
            authenticationsStateEvent.value = AuthenticationState.Authenticated
        }
    }

    private fun isValidForm(username: String, password: String): Boolean {
        val invalidFilds = arrayListOf<Pair<String,Int>>()
        if(username.isEmpty()){
            invalidFilds.add(INPUT_USERNAME)
        }

        if(password.isEmpty()){
            invalidFilds.add(INPUT_PASSWORD)
        }

        if(invalidFilds.isNotEmpty()){
            authenticationsStateEvent.value = AuthenticationState.InvalidAuthentication(invalidFilds)
            return false
        }
        return true
    }

    companion object{
        val INPUT_USERNAME = "INPUT_USERNAME" to R.string.login_input_layout_error_invalid_username
        val INPUT_PASSWORD = "INPUT_PASSWORD" to R.string.login_input_layout_error_invalid_password
    }
}