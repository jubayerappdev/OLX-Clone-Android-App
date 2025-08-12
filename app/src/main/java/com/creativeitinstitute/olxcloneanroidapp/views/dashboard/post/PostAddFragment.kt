package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.post

import android.content.Intent
import com.creativeitinstitute.olxcloneanroidapp.base.BaseFragment
import com.creativeitinstitute.olxcloneanroidapp.databinding.FragmentPostAddBinding
import com.creativeitinstitute.olxcloneanroidapp.views.dashboard.UserDashboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostAddFragment : BaseFragment<FragmentPostAddBinding>(FragmentPostAddBinding::inflate) {
    override fun setListener() {


        with(binding){
            btnBack.setOnClickListener {
                startActivity(Intent(requireContext(), UserDashboard::class.java))
                requireActivity().finish()
            }
        }

    }

    override fun allObserver() {

    }
}