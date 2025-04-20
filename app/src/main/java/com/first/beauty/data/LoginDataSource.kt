package com.first.beauty.data

import android.content.Context
import com.first.beauty.DatabaseManager
import com.first.beauty.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(private val context: Context) {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val dbManager = DatabaseManager(context)
            dbManager.open()
            val cursor = dbManager.getUserByUsername(username)

            return if (cursor != null && cursor.moveToFirst()) {
                val storedPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                if (storedPassword == password) {
                    val fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"))
                    val userId = cursor.getInt(cursor.getColumnIndexOrThrow("id")).toString()
                    cursor.close()
                    dbManager.close()
                    Result.Success(LoggedInUser(userId, fullName))
                } else {
                    cursor.close()
                    dbManager.close()
                    Result.Error(Exception("Incorrect password"))
                }
            } else {
                cursor?.close()
                dbManager.close()
                Result.Error(Exception("User not found"))
            }
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }



    fun logout() {
        // TODO: revoke authentication
    }
}