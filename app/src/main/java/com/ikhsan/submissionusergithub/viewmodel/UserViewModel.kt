package com.ikhsan.submissionusergithub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikhsan.submissionusergithub.network.ApiConfig
import com.ikhsan.submissionusergithub.response.ListUserResponse
import com.ikhsan.submissionusergithub.response.UserResponseItem
import com.ikhsan.submissionusergithub.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    private val _listUser = MutableLiveData<List<UserResponseItem>>()
    val listUser: LiveData<List<UserResponseItem>> = _listUser

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUsers(query)
        client.enqueue(object : Callback<ListUserResponse> {
            override fun onResponse(call: Call<ListUserResponse>, response: Response<ListUserResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _listUser.postValue(response.body()?.items)
                } else {
                    _snackBarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<ListUserResponse>, t: Throwable) {
                _isLoading.value = false
                _snackBarText.value = Event(t.message.toString())
            }

        })
    }
}