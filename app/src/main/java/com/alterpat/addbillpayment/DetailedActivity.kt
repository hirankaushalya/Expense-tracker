package com.alterpat.addbillpayment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailedActivity : AppCompatActivity() {
    private lateinit var transaction: Transaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        // Find and store reference
        val labelInput = findViewById<EditText>(R.id.labelInput)
        val amountInput = findViewById<EditText>(R.id.amountInput)
        val dateInput = findViewById<EditText>(R.id.dateInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val rootView = findViewById<View>(android.R.id.content)
        val updateBtn = findViewById<Button>(R.id.updateBtn)
        val labelLayout = findViewById<TextInputLayout>(R.id.labelLayout)
        val amountLayout = findViewById<TextInputLayout>(R.id.amountLayout)
        val dateLayout = findViewById<TextInputLayout>(R.id.dateLayout)

        // Get transaction object from intent extra
        transaction = intent.getSerializableExtra("transaction") as Transaction

        // Set input field tex
        labelInput.setText(transaction.label)
        amountInput.setText(transaction.amount.toString())
        dateInput.setText(transaction.month)
        descriptionInput.setText(transaction.description)

        // Handle click events on root view by clearing focus and hiding soft keyboard
        rootView.setOnClickListener {
            this.window.decorView.clearFocus()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        // Add text change listeners to input fields and set visibility of update button
        labelInput.addTextChangedListener {
            updateBtn.visibility = View.VISIBLE
            if (it!!.count() > 0)
                labelLayout.error = null
        }

        amountInput.addTextChangedListener {
            updateBtn.visibility = View.VISIBLE
            if (it!!.count() > 0)
                amountLayout.error = null
        }

        dateInput.addTextChangedListener {
            updateBtn.visibility = View.VISIBLE
            if (it!!.count() > 0)
                dateLayout.error = null
        }

        descriptionInput.addTextChangedListener {
            updateBtn.visibility = View.VISIBLE
        }


        fun String.toDoubleNull(): Double? {
            return if (this.isEmpty()) null else this.toDoubleOrNull()
        }

        // Add click listener to update button
        updateBtn.setOnClickListener {
            val label = findViewById<EditText>(R.id.labelInput).text.toString()
            val amount = findViewById<EditText>(R.id.amountInput).text.toString().toDoubleNull()
            val month = findViewById<EditText>(R.id.dateInput).text.toString()
            val description = findViewById<EditText>(R.id.descriptionInput).text.toString()

            // Validate input fields and display error message if necessary
            if (label.isEmpty())
                findViewById<TextInputLayout>(R.id.labelLayout).error = "Please enter a valid label"

            else if(amount == null)
                findViewById<TextInputLayout>(R.id.amountLayout).error = "Please enter a valid amount"

            else{
                val transaction = Transaction( transaction.id, label, amount, month, description)
                update(transaction)
            }
        }

        findViewById<ImageButton>(R.id.closeBtn).setOnClickListener {
            finish()
        }
    }

    private fun update(transaction: Transaction){
        val db = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "transactions").build()

        GlobalScope.launch {
            db.transactionDao().update(transaction)
            finish()
        }
    }
}
