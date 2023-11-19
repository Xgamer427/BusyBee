package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull

class MapPage : Fragment() {

    private val FINE_PERMISSION_CODE : Int = 1
    private lateinit var  myMap: GoogleMap
    lateinit var currentLocation: Location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var appContext : Context

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

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
            var marker: Marker? = null
            var iterator = 0

            while (true){
                Log.d("Map", "New Loop")
                /*locationTrack = LocationTrack(MainActivity.this);


                if (locationTrack.canGetLocation()) {


                    double longitude = locationTrack.getLongitude();
                    double latitude = locationTrack.getLatitude();
*/

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
                delay(5000)
            }
        }

        Log.d("Map", "After RCountine")
        /*mapFragment.getMapAsync(OnMapReadyCallback {
            myMap = it
        })*/
        //getLastLocation()

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


}