package com.voronets.myfavouriteplaces

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.voronets.myfavouriteplaces.data.Point
import com.voronets.myfavouriteplaces.data.PointViewModel

class MainActivity : AppCompatActivity(),OnMapReadyCallback {
    private val places: ArrayList<LatLng> = ArrayList()
    private var lng: LatLng? = null
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomSheetBehavior:BottomSheetBehavior<LinearLayout>
    private lateinit var mPointViewModel: PointViewModel
    private lateinit var etName:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomSheet = findViewById<LinearLayout>(R.id.containerBottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        mPointViewModel = ViewModelProvider(this).get(PointViewModel::class.java)
        refreshList()

        etName = findViewById(R.id.et_name)
        fab = findViewById<FloatingActionButton>(R.id.fab);

        bottomSheetBehavior.isHideable = false
        // настройка колбэков при изменениях
        bottomSheetBehavior.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback(){
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    fab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
                    etName.animate().alpha(1-slideOffset*2).start()
                }
                override fun onStateChanged(bottomSheet: View, newState: Int) {

                }
            })
        val mapFragment: SupportMapFragment? = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mPointViewModel.readAllData.observe(this, Observer { places ->
            val markers = arrayOfNulls<MarkerOptions>(places.size)
            for (i in places.indices) {
                markers[i] = MarkerOptions()
                    .position(LatLng(places[i].latitude, places[i].longitude)).title(places[i].name)
                googleMap!!.addMarker(markers[i])
            }
            refreshList()
        })
        var markers = arrayOfNulls<MarkerOptions>(places.size)
        fab.setOnClickListener {
            if (lng != null && etName.text.toString().isNotEmpty()) {
                places.add(lng!!)
                markers = arrayOfNulls(places.size)
                for (i in places.indices) {
                    markers[i] = MarkerOptions()
                        .position(places[i])
                    googleMap!!.addMarker(markers[i])
                }
                refreshList()
                insertDataToDatabase()
                etName.text.clear()
            }
        }
        googleMap!!.setOnMapClickListener {
            val markerOptions = MarkerOptions()
            markerOptions.position(it)
            googleMap.clear()
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(it))
            googleMap.addMarker(markerOptions)
            lng = it
        }
        refreshList()
        findViewById<FloatingActionButton>(R.id.fab_delete).setOnClickListener {
            deleteAll()
            refreshList()
        }
    }
    private fun insertDataToDatabase(){
        val point = Point(0,etName.text.toString() ,lng!!.latitude, lng!!.longitude)
        mPointViewModel.addPoint(point)
    }

    private fun deleteAll(){
        mPointViewModel.nukeTable()
        refreshList()
    }
    fun refreshList(){
        val adapter = ListAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.rv_points)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        mPointViewModel.readAllData.observe(this, Observer { places -> adapter.setData(places) })
        recyclerView.adapter = adapter
    }
}
