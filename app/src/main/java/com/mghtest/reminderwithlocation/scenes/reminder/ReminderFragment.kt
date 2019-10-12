package com.mghtest.reminderwithlocation.scenes.reminder


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mghtest.reminderwithlocation.R
import com.mghtest.reminderwithlocation.database.prefs.AppConstants
import com.mghtest.reminderwithlocation.database.prefs.AppPreferences

/**
 * A simple [Fragment] subclass.
 */
class ReminderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_reminder, container, false)



        val appPreferences = AppPreferences(requireContext())
        var address = appPreferences.getString(AppConstants.ADDRESS)


        Toast.makeText(context, address, Toast.LENGTH_SHORT).show()


        return view
    }


}
