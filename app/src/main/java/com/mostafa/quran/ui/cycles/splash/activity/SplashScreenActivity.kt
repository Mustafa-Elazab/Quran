package com.mostafa.quran.ui.cycles.splash.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.mostafa.quran.R
import com.mostafa.quran.databinding.ActivitySplashScreenBinding
import com.mostafa.quran.ui.activity.MainActivity
import com.mostafa.quran.utils.makeStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        binding.splashImg.startAnimation(slideAnimation)


        makeStatusBarTransparent()

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenResumed {
            delay(1000)
            openHomeActivity()
        }
    }

    private fun openHomeActivity() {
        Intent(
            this,
            MainActivity::class.java
        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK).also {
            startActivity(it)
        }
    }
}