package com.mostafa.quran.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager


fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        statusBarColor = Color.TRANSPARENT
    }
}


fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}


fun Activity.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.setDecorFitsSystemWindows(false)
    } else {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}

fun isConnectedToInternet(context: Context): Boolean{
    var isConnected = false
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    isConnected = true
                } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    isConnected = true
                } else if (hasTransport(NetworkCapabilities.TRANSPORT_VPN)){
                    isConnected = true
                }
            }
        }
    }else{
        cm?.run {
            cm.activeNetworkInfo?.run {
                when(type){
                    ConnectivityManager.TYPE_WIFI -> {
                        isConnected = true
                    }
                    ConnectivityManager.TYPE_MOBILE -> {
                        isConnected = true
                    }
                    ConnectivityManager.TYPE_VPN -> {
                        isConnected = true
                    }
                }
            }
        }
    }
    return isConnected
}

