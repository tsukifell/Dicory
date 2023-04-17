package com.cepotdev.dicory.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.helper.SessionManager
import com.cepotdev.dicory.logic.model.LoginRequest
import com.cepotdev.dicory.logic.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val context: Context) : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val apiConfig = ApiConfig()

    companion object {
        private const val TAG = "LoginViewModel"
    }

    fun userLogin(loginRequest: LoginRequest) {
        apiConfig.getApiService(context).userLogin(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        _loginResponse.postValue(response.body())
                        // Save the user token in session manager
                        response.body()?.loginResult?.token?.let {
                            SessionManager(context).saveAuthToken(it)
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
