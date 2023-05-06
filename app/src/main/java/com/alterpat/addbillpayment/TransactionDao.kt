package com.alterpat.addbillpayment

import androidx.room.*

@Dao
interface TransactionDao {
    //SQL query that retrieves all transactions
    @Query("SELECT * from transactions")
    fun getAll(): List<Transaction>

    //insert one or more transactions
    @Insert
    fun insertAll(vararg transaction: Transaction)

    //delete a transaction
    @Delete
    fun delete(transaction: Transaction)

    //update one or more transactions
    @Update
    fun update(vararg transaction: Transaction)


}
