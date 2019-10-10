package com.mghtest.reminderwithlocation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RemindersDao{
    @Insert
    fun insert(reminder: Reminders)

    @Query("Select * From reminders_table ORDER BY reminderId DESC")
    fun getAllReminders():LiveData<List<Reminders>>


    @Query("SELECT * FROM reminders_table ORDER BY reminderId DESC LIMIT 1")
    fun getTonight(): Reminders?
}