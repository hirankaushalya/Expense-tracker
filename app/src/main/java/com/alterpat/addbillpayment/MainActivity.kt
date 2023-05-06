package com.alterpat.addbillpayment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



// MainActivity class
class MainActivity : AppCompatActivity() {
    // Declare a variable to store
    private lateinit var deletedTransaction: Transaction
    private lateinit var transactions : List<Transaction>
    private lateinit var oldTransactions : List<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var db : AppDatabase

    // onCreate function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transactions = arrayListOf()// Initialize the transactions list as an empty array list

        transactionAdapter = TransactionAdapter(transactions)// Initialize the transaction adapter with the transactions list
        linearLayoutManager = LinearLayoutManager(this)// Initialize the layout manager for the recyclerview

        // Build the database object
        db = Room.databaseBuilder(this,
        AppDatabase::class.java,
        "transactions").build()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)// Get the recyclerview from the layout
        recyclerView.adapter = transactionAdapter// Set the transaction adapter to the recyclerview
        recyclerView.layoutManager = linearLayoutManager// Set the layout manager to the recyclerview

        //swipe to delete functionality
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTransaction(transactions[viewHolder.adapterPosition])// Delete the transaction when swiped
            }

        }

        val swipeHelper = ItemTouchHelper(itemTouchHelper)// Initialize the swipe helper with the item touch helper
        swipeHelper.attachToRecyclerView(recyclerView)// Attach the swipe helper to the recyclerview

        val addBtn = findViewById<ImageButton>(R.id.addBtn) // Get the add button from the layout

        // Create an intent to start the AddTransactionActivity
        addBtn.setOnClickListener{
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)// Start the AddTransactionActivity
        }

    }

    // Fetch all transactions from the database
    private fun fetchAll(){
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()// Get all transactions from the database


            runOnUiThread {
                 updateDashboard()// Update the dashboard with the new data
                 transactionAdapter.setData(transactions)// Update the transaction adapter with the new data
            }
        }
    }
    // Update the dashboard with the new data
    private fun updateDashboard() {
        val totalAmount = transactions.map { it.amount }.sum()
        val budgetAmount = transactions.filter { it.amount>0 }.map { it.amount }.sum()
        val expenseAmount = totalAmount - budgetAmount

        val balanceTextView = findViewById<TextView>(R.id.balance)
        balanceTextView.text = "Rs %.2f".format(totalAmount)

        val budgetTextView = findViewById<TextView>(R.id.budget)
        budgetTextView.text = "Rs %.2f".format(budgetAmount)

        val expenseTextView = findViewById<TextView>(R.id.expense)
        expenseTextView.text = "Rs %.2f".format(expenseAmount)
    }

    //deleted transaction by inserting it back into the database
    private fun undoDelete(){
        GlobalScope.launch {
            db.transactionDao().insertAll(deletedTransaction)

            transactions = oldTransactions

            runOnUiThread {
                transactionAdapter.setData(transactions)
                updateDashboard()
            }
        }
    }

    //show a Snackbar with an Undo button to undo a transaction deletion
    private fun showSnackbar(){
        val view = findViewById<View>(R.id.coordinator)
        val snackbar = Snackbar.make(view, "Transaction deleted!", Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo"){
            undoDelete()
        }
            .setActionTextColor(ContextCompat.getColor(this, R.color.red))
            .setTextColor(ContextCompat.getColor(this, R.color.white))
            .show()
    }

    //delete a transaction from the database and update the RecyclerView
    private fun deleteTransaction(transaction: Transaction){
        deletedTransaction = transaction
        oldTransactions = transactions

        GlobalScope.launch {
            db.transactionDao().delete(transaction)

            transactions = transactions.filter { it.id != transaction.id }
            runOnUiThread{
                updateDashboard()
                transactionAdapter.setData(transactions)
                showSnackbar()
            }
        }
    }

    // Called when the app resumes to fetch all the transactions from the database and update the RecyclerView
    override fun onResume() {
        super.onResume()
        fetchAll()

    }

}



