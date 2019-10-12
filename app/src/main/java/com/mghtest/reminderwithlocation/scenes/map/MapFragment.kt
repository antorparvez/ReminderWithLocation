package com.mghtest.reminderwithlocation.scenes.map


import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mghtest.reminderwithlocation.R
import com.mghtest.reminderwithlocation.scenes.reminder.ReminderFragment
import java.io.IOException
import java.util.*


class MapFragment : Fragment(), OnMapReadyCallback {


    private lateinit var autocomplete: AutocompleteSupportFragment
    private lateinit var mMap: GoogleMap
    private lateinit var saveReminderButton: Button
    var address: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_map,
            container,
            false
        )


        val fields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG)

        autocomplete =
            childFragmentManager.findFragmentById(R.id.fragment_autocomplete) as AutocompleteSupportFragment

        autocomplete.setPlaceFields(fields)

        autocomplete.setLocationRestriction(
            RectangularBounds.newInstance(
                LatLng(23.66892, 90.46126)
                , LatLng(23.89982, 90.38680)
            )
        )


        autocomplete.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                Log.i("TAG", p0.toString())
                address = p0.address.toString()
                latitude = p0.latLng!!.latitude
                longitude = p0.latLng!!.longitude
                if(!address.isEmpty()){
                    saveReminderButton.visibility=View.VISIBLE
                }
            }

            override fun onError(p0: Status) {
            }

        })

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        saveReminderButton = view.findViewById(R.id.btn_add_reminder)

        saveReminderButton.setOnClickListener { v->
          //Toast.makeText(context, address, Toast.LENGTH_SHORT).show()
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.selectedItemId=R.id.add_reminder

            //childFragmentManager.beginTransaction().replace(R.id.common_layout, ReminderFragment()).commit()

        }



        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(23.7509, 90.3904)
        mMap.addMarker(MarkerOptions().position(sydney).draggable(true))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f))



        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(p0: Marker) {
                var latLng = p0.position as LatLng
                val geocoder = Geocoder(context, Locale.getDefault())


                val addressShort =
                    geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0)
                Log.i("Address", addressShort.getAddressLine(0))


                address = addressShort.getAddressLine(0)
                latitude = latLng.latitude
                longitude = latLng.longitude

                if(!address.isEmpty()){
                    saveReminderButton.visibility=View.VISIBLE
                }

            }

            override fun onMarkerDragStart(p0: Marker?) {
                Log.i("Address", "dragging started")
            }

            override fun onMarkerDrag(p0: Marker?) {

                Log.i("Address", "dragging")
            }

        })


    }


}
