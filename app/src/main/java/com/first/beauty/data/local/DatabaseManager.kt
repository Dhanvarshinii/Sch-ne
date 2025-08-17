package com.first.beauty.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.first.beauty.data.local.DatabaseHelper

class DatabaseManager(context: Context) {
    private val dbHelper = DatabaseHelper(context)
    private var database: SQLiteDatabase? = null

    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun insertUser(fullName: String, name: String, email: String, country: String, gender: String,
                   username: String, password: String, dateOfBirth: String): Long {
        val values = ContentValues().apply {
            put(DatabaseHelper.Companion.COLUMN_FULL_NAME, fullName)
            put(DatabaseHelper.Companion.COLUMN_NAME, name)
            put(DatabaseHelper.Companion.COLUMN_EMAIL, email)
            put(DatabaseHelper.Companion.COLUMN_COUNTRY, country)
            put(DatabaseHelper.Companion.COLUMN_GENDER, gender)
            put(DatabaseHelper.Companion.COLUMN_USERNAME, username)
            put(DatabaseHelper.Companion.COLUMN_PASSWORD, password)
            put(DatabaseHelper.Companion.COLUMN_DATE_OF_BIRTH, dateOfBirth)
        }

        return database?.insert(DatabaseHelper.Companion.TABLE_USERS, null, values) ?: -1
    }

    fun getUserByUsername(username: String): Cursor? {
        val columns = arrayOf(
            DatabaseHelper.Companion.COLUMN_ID, DatabaseHelper.Companion.COLUMN_FULL_NAME, DatabaseHelper.Companion.COLUMN_NAME,
            DatabaseHelper.Companion.COLUMN_EMAIL, DatabaseHelper.Companion.COLUMN_COUNTRY, DatabaseHelper.Companion.COLUMN_GENDER,
            DatabaseHelper.Companion.COLUMN_USERNAME, DatabaseHelper.Companion.COLUMN_PASSWORD, DatabaseHelper.Companion.COLUMN_DATE_OF_BIRTH
        )

        val selection = "${DatabaseHelper.Companion.COLUMN_USERNAME} = ?"
        val selectionArgs = arrayOf(username)

        return database?.query(DatabaseHelper.Companion.TABLE_USERS, columns, selection, selectionArgs, null, null, null)
    }
}