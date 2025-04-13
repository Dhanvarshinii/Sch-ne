package com.first.beauty

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

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
            put(DatabaseHelper.COLUMN_FULL_NAME, fullName)
            put(DatabaseHelper.COLUMN_NAME, name)
            put(DatabaseHelper.COLUMN_EMAIL, email)
            put(DatabaseHelper.COLUMN_COUNTRY, country)
            put(DatabaseHelper.COLUMN_GENDER, gender)
            put(DatabaseHelper.COLUMN_USERNAME, username)
            put(DatabaseHelper.COLUMN_PASSWORD, password)
            put(DatabaseHelper.COLUMN_DATE_OF_BIRTH, dateOfBirth)
        }

        return database?.insert(DatabaseHelper.TABLE_USERS, null, values) ?: -1
    }

    fun getUserByUsername(username: String): Cursor? {
        val columns = arrayOf(
            DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_FULL_NAME, DatabaseHelper.COLUMN_NAME,
            DatabaseHelper.COLUMN_EMAIL, DatabaseHelper.COLUMN_COUNTRY, DatabaseHelper.COLUMN_GENDER,
            DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_PASSWORD, DatabaseHelper.COLUMN_DATE_OF_BIRTH
        )

        val selection = "${DatabaseHelper.COLUMN_USERNAME} = ?"
        val selectionArgs = arrayOf(username)

        return database?.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null)
    }
}
