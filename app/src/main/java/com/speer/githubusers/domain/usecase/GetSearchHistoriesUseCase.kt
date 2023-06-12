package com.speer.githubusers.domain.usecase

import com.speer.githubusers.domain.repository.SearchHistoryRepository
import com.speer.githubusers.domain.usecase.base.UseCase
import com.speer.githubusers.domain.usecase.base.UseCase.Result
import javax.inject.Inject

class GetSearchHistoriesUseCase @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) : UseCase<Array<String>> {

    override suspend fun execute(): Result<Array<String>> {
        return try {
            Result.Success(this.searchHistoryRepository.get())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
