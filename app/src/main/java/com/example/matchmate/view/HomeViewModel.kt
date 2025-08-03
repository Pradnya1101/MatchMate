package com.example.matchmate.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchmate.data.db.UserEntity
import com.example.matchmate.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    val users = repository.users

    fun loadUsers() {
        viewModelScope.launch {
            repository.fetchAndSaveUsers()
        }
    }

    fun updateUserStatus(user: UserEntity, status: String) {
        viewModelScope.launch {
            repository.updateUser(user.copy(status = status))
        }
    }
}
