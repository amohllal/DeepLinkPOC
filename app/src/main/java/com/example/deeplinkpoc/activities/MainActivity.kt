package com.example.deeplinkpoc.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.deeplinkpoc.R

class MainActivity : AppCompatActivity() {
    private val navController by lazy { findNavController(R.id.fragment_container_view_tag) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent?.let{ navController.handleDeepLink(it) }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { navController.handleDeepLink(it) }
    }
}