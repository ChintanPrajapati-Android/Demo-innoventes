package com.innoventes.demo.base

import androidx.lifecycle.ViewModel
import com.innoventes.demo.data.datamanager.DataManager
import com.innoventes.demo.data.preferences.IPreference
import com.innoventes.demo.data.remote.ApiService
import com.innoventes.demo.data.roomdatabase.AppDatabase

open class BaseViewModel : ViewModel() {

    fun getPreference(): IPreference {
        return DataManager.getInstance().getPreference()
    }

    fun getDatabase(): AppDatabase {
        return DataManager.getInstance().getDatabase()
    }

    fun getRemote(): ApiService {
        return DataManager.getInstance().getRemote()
    }
}