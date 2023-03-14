package com.ikhsan.submissionusergithub.response

import com.google.gson.annotations.SerializedName

data class UserResponseItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("type")
	val type: String
)
