package com.creativeitinstitute.olxcloneanroidapp.core

import com.creativeitinstitute.olxcloneanroidapp.data.models.Product

interface ImageUploadCallback {
    fun onSuccess(imageUrls: List<String>)
    fun onFailure(error: String)
    fun onProgress(progress: Int, total: Int)
}

interface ProductSaveCallback {
    fun onSuccess(productId: String)
    fun onFailure(error: String)
}
interface ProductFetchCallback {
    fun onSuccess(products: List<Product>)
    fun onFailure(error: String)
}