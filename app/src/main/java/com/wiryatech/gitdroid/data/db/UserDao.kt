package com.wiryatech.gitdroid.data.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.wiryatech.gitdroid.data.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users")
    fun getFavoriteUsers(): LiveData<List<User>>

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE login=:login)")
    fun checkFavorite(login: String): LiveData<Int>

    @Query("SELECT COUNT(*) FROM users")
    fun getAmountOfData(): LiveData<Int>

    @Query("SELECT * FROM users")
    fun getFavoriteListForConsumer(): Cursor

}