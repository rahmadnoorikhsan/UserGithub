package com.ikhsan.submissionusergithub.network

import com.ikhsan.submissionusergithub.response.DetailResponse
import com.ikhsan.submissionusergithub.response.ListUserResponse
import com.ikhsan.submissionusergithub.response.UserResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<ListUserResponse>

    @GET("users/{username}")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserResponseItem>>
}