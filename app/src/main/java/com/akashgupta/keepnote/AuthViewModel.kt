package com.akashgupta.keepnote

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akashgupta.keepnote.repository.UserRepository
import com.akashgupta.keepnote.utils.NetworkResult
import com.importantconcept.notesapp.models.signup.UserRequest
import com.akashgupta.keepnote.models.signup.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel //annotation
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch { //coroutine
            userRepository.registerUser(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    //Pair class  in kotlin
    fun validateCredentials(username: String, emailAddress: String, password: String, isLogin: Boolean): Pair<Boolean, String> {
        var result = Pair(true, "") //default value
        if ((!isLogin && TextUtils.isEmpty(username)) || TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password)) {
            result = Pair(false, "Please provide the credentials")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            result = Pair(false, "Please provide valid email")
        } else if (password.length <= 5) {
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }

}