package com.speer.githubusers.domain.usecase

import com.speer.githubusers.domain.model.User
import com.speer.githubusers.domain.repository.UserRepository
import com.speer.githubusers.domain.usecase.base.UseCase
import com.speer.githubusers.domain.usecase.base.UseCase.Result
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<Array<User>> {

    private var query: String? = null
    private var page = 1

    fun setQuery(query: String, page: Int) {
        this.query = query
        this.page = page
    }

    override suspend fun execute(): Result<Array<User>> {
        return try {
            Result.Success(this.userRepository.search(this.query!!, this.page))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
