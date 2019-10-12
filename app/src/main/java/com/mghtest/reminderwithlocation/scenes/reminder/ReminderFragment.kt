package com.mghtest.reminderwithlocation.scenes.reminder


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.mghtest.reminderwithlocation.R
import com.mghtest.reminderwithlocation.database.prefs.AppConstants
import com.mghtest.reminderwithlocation.database.prefs.AppPreferences
import java.util.*


class ReminderFragment : Fragment() {

    private lateinit var datePicker: Button
    private lateinit var timePicker: Button
    private lateinit var setReminder: Button

    private lateinit var time: TextView
    private lateinit var date: TextView

    private lateinit var reminderNote: EditText

    private var yearForSaving: Int = 9999
    private var monthForSaving: Int = 9999
    private var dayForSaving: Int = 9999
    private var hourForSaving: Int = 9999
    private var minuteForSaving: Int = 9999

    private var note: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_reminder, container, false)


        val appPreferences = AppPreferences(requireContext())
        var address = appPreferences.getString(AppConstants.ADDRESS)
        Toast.makeText(context, address, Toast.LENGTH_SHORT).show()

        datePicker = view.findViewById(R.id.btn_set_date)
        timePicker = view.findViewById(R.id.btn_set_time)
        setReminder = view.findViewById(R.id.btn_set_reminder)

        time = view.findViewById(R.id.reminder_time)
        date = view.findViewById(R.id.reminder_date)

        reminderNote = view.findViewById(R.id.et_note)




        timePicker.setOnClickListener { v ->
            val c = Calendar.getInstance()
            val hourX = c.get(Calendar.HOUR)
            val minuteX = c.get(Calendar.MINUTE)

            val listener = TimePickerDialog(
                requireContext(),
                TimePickerDialog.OnTimeSetListener(function = { v, hourOfDay, minute ->
                    time.setText("$hourOfDay : $minute")

                    hourForSaving = hourOfDay
                    minuteForSaving = minute

                }), hourX, minuteX, true
            )

            listener.show()
        }


        datePicker.setOnClickListener { v ->
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)
            val month = c.get(Calendar.MONTH)
            val year = c.get(Calendar.YEAR)

            val listener = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener(function = { v, year1: Int, month1: Int, day1: Int ->

                    date.setText("$day1/$month1/$year1")
                    yearForSaving = year1
                    monthForSaving = month1
                    dayForSaving = day1

                }),
                day,
                month,
                year
            )

            listener.show()
        }


        setReminder.setOnClickListener { v ->
            if (dayForSaving != 9999 && monthForSaving != 9999 && yearForSaving != 9999 && hourForSaving != 9999 && minuteForSaving != 9999) {

                val calendarIntent = Intent(Intent.ACTION_INSERT)

                calendarIntent.type="vnd.android.cursor.item/event"
                calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, note)
                calendarIntent.putExtra(CalendarContract.Events.ALLOWED_REMINDERS, true)
                calendarIntent.putExtra(CalendarContract.Events.HAS_ALARM, true)

                val date = GregorianCalendar(yearForSaving, monthForSaving, dayForSaving, hourForSaving, minuteForSaving)

                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.timeInMillis)
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, date.timeInMillis)

                startActivity(calendarIntent)


            } else {
                Toast.makeText(context, "Please Select both date & time", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }


}
