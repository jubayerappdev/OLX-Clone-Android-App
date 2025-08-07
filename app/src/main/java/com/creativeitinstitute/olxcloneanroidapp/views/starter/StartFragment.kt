package com.creativeitinstitute.olxcloneanroidapp.views.starter

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.creativeitinstitute.olxcloneanroidapp.R
import com.creativeitinstitute.olxcloneanroidapp.base.BaseFragment
import com.creativeitinstitute.olxcloneanroidapp.core.DataState
import com.creativeitinstitute.olxcloneanroidapp.databinding.FragmentStartBinding
import com.creativeitinstitute.olxcloneanroidapp.views.dashboard.UserDashboard
import com.creativeitinstitute.olxcloneanroidapp.views.signin.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {

    private val viewModel: SignInViewModel by viewModels ()
    override fun setListener() {
        setUpAutoLogin()

        with(binding){
            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_signInFragment)
            }
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_signUpFragment)
            }
        }
    }

    private fun setUpAutoLogin() {
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (user != null){
            binding.apply {
                layoutLoading.visibility = View.VISIBLE
                layoutMain.visibility = View.GONE

            }
            startActivity(Intent(requireContext(), UserDashboard::class.java))

        }else{
            binding.apply {
                layoutLoading.visibility = View.GONE
                layoutMain.visibility = View.VISIBLE
            }
        }


//        FirebaseAuth.getInstance().currentUser?.let {
//
//
//        }
    }

    override fun allObserver() {

    }
}