// Define the package for the main activity within the application
package com.example.myapplication

// Import necessary Android classes and libraries
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import JsonToSaveForPersistance
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.Simulation.BusDataSimulation
import com.example.myapplication.data.BusTrackerViewModel
import NotificationService
import com.example.myapplication.notification.NotificationThread
import com.example.myapplication.pages.MapPage
import com.example.myapplication.pages.OverviewFragment
import com.example.myapplication.pages.SelectionPage

// Main activity class for the application
class MainActivity : AppCompatActivity() {

    // Late-initialized properties for drawer layout and navigation view
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    // View model instance for handling bus tracking data
    val viewModel: BusTrackerViewModel by viewModels()

    // Tag for logging purposes
    private val TAG = "Notification"

    // Thread responsible for handling notifications
    private val notificationThread: Thread = NotificationThread(this)

    // Function called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Log information about buses from the BusDataSimulation
        BusDataSimulation.getInstance().getBusses().forEach {
            Log.d("BusDataSimulation", it.myToString())
        }

        // Start the notification thread
        notificationThread.start()

        // Set up the Hamburger-Icon for the action bar
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize drawer layout and navigation view
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        // Set up the ActionBarDrawerToggle for the navigation drawer
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open, R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set up the navigation item selection listener for handling menu item clicks
        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item clicks here
            when (menuItem.itemId) {
                R.id.menu_item1 -> {
                    // Handle screen 1 navigation
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, OverviewFragment())
                        .commitNow()
                }

                R.id.menu_item2 -> {
                    // Handle screen 2 navigation
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SelectionPage())
                        .commitNow()
                }
                R.id.menu_item3 -> {
                    // Handle screen 3 navigation
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MapPage())
                        .commitNow()
                }
            }

            // Close the drawer after handling the click
            drawerLayout.closeDrawers()
            true
        }

        // Log information about the UI state loading
        Log.d("uiStateJson", "OnStart")
        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val jsonStringUIState: String? = pref.getString("uiStateJson", null)
        if (jsonStringUIState != null) {
            val loadedUIState: JsonToSaveForPersistance =
                Gson().fromJson(jsonStringUIState, JsonToSaveForPersistance::class.java)
            viewModel.addToNotificationArray(loadedUIState.listOfNotification)

        }

        var msg = "${viewModel.notificationArray.size} Notifications Loaded"
        viewModel.notificationArray.forEach {
            msg = msg + "\n $it"
        }
        Log.d("uiStateJson", "Loaded " + msg)
    }

    // Function called when the activity is paused
    override fun onPause() {
        // Save UI state to shared preferences
        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val edit = pref.edit()
        val uiStateJson: String = Gson().toJson(JsonToSaveForPersistance(viewModel.notificationArray))
        edit.putString("uiStateJson", uiStateJson)
        edit.commit()

        // Start the NotificationService
        val intent = Intent(this, NotificationService::class.java)
        startService(intent)

        super.onPause()
    }

    // Function to handle options item selected (e.g., Hamburger-Icon)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open, R.string.close
        )

        if (toggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }
}
