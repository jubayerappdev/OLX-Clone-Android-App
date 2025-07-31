package com.creativeitinstitute.olxcloneanroidapp.views.signup


import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController
import com.creativeitinstitute.olxcloneanroidapp.R
import com.creativeitinstitute.olxcloneanroidapp.base.BaseFragment
import com.creativeitinstitute.olxcloneanroidapp.core.DataState

import com.creativeitinstitute.olxcloneanroidapp.data.models.UserRegistration
import com.creativeitinstitute.olxcloneanroidapp.databinding.FragmentSignUpBinding
import com.creativeitinstitute.olxcloneanroidapp.extract

import com.creativeitinstitute.olxcloneanroidapp.isEmpty
import com.creativeitinstitute.olxcloneanroidapp.views.dashboard.UserDashboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val viewModel: SignUpViewModel by viewModels()
    override fun setListener() {

        with(binding){
            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }
            btnRegister.setOnClickListener {
                etName.isEmpty()
                etEmail.isEmpty()
                etPassword.isEmpty()

                if (!etName.isEmpty() && !etEmail.isEmpty() && !etPassword.isEmpty()){

                    val user = UserRegistration(
                        etName.extract(),
                        etEmail.extract(),
                        etPassword.extract(),
                        ""
                    )

                    viewModel.userRegistration(user)

                }
            }
        }


    }

    override fun allObserver() {
        viewModel.registrationResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(context, it.message,Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading<*> -> {
                    loading.show()
                    Toast.makeText(context, "Loading....",Toast.LENGTH_SHORT).show()
                }
                is DataState.Success<*> -> {
                    loading.dismiss()
                    Toast.makeText(context,"created User : ${it.data}",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), UserDashboard::class.java))
                    requireActivity().finish()
                }
            }
        }

    }


}