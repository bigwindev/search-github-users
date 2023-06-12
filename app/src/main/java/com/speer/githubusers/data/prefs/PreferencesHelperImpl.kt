package com.speer.githubusers.data.prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesHelperImpl @Inject constructor(
    @ApplicationContext context: Context
) : PreferencesHelper {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("GitHubUsers", Context.MODE_PRIVATE)

    override fun getSearchHistories(): Array<String> {
        return this.prefs.getString("SearchHistories", null)?.split(", ")?.toTypedArray()
            ?: arrayOf()
    }

    override fun addSearchHistory(query: String) {
        val searchHistories: MutableList<String> =
            this.prefs.getString("SearchHistories", null)?.split(", ")?.toMutableList()
                ?: mutableListOf()

        if (searchHistories.contains(query)) {
            searchHistories.remove(query)
        }

        searchHistories.add(0, query)

        this.prefs.edit().putString("SearchHistories", searchHistories.joinToString()).apply()
    }
}
