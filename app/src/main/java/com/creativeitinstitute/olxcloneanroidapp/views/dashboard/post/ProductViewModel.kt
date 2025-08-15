package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creativeitinstitute.olxcloneanroidapp.core.DataState
import com.creativeitinstitute.olxcloneanroidapp.core.ImageUploadCallback
import com.creativeitinstitute.olxcloneanroidapp.core.ProductFetchCallback
import com.creativeitinstitute.olxcloneanroidapp.core.ProductSaveCallback
import com.creativeitinstitute.olxcloneanroidapp.core.UploadState
import com.creativeitinstitute.olxcloneanroidapp.data.models.Product
import com.creativeitinstitute.olxcloneanroidapp.data.repository.ProductRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepo: ProductRepository
): ViewModel() {
    private val _uploadStatus = MutableLiveData<UploadState>()
    val uploadStatus: LiveData<UploadState> = _uploadStatus

    private val _uploadProgress = MutableLiveData<Pair<Int, Int>>()
    val uploadProgress: LiveData<Pair<Int, Int>> = _uploadProgress

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun uploadProduct(product: Product, images: List<Uri>){

        _uploadStatus.value = UploadState.UploadingImages
        productRepo.uploadImages(images, object : ImageUploadCallback{
            override fun onSuccess(imageUrls: List<String>) {
                _uploadStatus.value = UploadState.SavingProduct
                val updateProduct = product.copy(imageLink = imageUrls)

                productRepo.saveProduct(updateProduct, object : ProductSaveCallback{
                    override fun onSuccess(productId: String) {
                        _uploadStatus.value = UploadState.Success(productId)
                    }

                    override fun onFailure(error: String) {
                        _uploadStatus.value = UploadState.Error(error)
                    }

                })

            }

            override fun onFailure(error: String) {
                _uploadStatus.value = UploadState.Error(error)

            }

            override fun onProgress(progress: Int, total: Int) {
                _uploadProgress.value = Pair(progress, total)

            }
        })




    }
    fun loadProductsByCategory(category: String) {
        productRepo.getProductByCategory(category, object : ProductFetchCallback {
            override fun onSuccess(products: List<Product>) {
                _products.value = products
            }

            override fun onFailure(error: String) {
                _error.value = error
            }
        })
    }
    fun loadAllProducts() {
        productRepo.getAllProducts(object : ProductFetchCallback {
            override fun onSuccess(products: List<Product>) {
                _products.value = products
            }

            override fun onFailure(error: String) {
                _error.value = error
            }
        })
    }
}