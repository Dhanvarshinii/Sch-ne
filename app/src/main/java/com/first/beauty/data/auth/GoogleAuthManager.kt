package com.first.beauty.data.auth

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

object GoogleAuthManager {

    // Create Google Sign-In Client
    fun getSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    // Launch Google Sign-In Intent
    fun getSignInIntent(client: GoogleSignInClient): Intent {
        return client.signInIntent
    }

    // Handle Sign-In result
    fun handleSignInResult(
        result: ActivityResult,
        onSuccess: (GoogleSignInAccount) -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                onSuccess(account) // pass back the account object
            } else {
                onError(Exception("Google account is null"))
            }
        } catch (e: ApiException) {
            onError(e)
        }
    }

}
