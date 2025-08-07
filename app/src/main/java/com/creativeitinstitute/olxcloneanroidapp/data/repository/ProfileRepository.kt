package com.creativeitinstitute.olxcloneanroidapp.data.repository

import android.net.Uri
import com.creativeitinstitute.olxcloneanroidapp.core.Nodes
import com.creativeitinstitute.olxcloneanroidapp.data.models.Profile
import com.creativeitinstitute.olxcloneanroidapp.data.models.toMap
import com.creativeitinstitute.olxcloneanroidapp.data.service.ProfileService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageRef: StorageReference
) : ProfileService {
    override fun getUserByUserID(userID: String): Task<QuerySnapshot> {

        return db.collection(Nodes.USER).whereEqualTo("userID", userID).get()
    }

    override fun uploadImage(productImageUri: Uri): UploadTask {
        val storage: StorageReference = storageRef.child("profile").child("USER_${System.currentTimeMillis()}")
        return storage.putFile(productImageUri)
    }

    override fun updateUser(user: Profile): Task<Void> {

        return db.collection(Nodes.USER).document(user.userID).update(user.toMap())

    }

    override fun changePassword() {

    }
}