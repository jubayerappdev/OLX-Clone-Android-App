package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creativeitinstitute.olxcloneanroidapp.core.DataState
import com.creativeitinstitute.olxcloneanroidapp.data.models.Profile
import com.creativeitinstitute.olxcloneanroidapp.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val profile: ProfileRepository
): ViewModel(){


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