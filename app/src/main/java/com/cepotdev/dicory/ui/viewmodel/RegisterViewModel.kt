package com.cepotdev.dicory.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.model.UserRequest
import com.cepotdev.dicory.logic.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val context: Context) : ViewModel() {
    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse> = _userResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val apiConfig = ApiConfig()

    companion object {
        private const val TAG = "RegisterViewModel"
    }

    fun userRegister(userData: UserRequest) {
        apiConfig.getApiService(context).userRegister(userData).enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _userResponse.postValue(response.body())
                } else {
                    _userResponse.postValue(
                        UserResponse(
                            error = true,
                            message = "Failed to register user"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }


}