package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.profile

import android.Manifest
import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.creativeitinstitute.olxcloneanroidapp.R
import com.creativeitinstitute.olxcloneanroidapp.base.BaseFragment
import com.creativeitinstitute.olxcloneanroidapp.core.DataState
import com.creativeitinstitute.olxcloneanroidapp.core.areAllPermissionsGranted
import com.creativeitinstitute.olxcloneanroidapp.core.extract
import com.creativeitinstitute.olxcloneanroidapp.core.requestPermissions
import com.creativeitinstitute.olxcloneanroidapp.data.models.Profile
import com.creativeitinstitute.olxcloneanroidapp.databinding.FragmentEditProfileBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {
    private var profile: Profile? = null
    private val viewModel : ProfileViewModel by viewModels()

    private var hashLocalImageUrl :Boolean = false
    override fun setListener() {
        FirebaseAuth.getInstance().currentUser?.let {
            viewModel.getUserByUserID(it.uid)
        }
        permissionsRequest = getPermissionsRequest()
        with(binding){
            ivProfilePic.setOnClickListener {
                requestPermissions(permissionsRequest, permissionList)
            }
            btnUpdate.setOnClickListener {
                loading.show()
                val name = etName.extract()
                val location = etLocation.extract()

                profile.apply {
                    this?.name = name
                    this?.location = location
                }
                updateProfile(profile)
            }
            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_editProfileFragment_to_myProfileFragment)

            }
        }

    }
    private fun updateProfile(profile: Profile?) {

        profile?.let {
            viewModel.updateProfile(it, hashLocalImageUrl)
        }
    }

    override fun allObserver() {
        viewModel.profileUpdateResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error -> {
                    loading.dismiss()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    Toast.makeText(requireContext(), it.data,Toast.LENGTH_LONG).show()
                    loading.dismiss()
                }
            }
        }
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
            etName.setText(profile?.name)
            etLocation.setText(profile?.location)
            ivProfilePic.load(profile?.userImage)
        }

    }

    companion object{
        private val permissionList = if (Build.VERSION.SDK_INT >=33){
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        }else{
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }


    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                Log.d("TAG", "$fileUri")
                binding.ivProfilePic.setImageURI(fileUri)
                profile?.userImage = fileUri.toString()
                hashLocalImageUrl = true



            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    private fun getPermissionsRequest(): ActivityResultLauncher<Array<String>> {

        return  registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){

            if (areAllPermissionsGranted(permissionList)){

                ImagePicker.with(this)
                    .compress(1024)         //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(512, 512)  //Final image resolution will be less than 1080 x 1080(Optional)
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }


            }


        }

    }


}