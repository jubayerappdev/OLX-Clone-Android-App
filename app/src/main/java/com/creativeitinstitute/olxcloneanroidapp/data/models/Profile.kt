package com.creativeitinstitute.olxcloneanroidapp.data.models

data class Profile(
    var name:String="",
    var email: String="",
    val password:String="",
    val userID:String="",
    var userImage: String?=null,
    var location: String?=null
)

fun Profile.toMap(): Map<String, Any?>{
    return mapOf(
        "name" to name,
        "email" to email,
        "password" to password,
        "userID" to userID,
        "userImage" to userImage,
        "location" to location
    )
}
