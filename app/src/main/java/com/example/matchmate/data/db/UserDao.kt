package com.example.matchmate.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE status IS NULL OR status = ''")
    fun getUnreviewedUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE status = 'accepted'")
    fun getAcceptedUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE status = 'declined'")
    fun getDeclinedUsers(): LiveData<List<UserEntity>>
}
