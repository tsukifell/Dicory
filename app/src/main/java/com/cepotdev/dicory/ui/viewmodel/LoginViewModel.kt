package com.cepotdev.dicory.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.helper.SessionManager
import com.cepotdev.dicory.logic.model.LoginRequest
import com.cepotdev.dicory.logic.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _isLoginLoading = MutableLiveData<Boolean>()
    val isLoginLoading: LiveData<Boolean> = _isLoginLoading

    private val apiConfig = ApiConfig()

    companion object {
        private const val TAG = "LoginViewModel"
    }

    fun userLogin(loginRequest: LoginRequest) {
        _isLoginLoading.value = true
        apiConfig.getApiService(getApplication()).userLogin(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    _isLoginLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _loginResponse.postValue(response.body())
                        response.body()?.loginResult?.token?.let {
                            SessionManager(getApplication()).saveAuthToken(it)
                        }
                    } else {
                        _loginResponse.postValue(
                            LoginResponse(
                                error = true,
                                message = "Check your email or password!"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }
}