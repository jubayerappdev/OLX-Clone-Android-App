package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.creativeitinstitute.olxcloneanroidapp.R
import com.creativeitinstitute.olxcloneanroidapp.base.BaseFragment
import com.creativeitinstitute.olxcloneanroidapp.core.DataState
import com.creativeitinstitute.olxcloneanroidapp.data.models.Profile
import com.creativeitinstitute.olxcloneanroidapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private var profile: Profile? = null
    private val viewModel: HomeViewModel by viewModels ()
    private var hashLocalImageUrl :Boolean = false
    override fun setListener() {
        FirebaseAuth.getInstance().currentUser?.let {
            viewModel.getUserByUserID(it.uid)
        }

        with(binding){
            ivProfilePic.setOnClickListener {
                findNavController().navigate(R.id.action_myHomeFragment_to_myProfileFragment)
            }
        }


    }

    override fun allObserver() {

        viewModel.logedInUserResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error -> {
                    loading.dismiss()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    profile = it.data
                    setProfileData(profile)
                    loading.dismiss()
                }
            }
        }
    }
    private fun setProfileData(profile: Profile?) {
        hashLocalImageUrl = profile?.userImage.isNullOrBlank()

        binding.apply {
            ivProfilePic.load(profile?.userImage)
        }

    }
}