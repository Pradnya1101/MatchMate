package com.example.matchmate.repository

import androidx.lifecycle.LiveData
import com.example.matchmate.data.api.RetrofitClient
import com.example.matchmate.data.db.UserDao
import com.example.matchmate.data.db.UserEntity

class HomeRepository(private val userDao: UserDao) {

    val users: LiveData<List<UserEntity>> = userDao.getAllUsers()

    suspend fun fetchAndSaveUsers() {
        val response = RetrofitClient.apiService.getUsers()
        val userEntities = response.results.map {
            UserEntity(
                email = it.email,
                firstName = it.name.first,
                lastName = it.name.last,
                imageUrl = it.picture.large,
                city = it.location.city,
                country = it.location.country,
                status = null
            )
        }
        userDao.insertAll(userEntities)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }
}
