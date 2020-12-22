package com.example.eventapp.view.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.eventapp.R
import com.google.android.gms.location.*
import java.util.jar.Manifest


@Suppress("CAST_NEVER_SUCCEEDS")
class HomeActivity : AppCompatActivity() {

   lateinit var mFusedLocationClient: FusedLocationProviderClient

    // Initializing other items
    // from layout file
    var latitudeTextView: Double = 0.0
    var longitTextView: Double = 0.0

    // Initializing other items
    // from layout file
    var PERMISSION_ID = 44

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation();
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last location from FusedLocationClient object
                mFusedLocationClient?.lastLocation?.addOnCompleteListener { task ->
                    val location: Location = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        latitudeTextView = location.latitude
                        longitTextView = location.longitude

                        Log.e("latitude", latitudeTextView.toString())
                        Log.e("longtitude", longitTextView.toString())

                    }
                }
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(
            locationResult: LocationResult
        ) {
            val mLastLocation = locationResult.lastLocation
            latitudeTextView = mLastLocation.latitude
            longitTextView = mLastLocation.longitude
            println(latitudeTextView.toString())
           println( longitTextView.toString())
        }
    }

    private fun checkPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        /* ActivityCompat
                .checkSelfPermission(
                    this,
                    Manifest.permission
                        .ACCESS_BACKGROUND_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        */
    }

    // method to request for permissions
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    // method to check
    // if location is enabled
    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager
        return (locationManager
            .isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )
                || locationManager
            .isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            ))
    }

    // If everything is alright then
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super
            .onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty()
                && grantResults[0]
                == PackageManager.PERMISSION_GRANTED
            ) {
                getLastLocation()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            getLastLocation()
        }
    }
}