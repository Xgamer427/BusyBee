package com.example.myapplication

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import org.jetbrains.annotations.NotNull

class MapPage : Fragment(), OnMapReadyCallback {

    private val FINE_PERMISSION_CODE : Int = 1
    private lateinit var  myMap: GoogleMap
    lateinit var currentLocation: Location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var appContext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Map", "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }
    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appContext  = requireContext()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(appContext)

        getLastLocation()
        super.onViewCreated(view, savedInstanceState)
    }

    fun getLastLocation(){

        if (
                ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
             != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
             != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "No permission", Toast.LENGTH_SHORT).show()
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),FINE_PERMISSION_CODE)
            return
        }
        Toast.makeText(context, "get location", Toast.LENGTH_SHORT).show()
        val task : Task<Location> = fusedLocationProviderClient.getLastLocation()
        task.addOnSuccessListener {location ->
            if(location!= null){
                currentLocation = location

                val mapFragment : SupportMapFragment = parentFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == FINE_PERMISSION_CODE){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }else{
                Toast.makeText(activity, "Location permission is denied, please allow permission", Toast.LENGTH_SHORT).show()
            }

        }
    }
    */
    override fun onMapReady(@NotNull googleMap: GoogleMap) {
        Log.d("Map", "OnMapReady")
        /*myMap = googleMap

        val sydney: LatLng = LatLng(-34.0, 100.0)
        myMap.addMarker(MarkerOptions().position(sydney).title("MyLocation"))
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        */
    }

}