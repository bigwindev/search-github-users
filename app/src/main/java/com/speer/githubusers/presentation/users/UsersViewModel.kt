package com.speer.githubusers.presentation.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.speer.githubusers.domain.model.User
import com.speer.githubusers.domain.usecase.GetFollowersUseCase
import com.speer.githubusers.domain.usecase.GetFollowingUseCase
import com.speer.githubusers.domain.usecase.SearchUsersUseCase
import com.speer.githubusers.domain.usecase.base.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val getFollowersUseCase: GetFollowersUseCase,
    private val getFollowingUseCase: GetFollowingUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _navigateToProfile = MutableLiveData<User>()
    val navigateToProfile: LiveData<User>
        get() = _navigateToProfile

    private val _navigateToFollowers = MutableLiveData<User>()
    val navigateToFollowers: LiveData<User>
        get() = _navigateToFollowers

    private val _navigateToFollowing = MutableLiveData<User>()
    val navigateToFollowing: LiveData<User>
        get() = _navigateToFollowing

    val adapter = UsersAdapter(OnClickListener { user ->
        this._navigateToProfile.value = user
    }, OnClickListener { user ->
        this._navigateToFollowers.value = user
    }, OnClickListener { user ->
        this._navigateToFollowing.value = user
    })

    private val _isShowingNotFound = MutableLiveData(false)
    val isShowingNotFound: LiveData<Boolean>
        get() = _isShowingNotFound

    val countOfUsers: Int
        get() = this.users.size

    private var users: Array<User> = arrayOf()
    private val query = MutableStateFlow<String?>(null)
    private var apiType = MutableStateFlow(ApiType.FOLLOWERS)
    private var username = MutableStateFlow<String?>(null)
    private var page = MutableStateFlow(1)

    init {
        this.query
            .filterNotNull()
            .combine(this.page) { query, page -> Pair(query, page) }
            .mapLatest { (query, page) -> executeSearchUsersUseCase(query, page) }
            .launchIn(this.viewModelScope)

        combine(this.apiType, this.username.filterNotNull(), this.page) { apiType, username, page ->
            Triple(apiType, username, page)
        }
            .mapLatest { (apiType, username, page) ->
                when (apiType) {
                    ApiType.FOLLOWERS -> executeGetFollowersUseCase(username, page)
                    ApiType.FOLLOWING -> executeGetFollowingUseCase(username, page)
                }
            }
            .launchIn(this.viewModelScope)
    }

    fun searchUsers(query: String) {
        this.query.value = ""
        this.query.value = query
        this.page.value = 1
    }

    fun getFollowers(username: String) {
        this.apiType.value = ApiType.FOLLOWERS
        this.username.value = username
    }

    fun getFollowing(username: String) {
        this.apiType.value = ApiType.FOLLOWING
        this.username.value = username
    }

    private suspend fun executeSearchUsersUseCase(query: String, page: Int) {
        if (query.isEmpty()) {
            handleReturnedUsers(arrayOf())
            this._isShowingNotFound.value = false
            return
        }

        if (page == 1)
            this._isLoading.value = true
        this.searchUsersUseCase.setQuery(query, page)
        handleResult(this.searchUsersUseCase.execute())
    }

    private suspend fun executeGetFollowersUseCase(username: String, page: Int) {
        if (page == 1)
            this._isLoading.value = true
        this.getFollowersUseCase.setUsername(username)
        this.getFollowersUseCase.setPage(page)
        handleResult(this.getFollowersUseCase.execute())
    }

    private suspend fun executeGetFollowingUseCase(username: String, page: Int) {
        if (page == 1)
            this._isLoading.value = true
        this.getFollowingUseCase.setUsername(username)
        this.getFollowingUseCase.setPage(page)
        handleResult(this.getFollowingUseCase.execute())
    }

    private fun handleResult(result: UseCase.Result<Array<User>>) {
        when (result) {
            is UseCase.Result.Success -> handleReturnedUsers(result.value)
            is UseCase.Result.Error -> print(result.e.message)
        }
    }

    private fun handleReturnedUsers(users: Array<User>) {
        this._isLoading.value = false

        if (this.page.value == 1) {
            this.users = users
            if (users.isEmpty())
                this._isShowingNotFound.value = true
        } else {
            this.users = this.users + users
        }
        this.adapter.setUsers(this.users)
    }

    fun refresh() {
        this.page.value = 0
        this.page.value = 1
    }

    fun loadMore() {
        if (!this.adapter.hasLoading()) {
            this.adapter.addLoading()

            this.page.value = this.page.value + 1
        }
    }

    fun setNavigatedToProfile() {
        this._navigateToProfile.value = null
    }

    fun setNavigatedToFollowers() {
        this._navigateToFollowers.value = null
    }

    fun setNavigatedToFollowing() {
        this._navigateToFollowing.value = null
    }

    enum class ApiType(val value: Int) {
        FOLLOWERS(0), FOLLOWING(1)
    }
}
