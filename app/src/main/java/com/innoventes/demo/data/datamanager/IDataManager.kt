package com.innoventes.demo.data.datamanager

import com.innoventes.demo.data.preferences.IPreference
import com.innoventes.demo.data.remote.ApiService
import com.innoventes.demo.data.roomdatabase.AppDatabase

interface IDataManager {
    fun getPreference(): IPreference
    fun getDatabase(): AppDatabase
    fun getRemote(): ApiService
}