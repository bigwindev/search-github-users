package com.speer.githubusers.data.model

import com.google.gson.annotations.SerializedName
import com.speer.githubusers.domain.model.User

data class UserSearchResponse(
    @SerializedName("total_count") val totalCount: Long,
    val items: Array<User>
)
