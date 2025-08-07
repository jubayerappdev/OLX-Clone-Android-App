package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.profile

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creativeitinstitute.olxcloneanroidapp.core.DataState
import com.creativeitinstitute.olxcloneanroidapp.data.models.Profile
import com.creativeitinstitute.olxcloneanroidapp.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profile: ProfileRepository
): ViewModel(){

    private val _profileUpdateResponse = MutableLiveData<DataState<String>>()
    val profileUpdateResponse : LiveData<DataState<String>> = _profileUpdateResponse

    fun updateProfile(user: Profile, hashLocalImageUrl : Boolean){
        _profileUpdateResponse.postValue(DataState.Loading())

        if (hashLocalImageUrl){
            val imageUri : Uri? = user.userImage?.toUri()

            imageUri?.let {
                profile.uploadImage(it).addOnSuccessListener { snapshot->

                    snapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { url->
                        user.userImage = url.toString()

                        profile.updateUser(user).addOnSuccessListener {
                            _profileUpdateResponse.postValue(DataState.Success(
                                "Uploaded and Updated user Profile Successfully !"
                            ))
                        }.addOnFailureListener {
                            _profileUpdateResponse.postValue(DataState.Error("${it.message}"))
                        }
                    }
                }.addOnFailureListener {
                    _profileUpdateResponse.postValue(DataState.Error("Image Uploaded fail"))
                }
            }
        }else{
            profile.updateUser(user)
                .addOnSuccessListener {
                    _profileUpdateResponse.postValue(DataState.Success(
                        "Uploaded and Updated user Profile Successfully !"
                    ))
                }.addOnFailureListener {
                    _profileUpdateResponse.postValue(DataState.Error("${it.message}"))
                }
        }
    }

    private val _logedInUserResponse = MutableLiveData<DataState<Profile>>()
    val logedInUserResponse : LiveData<DataState<Profile>>
        get() =  _logedInUserResponse

    fun getUserByUserID(userID: String){

        _logedInUserResponse.postValue(DataState.Loading())

        profile.getUserByUserID(userID).addOnSuccessListener { value ->

            _logedInUserResponse.postValue(DataState.Success(
                value.documents[0].toObject(
                    Profile::class.java
                )
            )
            )


        }


    }
}