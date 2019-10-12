package com.mghtest.reminderwithlocation.scenes.events


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mghtest.reminderwithlocation.R
import com.mghtest.reminderwithlocation.database.ReminderDatabase
import com.mghtest.reminderwithlocation.database.Reminders
import com.mghtest.reminderwithlocation.scenes.reminder.ReminderViewModel
import com.mghtest.reminderwithlocation.scenes.reminder.ReminderViewModelFactory


class EventFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    var dataList = ArrayList<Reminders>()
    private lateinit var bototmSheet: LinearLayout
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var rvList: RecyclerView
    private lateinit var tvNoData: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_event, container, false)


        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)


        val application = requireNotNull(this.activity).application

        val dataSource = ReminderDatabase.getInstance(application).remindersDao

        val viewModelFactory = EventsViewModelFactory(dataSource, application)


        val reminderViewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel::class.java)


        loadData(reminderViewModel)
        tvNoData=view.findViewById(R.id.tv_no_data)

        if(dataList.isEmpty()){
            tvNoData.visibility=View.VISIBLE
        }else{
            bototmSheet = view.findViewById(R.id.bottom_sheet)

            sheetBehavior = BottomSheetBehavior.from<LinearLayout>(bototmSheet)

            var eventsAdapter = EventsAdapter(dataList)
            rvList = view.findViewById(R.id.rv_search_list)

            rvList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvList.adapter = eventsAdapter
            toggleBottomSheet()
        }


        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val dhaka = LatLng(23.7509, 90.3904)
        mMap.addMarker(MarkerOptions().position(dhaka))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 13f))

    }

    private fun loadData(viewModel: EventViewModel) {

        dataList= viewModel.database.getAllReminders() as ArrayList<Reminders>

        Log.d("TAG", dataList.size.toString())

    }

    private fun toggleBottomSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

}
