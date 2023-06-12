package com.speer.githubusers.domain.usecase

import com.speer.githubusers.domain.model.User
import com.speer.githubusers.domain.repository.UserRepository
import com.speer.githubusers.domain.usecase.base.UseCase
import com.speer.githubusers.domain.usecase.base.UseCase.Result
import javax.inject.Inject

class GetFollowingUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<Array<User>> {

    private var username: String? = null
    private var page = 1

    fun setUsername(username: String) {
        this.username = username
    }

    fun setPage(page: Int) {
        this.page = page
    }

    override suspend fun execute(): Result<Array<User>> {
        return try {
            Result.Success(this.userRepository.getFollowing(this.username!!, this.page))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
