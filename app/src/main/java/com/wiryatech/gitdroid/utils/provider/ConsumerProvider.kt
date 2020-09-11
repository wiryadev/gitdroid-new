package com.wiryatech.gitdroid.utils.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.wiryatech.gitdroid.data.db.UserDao
import com.wiryatech.gitdroid.data.db.UserDb
import java.lang.UnsupportedOperationException

class ConsumerProvider : ContentProvider() {

    private lateinit var userDao: UserDao

    companion object {
        private const val AUTHORITY = "com.wiryatech.gitdroid"
        private const val TABLE_DAO = "user_db"
        private const val USER_DAO = 1

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply { addURI(AUTHORITY, TABLE_DAO, USER_DAO) }

    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun onCreate(): Boolean {
        userDao = UserDb.invoke(context as Context).getUserDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USER_DAO -> userDao.getFavoriteListForConsumer()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }
}
