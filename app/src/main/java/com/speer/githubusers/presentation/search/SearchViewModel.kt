package com.speer.githubusers.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.speer.githubusers.domain.usecase.AddSearchHistoryUseCase
import com.speer.githubusers.domain.usecase.GetSearchHistoriesUseCase
import com.speer.githubusers.domain.usecase.base.UseCase
import com.speer.githubusers.presentation.users.UsersViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchHistoriesUseCase: GetSearchHistoriesUseCase,
    private val addSearchHistoryUseCase: AddSearchHistoryUseCase
) : ViewModel() {

    val adapter = SearchHistoriesAdapter(OnClickListener { query ->
        setQuery(query)
    })

    private val _query = MutableLiveData("")
    val query: LiveData<String>
        get() = _query

    private lateinit var usersViewModel: UsersViewModel

    init {
        getSearchHistories()
    }

    fun setUsersViewModel(usersViewModel: UsersViewModel) {
        this.usersViewModel = usersViewModel
    }

    fun setQuery(query: String) {
        if (this._query.value == query) {
            return
        }

        this._query.value = query
        this.usersViewModel.searchUsers(query)

        if (query.isNotEmpty()) {
            addSearchHistory(query)
            getSearchHistories()
        }
    }

    private fun getSearchHistories() {
        this.viewModelScope.launch {
            val result = this@SearchViewModel.getSearchHistoriesUseCase.execute()
            if (result is UseCase.Result.Success)
                this@SearchViewModel.adapter.setHistories(result.value)
        }
    }

    private fun addSearchHistory(query: String) {
        this.viewModelScope.launch {
            this@SearchViewModel.addSearchHistoryUseCase.setQuery(query)
            this@SearchViewModel.addSearchHistoryUseCase.execute()
        }
    }
}
