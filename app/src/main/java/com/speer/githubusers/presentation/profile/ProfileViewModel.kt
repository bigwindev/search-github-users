package com.speer.githubusers.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.speer.githubusers.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private lateinit var _user: User
    val user: User
        get() = _user

    private val _navigateToFollowers = MutableLiveData<User>()
    val navigateToFollowers: LiveData<User>
        get() = _navigateToFollowers

    private val _navigateToFollowing = MutableLiveData<User>()
    val navigateToFollowing: LiveData<User>
        get() = _navigateToFollowing

    fun setUser(user: User) {
        this._user = user
    }

    fun setNavigatedToFollowers() {
        this._navigateToFollowers.value = null
    }

    fun setNavigatedToFollowing() {
        this._navigateToFollowing.value = null
    }

    fun onClickFollowers() {
        this._navigateToFollowers.value = this._user
    }

    fun onClickFollowing() {
        this._navigateToFollowing.value = this._user
    }
}
