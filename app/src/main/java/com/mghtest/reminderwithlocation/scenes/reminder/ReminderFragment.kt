package com.mghtest.reminderwithlocation.scenes.reminder


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mghtest.reminderwithlocation.R
import com.mghtest.reminderwithlocation.database.ReminderDatabase
import com.mghtest.reminderwithlocation.database.Reminders
import com.mghtest.reminderwithlocation.database.prefs.AppConstants
import com.mghtest.reminderwithlocation.database.prefs.AppPreferences
import kotlinx.android.synthetic.main.fragment_reminder.*
import java.util.*


class ReminderFragment : Fragment() {

    private lateinit var datePicker: Button
    private lateinit var timePicker: Button
    private lateinit var setReminder: Button

    private lateinit var time: TextView
    private lateinit var date: TextView
    private lateinit var noData: TextView

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

        datePicker = view.findViewById(R.id.btn_set_date)
        timePicker = view.findViewById(R.id.btn_set_time)
        setReminder = view.findViewById(R.id.btn_set_reminder)

        time = view.findViewById(R.id.reminder_time)
        date = view.findViewById(R.id.reminder_date)
        noData = view.findViewById(R.id.tv_no_data)

        reminderNote = view.findViewById(R.id.et_note)

        val application = requireNotNull(this.activity).application

        val dataSource = ReminderDatabase.getInstance(application).remindersDao

        val viewModelFactory = ReminderViewModelFactory(dataSource, application)


        val reminderViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ReminderViewModel::class.java)


        val appPreferences = AppPreferences(requireContext())
        val address = appPreferences.getString(AppConstants.ADDRESS)

        if (address==null) {
            noData.visibility = View.VISIBLE
        } else {

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

                        date.setText("$day/$month/$year")
                        yearForSaving = year
                        monthForSaving = month
                        dayForSaving = day

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

                    note = et_note.text.toString()
                    calendarIntent.type = "vnd.android.cursor.item/event"
                    calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, note)
                    calendarIntent.putExtra(CalendarContract.Events.ALLOWED_REMINDERS, true)
                    calendarIntent.putExtra(CalendarContract.Events.HAS_ALARM, true)

                    val date = GregorianCalendar(
                        yearForSaving,
                        monthForSaving,
                        dayForSaving,
                        hourForSaving,
                        minuteForSaving
                    )

                    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                    calendarIntent.putExtra(
                        CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        date.timeInMillis
                    )
                    calendarIntent.putExtra(
                        CalendarContract.EXTRA_EVENT_END_TIME,
                        date.timeInMillis
                    )

                    insertData(
                        "$dayForSaving-$monthForSaving-$yearForSaving",
                        "$hourForSaving : $minuteForSaving"
                        , reminderViewModel
                    )

                    startActivity(calendarIntent)


                } else {
                    Toast.makeText(context, "Please Select both date & time", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return view
    }


    private fun insertData(date: String, time: String, reminderViewModel: ReminderViewModel) {

        val appPreferences = AppPreferences(requireContext())
        val address = appPreferences.getString(AppConstants.ADDRESS)
        val lat = appPreferences.getString(AppConstants.LAT)
        val longt = appPreferences.getString(AppConstants.LONG)

        note = et_note.text.toString()
        var reminders = Reminders(
            0L,
            address.toString(),
            note,
            lat!!.toDouble(),
            longt!!.toDouble(),
            time,
            date
        )
        Log.i("TAG", reminders.placeName)
        reminderViewModel.saveReminder(reminders)

        Toast.makeText(context, "Reminder Saved", Toast.LENGTH_SHORT).show()
        clearCacheData()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            ?.selectedItemId = R.id.reminder_list

    }

    private fun clearCacheData() {

        val appPreferences = AppPreferences(requireContext())
        appPreferences.clear(AppConstants.LONG)
        appPreferences.clear(AppConstants.ADDRESS)
        appPreferences.clear(AppConstants.LAT)

    }

}
