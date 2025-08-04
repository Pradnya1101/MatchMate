package com.example.matchmate.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchmate.data.db.UserEntity
import com.example.matchmate.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _users = MediatorLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>> get() = _users

    private var currentSource: LiveData<List<UserEntity>>? = null

    init {
        loadUsers("all") // default load
    }

    fun loadUsers(filter: String = "all") {
        currentSource?.let { _users.removeSource(it) }
        val newSource = repository.getUsersByFilter(filter)
        currentSource = newSource
        _users.addSource(newSource) { _users.value = it }

        viewModelScope.launch {
            repository.fetchAndSaveUsers() // fetch latest users
        }
    }

    fun updateUserStatus(user: UserEntity, status: String) {
        viewModelScope.launch {
            repository.updateUser(user.copy(status = status))
            loadUsers() // refresh list after status change
        }
    }
}
