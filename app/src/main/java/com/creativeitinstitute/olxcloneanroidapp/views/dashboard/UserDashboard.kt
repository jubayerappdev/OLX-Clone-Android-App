package com.creativeitinstitute.olxcloneanroidapp.views.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.creativeitinstitute.olxcloneanroidapp.R
import com.creativeitinstitute.olxcloneanroidapp.databinding.ActivityUserDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDashboard : AppCompatActivity() {

    private lateinit var binding: ActivityUserDashboardBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController= findNavController(R.id.fragmentContainerView1)

       appBarConfiguration = AppBarConfiguration(setOf(
            R.id.myHomeFragment,
            R.id.myPostAddFragment,
            R.id.myChatFragment
        ))

        binding.bottomNavigationView.setupWithNavController(navController)
//        setupActionBarWithNavController(navController, appBarConfiguration)

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}