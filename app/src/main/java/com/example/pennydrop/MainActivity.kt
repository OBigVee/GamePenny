package com.example.pennydrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_pick_players.*


//import com.google.android.material.bottomnavigation.BottomNavigationItemView
//import androidx.navigation.ui.AppBarConfiguration
//import androidx.navigation.ui.setupActionBarWithNavController



class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.containerFragment) as
                NavHostFragment
        this.navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav).bottom_nav.setupWithNavController(
            this.navController)



//        this.buttonPlayGame.setOnClickListener {
//            Toast.makeText()
//        }
    }
}


