package com.mghtest.reminderwithlocation.scenes.events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mghtest.reminderwithlocation.database.Reminders
import com.mghtest.reminderwithlocation.database.RemindersDao
import kotlinx.coroutines.*

class EventViewModel(val database: RemindersDao, application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}