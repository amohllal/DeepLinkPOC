package com.example.implicit

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_hosting.*


class HostingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hosting)
        initView()
    }

    private fun initView() {
        val bundle = Bundle()
        bundle.putString("productId", getIdFromUrl())
        setupNavigationGraph(bundle)
    }

    private fun getIdFromUrl(): String? {
        val data = intent.getParcelableExtra<Uri>("deeplink")
        val path: String? = data?.path
        return path?.substring(path.lastIndexOf('/') + 1)
    }

    private fun setupNavigationGraph(bundle: Bundle) {
        val myNavHostFragment: NavHostFragment = fragmentContainerView as NavHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.implicit_nav_graph)
        myNavHostFragment.navController.setGraph(graph, bundle)
    }
}