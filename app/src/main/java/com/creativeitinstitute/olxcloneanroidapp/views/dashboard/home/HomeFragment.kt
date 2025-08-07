package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.home

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import coil.load
import com.bumptech.glide.Glide
import com.creativeitinstitute.olxcloneanroidapp.R
import com.creativeitinstitute.olxcloneanroidapp.base.BaseFragment
import com.creativeitinstitute.olxcloneanroidapp.core.Nodes
import com.creativeitinstitute.olxcloneanroidapp.data.models.Profile
import com.creativeitinstitute.olxcloneanroidapp.databinding.FragmentHomeBinding
import com.creativeitinstitute.olxcloneanroidapp.views.dashboard.profile.ProfileFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    lateinit var user: Profile

    override fun setListener() {

//        val storageReference = FirebaseStorage.getInstance().reference
//        val imageRef = user.userImage?.let { storageReference.child(it) }
//
//        imageRef?.downloadUrl?.addOnSuccessListener { uri ->
//
//            Glide.with(this)
//                .load(uri)
//                .into(binding.ivProfilePic)
//
//
//        }?.addOnFailureListener {
//            Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show()
//        }


        with(binding){
            ivProfilePic.setOnClickListener {
                findNavController().navigate(R.id.action_myHomeFragment_to_myProfileFragment)
            }
        }





    }

    override fun allObserver() {


    }
}