package com.hockey.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.hockey.data.model.UserModel


class AuthViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    fun login(
        email: String,
        password: String,
        onResult: (success: Boolean, role: String?, errorMessage: String?) -> Unit
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            onResult(false, null, "Email and password cannot be empty")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid
                    if (userId != null) {
                        // Fetch user role from Firestore
                        firestore.collection("users").document(userId)
                            .get()
                            .addOnSuccessListener { document ->
                                val userModel = document.toObject(UserModel::class.java)
                                val role = userModel?.role
                                if (role != null) {
                                    onResult(true, role, null)
                                } else {
                                    onResult(false, null, "User role not found")
                                }
                            }
                            .addOnFailureListener { e ->
                                onResult(false, null, "Failed to fetch user role: ${e.message}")
                            }
                    } else {
                        onResult(false, null, "User ID not found")
                    }
                } else {
                    onResult(false, null, task.exception?.localizedMessage ?: "Login failed")
                }
            }
    }

    fun signup(
        email: String,
        name: String,
        password: String,
        role: String, // Added role parameter for signup
        onResult: (success: Boolean, errorMessage: String?) -> Unit
    ) {
        if (email.isEmpty() || name.isEmpty() || password.isEmpty() || role.isEmpty()) {
            onResult(false, "All fields are required")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid
                    if (userId != null) {
                        val userModel = UserModel(name, email, userId, role)
                        firestore.collection("users").document(userId)
                            .set(userModel)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    onResult(true, null)
                                } else {
                                    onResult(false, "Failed to save user data: ${dbTask.exception?.message}")
                                }
                            }
                    } else {
                        onResult(false, "User ID not found")
                    }
                } else {
                    onResult(false, task.exception?.localizedMessage ?: "Signup failed")
                }
            }
    }
}