package com.example.room_user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.room_user.data.AppDatabase
import com.example.room_user.model.Transaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AllTransactionActivity : AppCompatActivity() {

    private lateinit var deletedTransaction: Transaction
    private lateinit var oldTransactions: List<Transaction>
    private lateinit var transactions:List<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var db: AppDatabase



    private lateinit var addBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_transaction)

        addBtn = findViewById(R.id.addBtn)
        transactions = arrayListOf()

        transactionAdapter = TransactionAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(this,)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        //heading back to home
        val backarrow = findViewById<ImageView>(R.id.backarrow)
        backarrow.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }


        db = Room.databaseBuilder(this, AppDatabase::class.java,"transactions").build()

        recyclerView.apply{
            adapter = transactionAdapter
            layoutManager = linearLayoutManager
        }

        //swap delete
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTransaction(transactions[viewHolder.adapterPosition])
            }

        }


        val swipHelper = ItemTouchHelper(itemTouchHelper)
        swipHelper.attachToRecyclerView(recyclerView)


        addBtn.setOnClickListener{
            val intent = Intent(this,AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchAll(){
        GlobalScope.launch {
            //db.transactionDao().insertAll(Transaction(0,"Icecream",-5.0,"Yummy"))
            transactions = db.transactionDao().getAll()

            runOnUiThread{
                updateDashboard()
                transactionAdapter.setData(transactions)
            }
        }
    }

    private fun updateDashboard(){

        val balance = findViewById<TextView>(R.id.balance)
        val budget  = findViewById<TextView>(R.id.budget)
        val expense = findViewById<TextView>(R.id.expense)

        val totalAmount = transactions.map {
            it.amount
        }.sum()

        val budgetAmount = transactions.filter { it.amount>0 }.map { it.amount }.sum()

        val expenseAmount = totalAmount - budgetAmount

        balance.text = "Rs.%.2f".format(totalAmount)
        budget.text = "Rs.%.2f".format(budgetAmount)
        expense.text = "Rs.%.2f".format(expenseAmount)
    }

    private fun undoDelete(){
        GlobalScope.launch {
            db.transactionDao().insertAll(deletedTransaction)

            transactions = oldTransactions

            runOnUiThread{
                transactionAdapter.setData(transactions)
                updateDashboard()
            }
        }
    }

    private fun showSnackbar(){
        val view = findViewById<View>(R.id.coordinator)
        val snackbar = Snackbar.make(view,"Transaction deleted", Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo"){
            undoDelete()
        }
            .setActionTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
            .setActionTextColor(ContextCompat.getColor(this, R.color.white))
            .show()

    }
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

    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}