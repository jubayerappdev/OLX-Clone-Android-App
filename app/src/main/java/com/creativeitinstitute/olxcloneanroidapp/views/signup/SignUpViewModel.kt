package com.creativeitinstitute.olxcloneanroidapp.views.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creativeitinstitute.olxcloneanroidapp.core.DataState
import com.creativeitinstitute.olxcloneanroidapp.data.models.UserRegistration
import com.creativeitinstitute.olxcloneanroidapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authService: AuthRepository
) : ViewModel() {
    private val _registrationResponse = MutableLiveData<DataState<UserRegistration>>()
    val registrationResponse: LiveData<DataState<UserRegistration>> = _registrationResponse

    fun userRegistration(user: UserRegistration){
        _registrationResponse.postValue(DataState.Loading())


        authService.userRegistration(user).addOnSuccessListener {
            it.user?.let { createUser->
                user.userID = createUser.uid

                authService.createUser(user).addOnSuccessListener {
                    _registrationResponse.postValue(DataState.Success(user))
                }.addOnFailureListener {error->
                    _registrationResponse.postValue(DataState.Error("${error.message}"))

                }
            }
        }.addOnFailureListener {error->
            _registrationResponse.postValue(DataState.Error("${error.message}"))

        }
    }
}