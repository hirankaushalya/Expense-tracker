package com.example.room_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.room.Room
import com.example.room_user.data.AppDatabase
import com.example.room_user.model.Transaction
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var addTransactionBtn: Button
    //initilize EditTexts
    private lateinit var labelInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var descriptionInput: EditText

    private lateinit var labelLayout: TextInputLayout
    private lateinit var labelAmount: TextInputLayout
    private lateinit var labelDescription: TextInputLayout

    private lateinit var imageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        addTransactionBtn = findViewById(R.id.addTransactionBtn)//get the button id
        //get the id and assign to a variable
        labelInput = findViewById(R.id.labelInput)
        amountInput = findViewById(R.id.amountInput)
        descriptionInput = findViewById(R.id.descriptionInput)

        labelLayout = findViewById(R.id.labelLayout)
        labelAmount = findViewById(R.id.amountLayout)
        labelDescription = findViewById(R.id.descriptionLayout)

        imageButton = findViewById(R.id.closeBtn)

        //add TextChangelistner method for change color of input fields when type something
        labelInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()) {
                    labelLayout.error = null
                }
            }
        })

        //add TextChangelistner method for change color of input fields when type something
        amountInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()) {
                    labelAmount.error = null
                }
            }
        })

        addTransactionBtn.setOnClickListener{
            val label = labelInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()
            val description = descriptionInput.text.toString()
            //check validation for input fields
            if(label.isEmpty()){
                labelLayout.error = "Please enter a valid label"
            }
            else if(amount == null){
                labelAmount.error = "Please enter the amount"
            }
            else{
                val transaction = Transaction(0,label,amount,description)
                insert(transaction)//insert to database
            }

        }

        imageButton.setOnClickListener{
            finish()//back button
        }

    }

    //insert method to Room Db
    private fun insert(transaction: Transaction){

        val  db = Room.databaseBuilder(this,
            AppDatabase::class.java,"transactions").build()

        GlobalScope.launch {
            db.transactionDao().insertAll(transaction)
            finish()
        }

    }



}