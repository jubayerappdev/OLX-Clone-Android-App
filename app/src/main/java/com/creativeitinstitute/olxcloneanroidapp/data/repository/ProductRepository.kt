package com.creativeitinstitute.olxcloneanroidapp.data.repository

import android.net.Uri
import com.creativeitinstitute.olxcloneanroidapp.core.ImageUploadCallback
import com.creativeitinstitute.olxcloneanroidapp.core.Nodes
import com.creativeitinstitute.olxcloneanroidapp.core.ProductFetchCallback
import com.creativeitinstitute.olxcloneanroidapp.core.ProductSaveCallback
import com.creativeitinstitute.olxcloneanroidapp.data.models.Product
import com.creativeitinstitute.olxcloneanroidapp.data.service.ProductService
import com.google.common.collect.Multimaps.index
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val jAuth : FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storageRef: StorageReference

) : ProductService {
    override fun uploadImages(
        images: List<Uri>,
        callback: ImageUploadCallback
    ) {
        if (images.isEmpty()) {
            callback.onFailure("No images selected")
            return
        }
        val imageUrls = mutableListOf<String>()
        var uploadedCount = 0
        val totalImages = images.size
        var hasError = false

        images.forEachIndexed { index, uri ->
            val imageRef= storageRef.child(Nodes.PRODUCT).child("PRD_ ${System.currentTimeMillis()}")
            imageRef.putFile(uri).addOnSuccessListener {
                if (!hasError){
                    imageRef.downloadUrl.addOnSuccessListener { downloadUri->
                        imageUrls.add(downloadUri.toString())
                        uploadedCount++
                        callback.onProgress(uploadedCount, totalImages)

                        if (uploadedCount==totalImages){
                            callback.onSuccess(imageUrls)
                        }

                    }.addOnFailureListener { exception ->
                        if (!hasError) {
                            hasError = true
                            callback.onFailure("Failed to get download URL: ${exception.message}")
                        }
                    }
                }
            }  .addOnFailureListener { exception ->
                if (!hasError) {
                    hasError = true
                    callback.onFailure("Failed to upload image: ${exception.message}")
                }
            }
        }

    }

    override fun saveProduct(
        product: Product,
        callback: ProductSaveCallback
    ) {
        val productId = db.collection(Nodes.PRODUCT).document().id
        product.productID = productId
        product.userID = jAuth.currentUser?.uid?:""
        db.collection(Nodes.PRODUCT).document(productId).set(product).addOnSuccessListener {
            callback.onSuccess(productId)
        }.addOnFailureListener { exception ->

            callback.onFailure("Failed to save product: ${exception.message}")
        }
    }

    override fun getProductByCategory(
        category: String,
        callback: ProductFetchCallback
    ) {
        db.collection(Nodes.PRODUCT).whereEqualTo(Nodes.CATEGORY, category).get().addOnSuccessListener { snapshot->
            val product = snapshot.documents.mapNotNull { document->
                try {
                    document.toObject(Product::class.java)
                }catch (e: Exception){
                    null
                }
            }
            callback.onSuccess(product)

        }.addOnFailureListener { exception ->
            callback.onFailure("Failed to fetch products: ${exception.message}")
        }
    }

    override fun getAllProducts(callback: ProductFetchCallback) {
        db.collection(Nodes.PRODUCT).get().addOnSuccessListener { snapshots ->
            val product = snapshots.documents.mapNotNull { document->
                try {
                    document.toObject(Product::class.java)
                }catch (e: Exception){
                    null
                }
            }
            callback.onSuccess(product)

        }
            .addOnFailureListener { exception ->
                callback.onFailure("Failed to fetch products: ${exception.message}")
            }
    }
}