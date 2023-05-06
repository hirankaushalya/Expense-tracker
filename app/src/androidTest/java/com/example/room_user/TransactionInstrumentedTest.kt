package com.example.room_user

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.room_user.data.AppDatabase
import com.example.room_user.model.Transaction
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

//instrumented testing for Transaction class
class TransactionInstrumentedTest {

    private lateinit var db: AppDatabase//create instance of AppDtabase
    private lateinit var dao: TransactionDao//define database access for the Transaction entity


    @Before//call before test method run
    fun createDb() {
        //creating in memory databas for testing
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.transactionDao()
    }

    @After
    fun closeDb() {
        db.close()//Close sql lite db connection
    }

    @Test
    fun insertAndGetAll() {
        //Test get all method returns all transaction entity  from database
        val transaction1 = Transaction(1, "Ice Cream", -5.0, "Yummy")
        val transaction2 = Transaction(2, "Salary", 1000.0, "Monthly salary")
        dao.insertAll(transaction1, transaction2)
        val transactions = dao.getAll()
        Assert.assertEquals(2, transactions.size)//The assertThat method compares the
        // actual value with the expected value throws error if comparison fail
        Assert.assertEquals(transaction1, transactions[0])
        Assert.assertEquals(transaction2, transactions[1])
    }

    @Test
    fun delete() {
        //Testing delete transaction method
        val transaction = Transaction(1, "Ice Cream", -5.0, "Yummy")
        dao.insertAll(transaction)
        dao.delete(transaction)//dao provides interface to access database
        val transactions = dao.getAll()
        Assert.assertEquals(0, transactions.size)
    }

    @Test
    fun update() {
        val transaction = Transaction(1, "Ice Cream", -5.0, "Yummy")
        dao.insertAll(transaction)
        transaction.amount = -10.0//If the actual value of the property is
        // within 0.01 of the expected value, the test passes.
        dao.update(transaction)
        val updatedTransaction = dao.getAll()[0]
        Assert.assertEquals(
            -10.0,
            updatedTransaction.amount,
            0.01
        )// compare entered and expected values
    }
}