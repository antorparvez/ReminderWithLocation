package com.mghtest.reminderwithlocation.scenes.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mghtest.reminderwithlocation.database.Reminders

class EventsAdapter (private val dataList:List<Reminders>): RecyclerView.Adapter<EventsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventsViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {

        holder.bind(dataList[position])
    }

}