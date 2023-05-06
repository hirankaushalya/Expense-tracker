package com.alterpat.addbillpayment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        // Find the add transaction button and set a click listener
        val addTransactionBtn = findViewById<Button>(R.id.addTransactionBtn)

        // Extension function to convert empty string to null when converting to double
        fun String.toDoubleNull(): Double? {
            return if (this.isEmpty()) null else this.toDoubleOrNull()
        }

        addTransactionBtn.setOnClickListener {
            // Get the values from the input fields
            val label = findViewById<EditText>(R.id.labelInput).text.toString()
            val amount = findViewById<EditText>(R.id.amountInput).text.toString().toDoubleNull()
            val month = findViewById<EditText>(R.id.dateInput).text.toString()
            val description = findViewById<EditText>(R.id.descriptionInput).text.toString()

            // Validate the input values
            if (label.isEmpty())
                findViewById<TextInputLayout>(R.id.labelLayout).error = "Please enter a valid label"

            else if(amount == null)
                findViewById<TextInputLayout>(R.id.amountLayout).error = "Please enter a valid amount"

            else{
                // Create a new transaction object and insert it into the database
                val transaction = Transaction(0, label, amount, month, description)
                insert(transaction)
            }
        }

        // Find the close button and set a click listener to finish the activity
        findViewById<ImageButton>(R.id.closeBtn).setOnClickListener {
            finish()
        }
    }

    // Function to insert a transaction into the database
     private fun insert(transaction: Transaction){
         val db = Room.databaseBuilder(this,
             AppDatabase::class.java,
             "transactions").build()

         GlobalScope.launch {
             db.transactionDao().insertAll(transaction)
             finish()
         }
     }
}