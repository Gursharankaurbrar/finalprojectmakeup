package com.example.finalprojectmakeup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * ViewModel that handles authentication logic for user login, registration, and sign-out
 * using Firebase Authentication.
 *
 * It provides methods for user registration, login, anonymous sign-in, and linking anonymous users
 * with email credentials. It also observes the authentication state and updates the `isUserLoggedIn`
 * variable when the user's authentication state changes.
 */
class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    var isUserLoggedIn by mutableStateOf(false)

    init {

        Firebase.auth.addAuthStateListener { firebaseAuth ->
            isUserLoggedIn = firebaseAuth.currentUser != null
        }
    }

    /**
     * Registers a new user with email and password.
     *
     * @param email: String - User's email address
     * @param password: String - User's password
     * @param onSuccess: () -> Unit - Callback function invoked when registration is successful
     * @param onError: (String) -> Unit - Callback function invoked with an error message when registration fails
     */
    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError(task.exception?.message ?: "Registration failed")
                }
            }

    }

    /**
     * Logs in a user with email and password.
     *
     * @param email: String - User's email address
     * @param password: String - User's password
     * @param onSuccess: () -> Unit - Callback function invoked when login is successful
     * @param onError: (String) -> Unit - Callback function invoked with an error message when login fails
     */
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError(task.exception?.message ?: "Login failed")
                }
            }
        isUserLoggedIn = true
    }

    /**
     * Signs out the currently logged-in user.
     */
    fun signOut() {
        auth.signOut()

    }

// Second Sign-In Method
    /**
     * Signs in the user anonymously.
     *
     * @param onSuccess: () -> Unit - Callback function invoked when sign-in is successful
     * @param onError: (Exception) -> Unit - Callback function invoked with an exception when sign-in fails
     */
    fun signInAnonymously(
        onSuccess: () -> Unit,
        onError:  (Exception) -> Unit
    ) {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError(task.exception ?: Exception("Unknown error occurred"))
                }
            }
    }

    /**
     * Links an anonymous user with an email/password credential.
     *
     * @param email: String - User's email address
     * @param password: String - User's password
     * @param onSuccess: () -> Unit - Callback function invoked when the link is successful
     * @param onFailure: (Exception) -> Unit - Callback function invoked with an exception if the link fails
     */
    fun linkAnonymousWithEmail(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)
        FirebaseAuth.getInstance().currentUser?.linkWithCredential(credential)
            ?.addOnSuccessListener {
                onSuccess()
            }
            ?.addOnFailureListener { e ->
                onFailure(e)
            }
    }

}