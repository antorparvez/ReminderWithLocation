package com.mghtest.reminderwithlocation.scenes.events


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
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
import com.mghtest.reminderwithlocation.database.Reminders
import kotlinx.android.synthetic.main.bottom_sheet.*


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

        loadData()
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
        val sydney = LatLng(23.7509, 90.3904)
        mMap.addMarker(MarkerOptions().position(sydney).draggable(true))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f))

    }

    private fun loadData() {
        val reminders = Reminders(1L, "Dhaka", "Dhaka", 12.3, 12.3, "time", "date")
        val reminders1 = Reminders(1L, "Dhaka", "Dhaka", 12.3, 12.3, "time", "date")
        val reminders2 = Reminders(1L, "Dhaka", "Dhaka", 12.3, 12.3, "time", "date")
        val reminders3 = Reminders(1L, "Dhaka", "Dhaka", 12.3, 12.3, "time", "date")
        val reminders4 = Reminders(1L, "Dhaka", "Dhaka", 12.3, 12.3, "time", "date")

        dataList.add(reminders)
        dataList.add(reminders1)
        dataList.add(reminders2)
        dataList.add(reminders3)
        dataList.add(reminders4)

       // dataList.clear()

    }

    private fun toggleBottomSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

}
