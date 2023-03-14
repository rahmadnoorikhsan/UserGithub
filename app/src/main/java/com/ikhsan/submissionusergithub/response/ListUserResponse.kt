package com.ikhsan.submissionusergithub.response

import com.google.gson.annotations.SerializedName

data class ListUserResponse(

	@field:SerializedName("items")
	val items: List<UserResponseItem>
)
