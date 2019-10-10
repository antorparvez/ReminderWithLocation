package com.mghtest.reminderwithlocation.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders_table")
data class Reminders(
    @PrimaryKey(autoGenerate = true)
    var reminderId: Long = 0L,

    @ColumnInfo(name = "placeName")
    var placeName: String="",

    @ColumnInfo(name = "reminderNote")
    var reminderNote: String="",

    @ColumnInfo(name = "latitude")
    var latitude: Double=12.0,

    @ColumnInfo(name = "longitude")
    var longitude: Double=12.0,

    @ColumnInfo(name = "time")
    var time: String="1",

    @ColumnInfo(name = "date")
    var date: String="1"


    )