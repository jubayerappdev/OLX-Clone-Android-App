package com.creativeitinstitute.olxcloneanroidapp.data.service

import android.net.Uri
import com.creativeitinstitute.olxcloneanroidapp.core.ImageUploadCallback
import com.creativeitinstitute.olxcloneanroidapp.core.ProductFetchCallback
import com.creativeitinstitute.olxcloneanroidapp.core.ProductSaveCallback
import com.creativeitinstitute.olxcloneanroidapp.data.models.Product
import com.google.firebase.storage.UploadTask

interface ProductService {
    fun uploadImages(images: List<Uri>, callback: ImageUploadCallback)

    fun saveProduct(product: Product, callback: ProductSaveCallback)

    fun getProductByCategory(category: String, callback: ProductFetchCallback)

    fun getAllProducts(callback: ProductFetchCallback)

}