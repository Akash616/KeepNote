package com.akashgupta.keepnote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akashgupta.keepnote.api.UserAPI
import com.akashgupta.keepnote.utils.Constants.TAG
import com.akashgupta.keepnote.utils.NetworkResult
import com.importantconcept.notesapp.models.signup.UserRequest
import com.akashgupta.keepnote.models.signup.UserResponse
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

//Repository: access data from remote data source (Retrofit - UserAPI) or local data source
class UserRepository @Inject constructor(private val userAPI: UserAPI) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>() //mutable - we can change
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>> //publicly accessible - only ready
        get() = _userResponseLiveData

    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading()) //loading state
        val response = userAPI.signup(userRequest)
        Log.d(TAG, response.body().toString())
        handleResponse(response)
    }

    suspend fun loginUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading()) //loading state
        val response = userAPI.signin(userRequest)
        Log.d(TAG, response.body().toString())
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            //Retrofit ka case mai, if req is successfully then auto create java/kotlin object and give resp.body .
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            //But in error we have to create java/kotlin object.
            //Json parse using -> JSONObject
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            //charStream().readText() -> puri json ko read kar lega.
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}