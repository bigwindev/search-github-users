package com.speer.githubusers.domain.repository

interface SearchHistoryRepository {
    suspend fun get(): Array<String>
    fun add(query: String)
}
