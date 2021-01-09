package com.example.cryptoappkotlin.data

import android.app.Application
import androidx.room.Room

class App : Application() {

    companion object {
        private const val DB_NAME = "main.db"

        private var database: AppDatabase? = null
        private var instance: App? = null

        fun getInstance(): App? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun getDatabase(): AppDatabase? {
        return database
    }
}