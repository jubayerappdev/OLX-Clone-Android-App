package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.home

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.creativeitinstitute.olxcloneanroidapp.R
import com.creativeitinstitute.olxcloneanroidapp.base.BaseFragment
import com.creativeitinstitute.olxcloneanroidapp.core.DataState
import com.creativeitinstitute.olxcloneanroidapp.core.Nodes
import com.creativeitinstitute.olxcloneanroidapp.data.models.Profile
import com.creativeitinstitute.olxcloneanroidapp.databinding.FragmentHomeBinding
import com.creativeitinstitute.olxcloneanroidapp.views.dashboard.post.ProductViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private var profile: Profile? = null
    private val viewModel: HomeViewModel by viewModels ()
    private val productViewModel: ProductViewModel by viewModels ()
    private lateinit var productListAdapter: ProductListAdapter
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
        val category = requireActivity().intent.getStringExtra(Nodes.CATEGORY)
        setupRecyclerView()
        if (category != null){
            productViewModel.loadProductsByCategory(category)
        }else{
            productViewModel.loadAllProducts()
        }





    }

    private fun setupRecyclerView() {
        productListAdapter = ProductListAdapter()
        with(binding){
            recyclerViewProducts.adapter = productListAdapter
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
        productViewModel.products.observe(this){products->
            productListAdapter.setProducts(products)

        }
        productViewModel.error.observe(this){errorMessage->
            if (errorMessage.isNotEmpty()){
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }

        }
    }
    private fun setProfileData(profile: Profile?) {
        hashLocalImageUrl = profile?.userImage.isNullOrBlank()

        Glide.with(this)
            .load(profile?.userImage) // your Firebase image URL
            .placeholder(R.drawable.ic_profile) // shown while loading
            .error(R.drawable.ic_user)       // shown if URL is null or fails
            .into(binding.ivProfilePic)

    }
}