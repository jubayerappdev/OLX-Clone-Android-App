package com.creativeitinstitute.olxcloneanroidapp.views.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creativeitinstitute.olxcloneanroidapp.core.DataState
import com.creativeitinstitute.olxcloneanroidapp.data.models.UserLogin
import com.creativeitinstitute.olxcloneanroidapp.data.models.UserRegistration
import com.creativeitinstitute.olxcloneanroidapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authService: AuthRepository
) : ViewModel() {
    private val _loginResponse = MutableLiveData<DataState<UserLogin>>()
    val loginResponse: LiveData<DataState<UserLogin>> = _loginResponse

    fun userLogin(user: UserLogin){
        _loginResponse.postValue(DataState.Loading())


        authService.userLogin(user).addOnSuccessListener {
            _loginResponse.postValue(DataState.Success(user))

        }.addOnFailureListener {error->
            _loginResponse.postValue(DataState.Error("${error.message}"))

        }
    }
}