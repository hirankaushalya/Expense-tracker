package com.example.room_user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.room_user.model.Transaction
//Data access class
@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transactions`")
    fun getAll():List<Transaction>

    @Insert
    fun insertAll(vararg transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Update
    fun update(vararg transaction: Transaction)
}