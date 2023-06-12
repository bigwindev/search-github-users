package com.speer.githubusers.data.repository

import com.speer.githubusers.data.prefs.PreferencesHelper
import com.speer.githubusers.domain.repository.SearchHistoryRepository
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val preferencesHelper: PreferencesHelper
) : SearchHistoryRepository {

    override suspend fun get(): Array<String> {
        return this.preferencesHelper.getSearchHistories()
    }

    override fun add(query: String) {
        this.preferencesHelper.addSearchHistory(query)
    }
}
