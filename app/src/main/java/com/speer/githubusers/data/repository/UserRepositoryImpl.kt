package com.speer.githubusers.data.repository

import com.speer.githubusers.data.api.ApiService
import com.speer.githubusers.domain.model.User
import com.speer.githubusers.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun search(query: String, page: Int): Array<User> {
        return this.apiService.searchUsers(query, page).items
            .map { this.apiService.getUser(it.login) }.toTypedArray()
    }

    override suspend fun getFollowers(username: String, page: Int): Array<User> {
        return this.apiService.getFollowers(username, page)
            .map { this.apiService.getUser(it.login) }.toTypedArray()
    }

    override suspend fun getFollowing(username: String, page: Int): Array<User> {
        return this.apiService.getFollowing(username, page)
            .map { this.apiService.getUser(it.login) }.toTypedArray()
    }
}
