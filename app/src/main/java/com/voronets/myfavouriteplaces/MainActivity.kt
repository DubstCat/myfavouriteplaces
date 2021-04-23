package com.voronets.myfavouriteplaces

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),OnMapReadyCallback {

    private val places: ArrayList<LatLng> = ArrayList()
    lateinit var lng: LatLng
    lateinit var bottomSheetBehavior:BottomSheetBehavior<FrameLayout>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomSheet = findViewById<FrameLayout>(R.id.containerBottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        places.add(LatLng(55.754724, 37.621380));
        places.add(LatLng(55.760133, 37.618697));
        places.add(LatLng(55.764753, 37.591313));

        val fab = findViewById<FloatingActionButton>(R.id.fab);

        val coordinatorLayout = findViewById<CoordinatorLayout>(R.id.coordinator)

        bottomSheetBehavior.isHideable = false
        // настройка колбэков при изменениях
        bottomSheetBehavior.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback(){
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    fab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
                }
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }
            })

        val mapFragment: SupportMapFragment? = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val markers = arrayOfNulls<MarkerOptions>(places.size)
        for (i in places.indices) {
            markers[i] = MarkerOptions()
                .position(places[i])
            googleMap!!.addMarker(markers[i])
        }
        googleMap!!.setOnMapClickListener {
            val markerOptions = MarkerOptions()
            markerOptions.position(it)
            markerOptions.title(it.latitude.toString() + " : " + it.longitude)
            googleMap.clear()
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(it))
            googleMap.addMarker(markerOptions)
            lng = it
        }
    }

}
