package com.odougle.simplelogin.ui.login

import androidx.lifecycle.LiveData
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

    var username: String = ""
    var token: String = ""

    private val _authenticationStateEvent = MutableLiveData<AuthenticationState>()
    val authenticationStateEvent: LiveData<AuthenticationState>
        get() = _authenticationStateEvent

    init {
        refuseAuthentication()
    }

    fun refuseAuthentication(){
        _authenticationStateEvent.value = AuthenticationState.Unauthenticated
    }

    fun authenticateToken(token: String, username: String){
        this.token = token
        this.username = username
        _authenticationStateEvent.value = AuthenticationState.Authenticated
    }

    fun authentication(username: String, password: String){
        if(isValidForm(username, password)){
            _authenticationStateEvent.value = AuthenticationState.Authenticated
            this.username = username
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
            _authenticationStateEvent.value = AuthenticationState.InvalidAuthentication(invalidFilds)
            return false
        }
        return true
    }

    companion object{
        val INPUT_USERNAME = "INPUT_USERNAME" to R.string.login_input_layout_error_invalid_username
        val INPUT_PASSWORD = "INPUT_PASSWORD" to R.string.login_input_layout_error_invalid_password
    }
}