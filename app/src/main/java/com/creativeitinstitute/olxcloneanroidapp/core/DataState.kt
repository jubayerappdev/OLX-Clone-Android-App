package com.creativeitinstitute.olxcloneanroidapp.core

sealed class DataState<T>(
    var message: String? = null,
    var data: T? = null
) {
    class Loading<T> : DataState<T>()
    class Success<T>(mData: T?) : DataState<T>(data = mData)
    class Error<T>(message: String?) : DataState<T>(message)


}
sealed class UploadState {
    object Idle : UploadState()
    object UploadingImages : UploadState()
    object SavingProduct : UploadState()
    data class Success(val productId: String) : UploadState()
    data class Error(val message: String) : UploadState()
}