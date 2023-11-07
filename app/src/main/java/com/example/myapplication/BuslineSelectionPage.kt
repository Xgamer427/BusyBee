package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Busline
import com.example.myapplication.databinding.FragmentBuslineselectionPageBinding
import kotlinx.android.synthetic.main.fragment_buslineselection_page.actvBuslineSelection
import kotlinx.android.synthetic.main.fragment_buslineselection_page.btnSaveBuslineSelection
import kotlinx.android.synthetic.main.fragment_buslineselection_page.rgBuslineSelectionPage

class BuslineSelectionPage : Fragment() {

    private lateinit var binding: FragmentBuslineselectionPageBinding

    private val buslines= arrayOf("busline1", "busline2")
    private var selectedBusline: Busline? = null

    private var checkedRadioButton: RadioButton? = null
    private var checkedText: String? = null

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buslineselection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //setup UI Element. Default value == false
        btnSaveBuslineSelection.isEnabled = false

        //Set array for busSelection on auto complete
        val con = this.context
        val buslines = BusDataSimulation.getInstance().getBuslines()
        var buslineNameArray = arrayOf<String>()
        buslines.forEach {
            buslineNameArray += it.name
        }
        if(con != null){
            actvBuslineSelection.setAdapter(
                ArrayAdapter(
                    con,
                    android.R.layout.simple_dropdown_item_1line,
                    buslines
                )
            )
        }

        actvBuslineSelection.setOnItemClickListener { parent, view, position, id ->
            selectedBusline = parent.getItemAtPosition(position) as Busline
            btnSaveBuslineSelection.isEnabled = true
        }

        actvBuslineSelection.addTextChangedListener {
            btnSaveBuslineSelection.isEnabled = false
        }

        //Update current checked Button
        rgBuslineSelectionPage.setOnCheckedChangeListener { group, checkedId ->
            checkedRadioButton = view.findViewById<RadioButton>(checkedId)
            Log.d("Tim", "rgChangeListener")
            //Ensure that a RadioButton is checked
            if (checkedRadioButton != null) {
                checkedText = checkedRadioButton!!.text.toString()
                Log.d("Tim", checkedText!!)
            }
        }

        //OnClick for button to save selected bus
        btnSaveBuslineSelection.setOnClickListener {
            Log.d("Tim", "Clicked")
            val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
            model.updateCurrentBusline(selectedBusline!!)
            activity?.supportFragmentManager?.popBackStack()
        }

        //TODO set listener for radio button and save direction of the busline the user want to get

        super.onViewCreated(view, savedInstanceState)
        }
    }