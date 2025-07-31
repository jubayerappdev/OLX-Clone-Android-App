package com.creativeitinstitute.olxcloneanroidapp.data.repository

import com.creativeitinstitute.olxcloneanroidapp.core.Nodes
import com.creativeitinstitute.olxcloneanroidapp.data.models.UserLogin
import com.creativeitinstitute.olxcloneanroidapp.data.models.UserRegistration
import com.creativeitinstitute.olxcloneanroidapp.data.service.AuthService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val jAuth: FirebaseAuth,
    private val db: FirebaseFirestore
): AuthService {
    override fun userRegistration(user: UserRegistration): Task<AuthResult> {

        return jAuth.createUserWithEmailAndPassword(user.email, user.password)
    }

    override fun createUser(user: UserRegistration): Task<Void> {
        return db.collection(Nodes.USER).document(user.userID).set(user)
    }

    override fun userLogin(user: UserLogin): Task<AuthResult> {
       return jAuth.signInWithEmailAndPassword(user.email, user.password)
    }

    override fun userForgetPassword(email: String) {

    }
}