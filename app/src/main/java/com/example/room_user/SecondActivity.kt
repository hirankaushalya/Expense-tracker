package com.example.room_user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SecondActivity : AppCompatActivity() {

    private lateinit var categoryBtn:Button
    private lateinit var dashboard:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        categoryBtn = findViewById(R.id.cateBtn)
        dashboard = findViewById(R.id.dashBtn)
        categoryBtn.setOnClickListener{
            val intent = Intent(this@SecondActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        dashboard.setOnClickListener{
            val intent = Intent(this@SecondActivity, AllTransactionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}