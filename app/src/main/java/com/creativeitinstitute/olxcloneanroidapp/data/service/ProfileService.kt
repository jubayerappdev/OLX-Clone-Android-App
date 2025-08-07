package com.creativeitinstitute.olxcloneanroidapp.data.service

import android.net.Uri
import com.creativeitinstitute.olxcloneanroidapp.data.models.Profile
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask

interface ProfileService {

    fun getUserByUserID(userID: String): Task<QuerySnapshot>
    fun uploadImage(productImageUri: Uri): UploadTask
    fun updateUser(user: Profile): Task<Void>
    fun changePassword()
}