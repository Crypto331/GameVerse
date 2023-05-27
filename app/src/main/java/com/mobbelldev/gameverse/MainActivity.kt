package com.mobbelldev.gameverse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.mobbelldev.gameverse.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav = binding.bottomNavigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigation_host_main) as NavHostFragment
        NavigationUI.setupWithNavController(
            bottomNav,
            navHostFragment.navController
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.root.removeAllViews()
        _binding = null
    }
}