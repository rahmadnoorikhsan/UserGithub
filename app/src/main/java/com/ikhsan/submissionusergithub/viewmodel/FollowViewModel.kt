package com.ikhsan.submissionusergithub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikhsan.submissionusergithub.network.ApiConfig
import com.ikhsan.submissionusergithub.response.UserResponseItem
import com.ikhsan.submissionusergithub.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _listFollowers = MutableLiveData<List<UserResponseItem>>()
    val listFollowers: LiveData<List<UserResponseItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<UserResponseItem>>()
    val listFollowing: LiveData<List<UserResponseItem>> = _listFollowing

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<UserResponseItem>>{
            override fun onResponse(
                call: Call<List<UserResponseItem>>,
                response: Response<List<UserResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _listFollowers.value = response.body()
                } else {
                    _snackBarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<List<UserResponseItem>>, t: Throwable) {
                _snackBarText.value = Event(t.message.toString())
                _isLoading.value = false

            }
        })
    }

    fun setFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<UserResponseItem>>{
            override fun onResponse(
                call: Call<List<UserResponseItem>>,
                response: Response<List<UserResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _listFollowing.value = response.body()
                } else {
                    _snackBarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<List<UserResponseItem>>, t: Throwable) {
                _snackBarText.value = Event(t.message.toString())
                _isLoading.value = false
            }

        })
    }
}