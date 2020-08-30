package com.lazday.poslaravel.activity.agent

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lazday.poslaravel.R
import com.lazday.poslaravel.data.Constant

class AgentMapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val tagLog: String = "MapsActivity"

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private val marker = MarkerOptions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_maps)
        supportActionBar!!.title = "Lokasi"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))

                Log.d(tagLog, "latitude: ${location.latitude} longitude: ${location.longitude}" )

                Constant.LATITUDE = location.latitude.toString()
                Constant.LONGITUDE = location.longitude.toString()

                marker.position(currentLatLng)
                googleMap.addMarker(marker)
            }
        }
        mMap.setOnMapClickListener { latLng: LatLng ->
            marker.position(latLng)
            marker.title(latLng.latitude.toString() + " : " + latLng.longitude)

            Log.d(tagLog, "latitude: ${latLng.latitude} longitude: ${latLng.longitude}" )

            Constant.LATITUDE = latLng.latitude.toString()
            Constant.LONGITUDE = latLng.longitude.toString()

            googleMap.clear()
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            googleMap.addMarker(marker)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_maps, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                Constant.LOCATION_SELECTED = true
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}
