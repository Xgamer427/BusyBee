package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.DepartureTime
import com.example.myapplication.databinding.FragmentDeparturetimeselectionPageBinding
import kotlinx.android.synthetic.main.fragment_departuretimeselection_page.btnSaveDeparturetime

class DeparturetimeSelectionPage : Fragment() {

    private var binding: FragmentDeparturetimeselectionPageBinding? = null
    private var selectedDeparturetime: DepartureTime? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Initialize TimePicker
        binding = FragmentDeparturetimeselectionPageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setup UI element
        btnSaveDeparturetime.isEnabled = false

        //Access the TimePicker using the binding object
        val departureTimePicker = binding?.tpDeparturetime

        //Now work with the departureTimePicker as needed
        departureTimePicker?.setIs24HourView(true)

        departureTimePicker?.setOnTimeChangedListener { view, hourOfDay, minute ->
            selectedDeparturetime = DepartureTime(hourOfDay, minute)
            btnSaveDeparturetime.isEnabled = true
        }

        btnSaveDeparturetime.setOnClickListener {
            Log.d("User", "Clicked")
            val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
            model.updateCurrentDeparturetime(selectedDeparturetime!!)
            activity?.supportFragmentManager?.popBackStack()

        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //Release the binding when the view is destroyed
        binding = null
    }
}