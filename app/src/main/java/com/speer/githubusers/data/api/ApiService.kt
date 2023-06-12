package com.speer.githubusers.data.api

import com.speer.githubusers.data.model.UserSearchResponse
import com.speer.githubusers.domain.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search/users")
    suspend fun searchUsers(@Query("q") query: String, @Query("page") page: Int): UserSearchResponse

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username: String): User

    @GET("/users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String, @Query("page") page: Int): Array<User>

    @GET("/users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String, @Query("page") page: Int): Array<User>
}
