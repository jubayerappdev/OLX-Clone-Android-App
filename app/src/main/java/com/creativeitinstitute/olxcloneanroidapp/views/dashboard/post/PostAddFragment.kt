package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.post

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.creativeitinstitute.olxcloneanroidapp.base.BaseFragment
import com.creativeitinstitute.olxcloneanroidapp.core.UploadState
import com.creativeitinstitute.olxcloneanroidapp.core.extract
import com.creativeitinstitute.olxcloneanroidapp.data.models.Product
import com.creativeitinstitute.olxcloneanroidapp.databinding.FragmentPostAddBinding
import com.creativeitinstitute.olxcloneanroidapp.views.dashboard.UserDashboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostAddFragment : BaseFragment<FragmentPostAddBinding>(FragmentPostAddBinding::inflate) {
    private val product: Product by lazy {
        Product()
    }
    private val viewModel : ProductViewModel by viewModels ()
    private lateinit var imageAdapter: ProductImageAdapter

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        uris?.let {
            it.forEach { uri -> imageAdapter.addImage(uri) }
        }
    }

    override fun setListener() {


        imageAdapter = ProductImageAdapter()
        binding.recyclerViewImages.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerViewImages.adapter = imageAdapter


        with(binding){
            btnAddImages.setOnClickListener {
                imagePickerLauncher.launch("image/*")
            }
            btnUpload.setOnClickListener {
                product.apply {
                    this.title = etTitle.extract()
                    this.description = etDescription.extract()
                    this.price = etPrice.extract().toDouble()
                    this.location = etLocation.extract()
                    this.category = spinnerCategory.text.toString()
                }

                if (validateInput(product) && imageAdapter.getImages().isNotEmpty()) {
                    viewModel.uploadProduct(product, imageAdapter.getImages())
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields and add at least one image", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }
    private fun validateInput(product: Product): Boolean {
        return product.title.isNotBlank() &&
                product.description.isNotBlank() &&
                product.price > 0 &&
                product.location.isNotBlank()
    }


    override fun allObserver() {
        viewModel.uploadStatus.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UploadState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnUpload.isEnabled = true
                    binding.tvProgress.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${state.message}", Toast.LENGTH_LONG).show()
                }
               is UploadState.Idle -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnUpload.isEnabled = true
                    binding.tvProgress.visibility = View.GONE
                }
               is UploadState.SavingProduct -> {
                    binding.tvProgress.text = "Saving product..."
                }
                is UploadState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnUpload.isEnabled = true
                    binding.tvProgress.visibility = View.GONE
                    Toast.makeText(requireContext(), "Product uploaded successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), UserDashboard::class.java))
                    requireActivity().finish()
                }
               is UploadState.UploadingImages -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnUpload.isEnabled = false
                    binding.tvProgress.visibility = View.VISIBLE
                    binding.tvProgress.text = "Uploading images..."
                }
            }
        }

        viewModel.uploadProgress.observe(viewLifecycleOwner) { (current, total) ->
            binding.tvProgress.text = "Uploading images: $current/$total"
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }

    }
}