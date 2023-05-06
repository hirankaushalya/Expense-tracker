package com.example.room_user

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.room.Room
import com.example.room_user.data.AppDatabase
import com.example.room_user.model.Transaction
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailedActivity : AppCompatActivity() {

    //initialize EditText
    private lateinit var transaction: Transaction
    private lateinit var labelInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var descriptionInput: EditText

    private lateinit var labelLayout: TextInputLayout
    private lateinit var labelAmount: TextInputLayout
    private lateinit var labelDescription: TextInputLayout

    private lateinit var rootView: ConstraintLayout

    private lateinit var imageButton: ImageButton
    private lateinit var updateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        transaction = intent.getSerializableExtra("transaction") as Transaction

        updateBtn = findViewById(R.id.updateBtn)//get the update button

        labelInput = findViewById(R.id.labelInput)
        amountInput = findViewById(R.id.amountInput)
        descriptionInput = findViewById(R.id.descriptionInput)

        labelInput.setText(transaction.label)
        amountInput.setText(transaction.amount.toString())
        descriptionInput.setText(transaction.description)
        //get the layout
        labelLayout = findViewById(R.id.labelLayout)
        labelAmount = findViewById(R.id.amountLayout)
        labelDescription = findViewById(R.id.descriptionLayout)
        rootView = findViewById(R.id.rootView)

        imageButton = findViewById(R.id.closeBtn)
        updateBtn  = findViewById(R.id.updateBtn)

        //set onclcik listner to clear focus
        rootView.setOnClickListener{
            this.window.decorView.clearFocus()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken,0)
        }
        //add text change method
        labelInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()) {
                    labelLayout.error = null
                }
            }
        })
        //add text change method
        amountInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()) {
                    labelAmount.error = null
                }
            }
        })
        //oncliklistner for updateBtn
        updateBtn.setOnClickListener{
            val label = labelInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()
            val description = descriptionInput.text.toString()
            //validation part
            if(label.isEmpty()){
                labelLayout.error = "Please enter a valid label"
            }
            else if(amount == null){
                labelAmount.error = "Please enter the amount"
            }
            else{
                val transaction = Transaction(transaction.id,label,amount,description)
                update(transaction)
            }
        }

        imageButton.setOnClickListener{
            finish()
        }
    }


    //add updated data to database
    private fun update(transaction: Transaction){

        val  db = Room.databaseBuilder(this,
            AppDatabase::class.java,"transactions").build()

        GlobalScope.launch {
            db.transactionDao().update(transaction)
            finish()
        }

    }

}