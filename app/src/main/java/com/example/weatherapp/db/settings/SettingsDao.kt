package com.example.weatherapp.db.settings

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SettingsDao {

    @get:Query("SELECT * FROM settings")
    val allSettings: List<Setting>

    @get:Query("SELECT * FROM settings WHERE name='degrees'")
    val degrees: Setting

    @Insert
    fun insertSetting(setting: Setting)
}