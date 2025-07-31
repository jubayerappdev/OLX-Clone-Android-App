package com.creativeitinstitute.olxcloneanroidapp.data.service

import com.creativeitinstitute.olxcloneanroidapp.data.models.UserLogin
import com.creativeitinstitute.olxcloneanroidapp.data.models.UserRegistration
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthService {

    fun userRegistration(user: UserRegistration): Task<AuthResult>
    fun createUser(user: UserRegistration): Task<Void>
    fun userLogin(user: UserLogin): Task<AuthResult>
    fun userForgetPassword(email: String)

}