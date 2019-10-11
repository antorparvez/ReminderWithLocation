package com.mghtest.reminderwithlocation.scenes.events

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mghtest.reminderwithlocation.R
import com.mghtest.reminderwithlocation.database.Reminders

class EventsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_reminder, parent, false)) {

    private var tvPlaceName: TextView? = null
    private var tvRemninderNote: TextView? = null
    private var tvReminderTime: TextView? = null
    private var tvReminderDate: TextView? = null

    init {
        tvPlaceName=itemView.findViewById(R.id.tv_place_name)
        tvRemninderNote=itemView.findViewById(R.id.tv_reminder_note)
        tvReminderTime=itemView.findViewById(R.id.tv_reminder_time)
        tvReminderDate=itemView.findViewById(R.id.tv_reminder_date)
    }

    fun bind(reminders: Reminders){
        tvPlaceName?.text=reminders.placeName
        tvRemninderNote?.text=reminders.reminderNote
        tvReminderDate?.text=reminders.date
        tvReminderTime?.text=reminders.time
    }
}