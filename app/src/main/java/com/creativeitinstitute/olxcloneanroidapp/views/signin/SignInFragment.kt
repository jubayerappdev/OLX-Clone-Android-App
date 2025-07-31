package com.creativeitinstitute.olxcloneanroidapp.views.signin

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.creativeitinstitute.olxcloneanroidapp.R
import com.creativeitinstitute.olxcloneanroidapp.base.BaseFragment
import com.creativeitinstitute.olxcloneanroidapp.core.DataState
import com.creativeitinstitute.olxcloneanroidapp.data.models.UserLogin
import com.creativeitinstitute.olxcloneanroidapp.databinding.FragmentSignInBinding
import com.creativeitinstitute.olxcloneanroidapp.extract
import com.creativeitinstitute.olxcloneanroidapp.isEmpty
import com.creativeitinstitute.olxcloneanroidapp.views.dashboard.UserDashboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    private val viewModel : SignInViewModel by viewModels ()

    override fun setListener() {

        with(binding){
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
            btnLogin.setOnClickListener {
                etEmail.isEmpty()
                etPassword.isEmpty()
                if (!etEmail.isEmpty() && !etPassword.isEmpty()){

                    val user = UserLogin(
                        etEmail.extract(),
                        etPassword.extract()
                    )
                    viewModel.userLogin(user)
                    loading.show()
                }
            }
        }



    }

    override fun allObserver() {
        viewModel.loginResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(context, it.message,Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    loading.show()
                    Toast.makeText(context, "Loading....",Toast.LENGTH_SHORT).show()
                }
                is DataState.Success<*> -> {
                    loading.dismiss()
                    Toast.makeText(context,"User logged in : ${it.data}",Toast.LENGTH_SHORT).show()

                    startActivity(Intent(requireContext(), UserDashboard::class.java))
                    requireActivity().finish()
                }
            }
        }


    }


}