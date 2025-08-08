package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.creativeitinstitute.olxcloneanroidapp.R
import com.creativeitinstitute.olxcloneanroidapp.base.BaseFragment
import com.creativeitinstitute.olxcloneanroidapp.core.DataState
import com.creativeitinstitute.olxcloneanroidapp.data.models.Profile
import com.creativeitinstitute.olxcloneanroidapp.databinding.FragmentProfileBinding
import com.creativeitinstitute.olxcloneanroidapp.views.starter.MainActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private var profile: Profile? = null
    private val viewModel : ProfileViewModel by viewModels()

    private var hashLocalImageUrl :Boolean = false

    @Inject
    lateinit var jAuth: FirebaseAuth

    override fun setListener() {
        FirebaseAuth.getInstance().currentUser?.let {
            viewModel.getUserByUserID(it.uid)
        }

        with(binding){
            btnLogout.setOnClickListener {
                jAuth.signOut()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
            tvNavEditProfile.setOnClickListener {
                findNavController().navigate(R.id.action_myProfileFragment_to_editProfileFragment)
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
            tvUserName.setText(profile?.name)
            tvLocation.setText(profile?.location)
            ivProfilePic.load(profile?.userImage)
        }

    }

}


