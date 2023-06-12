package com.speer.githubusers.domain.usecase

import com.speer.githubusers.domain.repository.SearchHistoryRepository
import com.speer.githubusers.domain.usecase.base.UseCase
import com.speer.githubusers.domain.usecase.base.UseCase.Result
import javax.inject.Inject

class AddSearchHistoryUseCase @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) : UseCase<Unit> {

    private var query: String? = null

    fun setQuery(query: String) {
        this.query = query
    }

    override suspend fun execute(): Result<Unit> {
        return try {
            Result.Success(this.searchHistoryRepository.add(this.query!!))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
