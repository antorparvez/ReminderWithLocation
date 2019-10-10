package com.mghtest.reminderwithlocation.scenes.reminder


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mghtest.reminderwithlocation.R

/**
 * A simple [Fragment] subclass.
 */
class ReminderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_reminder, container, false);
        return view
    }


}
