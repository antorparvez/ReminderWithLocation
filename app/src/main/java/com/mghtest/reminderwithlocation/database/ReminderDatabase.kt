package com.mghtest.reminderwithlocation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Reminders::class], version = 1, exportSchema = false)
abstract class ReminderDatabase : RoomDatabase(){

    abstract val  remindersDao: RemindersDao

    companion object {
        @Volatile
        private var INSTANCE: ReminderDatabase? = null

        fun getInstance(context: Context): ReminderDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        ReminderDatabase::class.java, "reminders_database").fallbackToDestructiveMigration().allowMainThreadQueries().build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}