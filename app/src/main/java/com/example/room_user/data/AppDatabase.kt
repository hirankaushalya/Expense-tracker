package com.example.room_user.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.room_user.TransactionDao
import com.example.room_user.model.Transaction

@Database(entities = arrayOf(Transaction::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

}