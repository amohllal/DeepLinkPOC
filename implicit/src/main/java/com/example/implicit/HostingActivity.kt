package com.example.implicit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController


class HostingActivity : AppCompatActivity() {
    private val navController by lazy { findNavController(R.id.fragmentContainerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hosting)
        intent?.let{ navController.handleDeepLink(it) }

    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { navController.handleDeepLink(it) }
    }
}