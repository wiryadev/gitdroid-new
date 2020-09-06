package com.wiryatech.gitdroid.data.db

import android.content.Context
import androidx.room.*
import com.wiryatech.gitdroid.data.model.User

@Database(entities = [User::class], version = 1)

abstract class UserDb : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context): UserDb {
            return Room.databaseBuilder(context.applicationContext, UserDb::class.java, "user.db")
                .build()
        }
    }

}