package com.example.weatherapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.db.city.City
import com.example.weatherapp.db.city.CityDao

@Database(entities = [City::class], version = 2)
abstract class MyDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        private var databaseInstance: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase? {
            if (databaseInstance == null) {
                synchronized(MyDatabase::class.java) {
                    if (databaseInstance == null) {
                        databaseInstance = Room.databaseBuilder<MyDatabase>(
                            context.applicationContext,
                            MyDatabase::class.java, "my_database"
                        ).build()
                    }
                }
            }
            return databaseInstance
        }
    }
}