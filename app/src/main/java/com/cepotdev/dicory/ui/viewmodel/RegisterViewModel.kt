package com.cepotdev.dicory.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.model.UserRequest
import com.cepotdev.dicory.logic.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse> = _userResponse

    private val _isRegisterLoading = MutableLiveData<Boolean>()
    val isRegisterLoading: LiveData<Boolean> = _isRegisterLoading

    private val apiConfig = ApiConfig()

    companion object {
        private const val TAG = "RegisterViewModel"
    }

    fun userRegister(userData: UserRequest) {
        _isRegisterLoading.value = true
        apiConfig.getApiService(getApplication()).userRegister(userData)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isRegisterLoading.value = false
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