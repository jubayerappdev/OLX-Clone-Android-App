package com.creativeitinstitute.olxcloneanroidapp.data.models

data class Product(
    var title: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var imageLink: List<String> = emptyList(),
    var location: String = "",
    var category: String = "",
    var userID: String = "",
    var productID: String = "",
    var createdAt:Long = System.currentTimeMillis()
)
