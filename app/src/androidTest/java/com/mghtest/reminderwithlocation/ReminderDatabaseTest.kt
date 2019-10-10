package com.mghtest.reminderwithlocation

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.mghtest.reminderwithlocation.database.ReminderDatabase
import com.mghtest.reminderwithlocation.database.Reminders
import com.mghtest.reminderwithlocation.database.RemindersDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var reminderDao: RemindersDao
    private lateinit var db: ReminderDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ReminderDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        reminderDao = db.remindersDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val night = Reminders()
        reminderDao.insert(night)
        val tonight = reminderDao.getTonight()
        Assert.assertEquals(tonight?.date, "1")
    }
}

