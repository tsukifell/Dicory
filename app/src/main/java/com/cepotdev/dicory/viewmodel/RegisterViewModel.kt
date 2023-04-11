package com.cepotdev.dicory.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.model.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    companion object {
        private const val TAG = "RegisterViewModel"
    }

    fun userRegister(userData: UserInfo) {
        val client = ApiConfig.getApiService().userRegister(userData)
        client.enqueue(object : Callback<UserInfo> {
            override fun onResponse(
                call: Call<UserInfo>,
                response: Response<UserInfo>
            ) {
                response.body()
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }


}