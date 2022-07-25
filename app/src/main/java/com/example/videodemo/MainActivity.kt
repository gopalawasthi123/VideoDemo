package com.example.videodemo

import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.videodemo.databinding.ActivityMainBinding
import com.example.videodemo.databinding.FragmentVideoMainBinding
import com.example.videodemo.ui.SharedViewModel
import com.example.videodemo.util.setVisibility
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
                fragmentVideoMainBinding?.videoTopFrag?.setVisibility(false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
            }
            else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            fragmentVideoMainBinding?.videoTopFrag?.setVisibility(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController?.show(WindowInsets.Type.statusBars())
            }
            else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }

        }
    }
}