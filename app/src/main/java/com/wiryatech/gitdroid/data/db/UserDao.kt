package com.wiryatech.gitdroid.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wiryatech.gitdroid.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users")
    fun getFavoriteUsers(): LiveData<List<User>>

    @Delete
    suspend fun deleteUser(user: User)

}