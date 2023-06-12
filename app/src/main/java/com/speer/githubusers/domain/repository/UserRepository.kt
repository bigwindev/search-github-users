package com.speer.githubusers.domain.repository

import com.speer.githubusers.domain.model.User

interface UserRepository {
    suspend fun search(query: String, page: Int): Array<User>
    suspend fun getFollowers(username: String, page: Int): Array<User>
    suspend fun getFollowing(username: String, page: Int): Array<User>
}
