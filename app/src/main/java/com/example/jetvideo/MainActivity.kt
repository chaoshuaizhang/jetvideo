package com.example.jetvideo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.jetvideo.databinding.ActivityMainBinding
import com.example.jetvideo.util.HideShowFragNavigator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val navController = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
            navController.navigatorProvider.addNavigator("hideShowFragNavigator", HideShowFragNavigator(this, supportFragmentManager, R.id.container))
            navController.setGraph(R.navigation.nav_graph)
            binding.bottomNav.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                    R.id.navigation_soft -> navController.navigate(R.id.softFragment)
                }
                true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
