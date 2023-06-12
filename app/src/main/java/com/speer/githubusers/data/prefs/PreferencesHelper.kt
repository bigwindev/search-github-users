package com.speer.githubusers.data.prefs

interface PreferencesHelper {
    fun getSearchHistories(): Array<String>
    fun addSearchHistory(query: String)
}
