package com.mghtest.reminderwithlocation.scenes.map


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.mghtest.reminderwithlocation.R
import java.util.*


class MapFragment : Fragment() {

    val AUTOCOMPLETE_REQUEST_CODE = 114
    lateinit var autocomplete: AutocompleteSupportFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_map,
            container,
            false
        )

        val fields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG)

        autocomplete =
            childFragmentManager.findFragmentById(R.id.fragment_autocomplete) as AutocompleteSupportFragment

        autocomplete.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                Log.i("TAG", p0.toString())

            }

            override fun onError(p0: Status) {
            }

        })

        autocomplete.setPlaceFields(fields)


        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val place = data?.let { Autocomplete.getPlaceFromIntent(it) }
                Log.i("TAG", place.toString())
            }
        }
    }
}
