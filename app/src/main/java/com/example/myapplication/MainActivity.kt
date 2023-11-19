package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.data.JsonToSaveForPersistance
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.data.BusTrackerViewModel
import com.example.myapplication.notification.NotificationService
import com.example.myapplication.notification.NotificationThread
import com.example.myapplication.pages.OverviewFragment
import com.example.myapplication.pages.SelectionPage

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    val viewModel: BusTrackerViewModel by viewModels()

    private val TAG = "Notification"

    private val notificationThread : Thread = NotificationThread(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        notificationThread.start()
        //For the Hamburger-Icon
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open, R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item clicks here
            when (menuItem.itemId) {
                R.id.menu_item1 -> {
                    // Handle Overview Fragment navigation
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, OverviewFragment())
                        .commitNow()
                }

                R.id.menu_item2 -> {
                    // Handle Selectionpage navigation
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SelectionPage())
                        .commitNow()
                }
                R.id.menu_item3 -> {
                    // Handle Map navigation
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MapPage())
                        .commitNow()
                }
            }
            // Close the drawer after handling the click
            drawerLayout.closeDrawers()
            true
        }

        /*val intent = Intent(this, NotificationService::class.java);

        val viewModelJson:String = Gson().toJson(viewModel.uiState.value)
        intent.putExtra("viewModelUIState",viewModelJson)
        startService(intent);
        */

        Log.d("uiStateJson","OnStart")
        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val jsonStringUIState:String? = pref.getString("uiStateJson", null)
        if(jsonStringUIState != null){
            val loadedUIState: JsonToSaveForPersistance = Gson().fromJson(jsonStringUIState, JsonToSaveForPersistance::class.java)
            viewModel.addToNotificationArray(loadedUIState.listOfNotification)
        }

        var msg = "${viewModel.notificationArray.size} Notifications Loaded"
        viewModel.notificationArray.forEach {
            msg = msg+ "\n $it"
        }
    }

    override fun onPause() {
        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val edit = pref.edit()
        val uiStateJson:String = Gson().toJson(JsonToSaveForPersistance(viewModel.notificationArray))
        edit.putString("uiStateJson", uiStateJson)
        edit.commit()

        val intent = Intent(this, NotificationService::class.java)
        startService(intent)
        super.onPause()
    }

    //Function for the Drawer Layout
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open, R.string.close
        )

        if (toggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }
}