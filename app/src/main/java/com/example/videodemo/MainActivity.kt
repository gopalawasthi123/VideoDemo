package com.example.videodemo

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.videodemo.databinding.ActivityMainBinding
import com.example.videodemo.databinding.FragmentVideoMainBinding
import com.example.videodemo.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var fragmentVideoMainBinding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding = ActivityMainBinding.inflate(layoutInflater)
        fragmentVideoMainBinding = binding
        val rootView = binding.root
        setContentView(rootView)


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
                fragmentVideoMainBinding?.videoTopFrag?.visibility = View.GONE
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            fragmentVideoMainBinding?.videoTopFrag?.visibility = View.VISIBLE

        }
    }
}