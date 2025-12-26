package com.first.beauty.data.auth

import android.app.Instrumentation.ActivityResult
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object GoogleAuthManager {

    private const val WEB_CLIENT_ID =
        "219736585947-e6ai7bs91lsootu5j58aqq9ec77qc3ol.apps.googleusercontent.com" // <-- Replace with yours

    fun getClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    fun handleSignInResult(
        data: android.content.Intent?,
        onSuccess: (email: String) -> Unit,
        onError: () -> Unit
    ) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(Exception::class.java)
            val email = account?.email

            if (email.isNullOrEmpty()) {
                onError()
                return
            }

            onSuccess(email)
        } catch (e: Exception) {
            onError()
        }
    }
}
