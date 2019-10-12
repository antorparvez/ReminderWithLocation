package com.mghtest.reminderwithlocation.scenes.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mghtest.reminderwithlocation.database.Reminders
import com.mghtest.reminderwithlocation.database.RemindersDao
import kotlinx.coroutines.*

class ReminderViewModel(val database:RemindersDao, application: Application)
    :AndroidViewModel(application){

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private suspend fun insert(reminders: Reminders) {
        withContext(Dispatchers.IO) {
            database.insert(reminders)
        }
    }

    fun saveReminder(reminders: Reminders) {
        uiScope.launch {
            insert(reminders)
        }
    }
}