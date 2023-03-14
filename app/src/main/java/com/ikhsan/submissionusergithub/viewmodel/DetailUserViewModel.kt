package com.ikhsan.submissionusergithub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikhsan.submissionusergithub.network.ApiConfig
import com.ikhsan.submissionusergithub.response.DetailResponse
import com.ikhsan.submissionusergithub.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {

    private val _listDetailUser = MutableLiveData<DetailResponse>()
    val listDetailUser: LiveData<DetailResponse> = _listDetailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    fun setDetailUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUsers(username)
        client.enqueue(object : Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listDetailUser.postValue(response.body())
                } else {
                    _snackBarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _snackBarText.value = Event(t.message.toString())
                _isLoading.value = false
            }
        })
    }
}