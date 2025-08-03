package com.example.matchmate.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val firstName: String,
    val lastName: String,
    val imageUrl: String,
    val city: String,
    val country: String,
    val status: String?
)
