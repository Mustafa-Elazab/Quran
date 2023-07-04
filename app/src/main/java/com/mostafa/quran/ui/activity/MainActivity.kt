package com.mostafa.quran.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Window
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.mostafa.quran.R
import com.mostafa.quran.di.NetworkUtils
import com.mostafa.quran.ui.cycles.home.main.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private val viewModel: HomeViewModel by viewModels()


    private val locationManager: LocationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Inject
    lateinit var networkUtils: NetworkUtils

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            var isGranted = true
            for (key in it.keys) {
                isGranted = isGranted && it[key]!!
            }

            if (isGranted) {
                if (networkUtils.isNetworkConnected()) {

                    checkGpsStatus()
                } else {
                    lifecycleScope.launch {
                        viewModel.fetchPrayTime(
                            latitude = 0.0,
                            longitude = 0.0
                        )
                    }
                }

            } else {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    provideExplainPermissionDialog().show()
                }
            }
        }


    }



    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        val mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    updateUserLocation(location)
                    updateUserCountry(location)
                    Log.d(TAG, "getUserLocation: location not equal null")
                } else {
                    Log.d(TAG, "getUserLocation: location equal null")
                    val locationRequest = LocationRequest.create()
                    locationRequest.apply {
                        interval = 10000
                        fastestInterval = 5000
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    }

                    val locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            super.onLocationResult(locationResult)
                            locationResult.lastLocation.let {
                                updateUserLocation(it)
                                updateUserCountry(it)
                                Log.d(TAG, "getUserLocation: location request success")
                            }
                        }
                    }

                    mFusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        null
                    )
                }
            }
    }


    private fun updateUserCountry(location: Location?) {
        try {

            viewModel.getLocationAddress(
                this,
                longitude = location!!.longitude,
                latitude = location.latitude
            )
        } catch (e: Exception) {
            Log.d(TAG, "getUserCity: " + e.message)
        }
    }

    private fun updateUserLocation(location: Location?) {
        viewModel.fetchPrayTime(
            latitude = location!!.latitude,
            longitude = location.longitude
        )
    }

    private fun checkGpsStatus() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            lifecycleScope.launchWhenResumed {
                getUserLocation()
            }
        } else {
            buildAlertMessageNoGps()
        }
    }

    fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.enableGPS))
            .setIcon(R.drawable.ic_location)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.enable)) { _, _ ->
                val enableGpsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(enableGpsIntent)
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }


    private fun provideExplainPermissionDialog(): Dialog {
        val dialog = Dialog(this)
        with(dialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.explain_permission_dialog_layout)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val closeDialog = findViewById<AppCompatImageView>(R.id.img_close)
            closeDialog.setOnClickListener { hide() }
            val openPermissionSettings = findViewById<AppCompatButton>(R.id.openPermissionSettings)
            openPermissionSettings.setOnClickListener {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
            create()
        }
        return dialog
    }


    override fun onStart() {
        super.onStart()

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

    }

}