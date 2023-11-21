package com.example.myapplication.pages

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap

/**
 * Fragment for showing a map
 */
class MapPage : Fragment() {

    //Map location works but updating it to track location not works so commented out
    /*
    private val FINE_PERMISSION_CODE : Int = 1
    private lateinit var  myMap: GoogleMap
    lateinit var currentLocation: Location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var appContext : Context
    */
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
    //Map location works but updating it to track location not works so commented out
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appContext  = requireContext()
        if (ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),FINE_PERMISSION_CODE)
            Toast.makeText(appContext, "Location access must be granted", Toast.LENGTH_SHORT)
            return
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(appContext)
        Log.d("Map", "Before cRountine")

        val mapFragment : SupportMapFragment  = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            myMap = it
            myMap.moveCamera(CameraUpdateFactory.zoomTo(16f))
        })

        lifecycleScope.launch {
            val request = LocationRequest.create().setInterval(1000L).setFastestInterval(1000L)

            val locationCallBack = object  : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        launch { Log.d("Map", location.toString()) }
                    }
                }
            }
            var marker: Marker? = null

            while (true){


                Log.d("Map", "New Loop")
                Toast.makeText(appContext, "Get location Loop", Toast.LENGTH_SHORT)
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(appContext)


                fusedLocationProviderClient.requestLocationUpdates(request, locationCallBack, Looper.getMainLooper())

                val task : Task<Location> = fusedLocationProviderClient.getLastLocation()
                task.addOnSuccessListener {location ->

                    Log.d("Map", "Location success")
                    if(location!= null){
                        Log.d("Map", "Location Updated " +location.toString())
                        Toast.makeText(appContext, "Location updated", Toast.LENGTH_SHORT)
                        currentLocation = location
                        val sydney: LatLng = LatLng(location.latitude, location.longitude)
                        marker?.remove()
                        marker = myMap.addMarker(MarkerOptions().position(sydney).title("MyLocation"))
                        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    }else{
                        Toast.makeText(appContext, "Location is disabled", Toast.LENGTH_SHORT)
                    }
                }
                delay(1000)
            }
        }

        Log.d("Map", "After RCountine")

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
            val task : Task<Location> = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener {location ->
                if(location!= null){
                    currentLocation = location


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
}