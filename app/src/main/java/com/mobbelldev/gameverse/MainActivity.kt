package com.mobbelldev.gameverse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.mobbelldev.gameverse.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bottomNav = binding.bottomNavigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigation_host_main) as NavHostFragment
        NavigationUI.setupWithNavController(
            bottomNav,
            navHostFragment.navController
        )
    }
}