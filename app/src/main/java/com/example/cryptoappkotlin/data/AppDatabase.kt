package com.example.cryptoappkotlin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptoappkotlin.model.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun coinPriceInfoDao(): CoinPriceInfoDao
}
