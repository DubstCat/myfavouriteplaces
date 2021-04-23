package com.voronets.myfavouriteplaces

import android.os.Bundle
import android.widget.FrameLayout

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity(),OnMapReadyCallback {

    private val places: ArrayList<LatLng> = ArrayList()
    lateinit var bottomSheetBehavior:BottomSheetBehavior<FrameLayout>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomSheet = findViewById<FrameLayout>(R.id.containerBottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        places.add(LatLng(55.754724, 37.621380));
        places.add(LatLng(55.760133, 37.618697));
        places.add(LatLng(55.764753, 37.591313));

        val fab = findViewById(R.id.fab);

        // настройка колбэков при изменениях
        bottomSheetBehavior.addBottomSheetCallback(BottomSheetBehavior.BottomSheetCallback)

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
    }
}
