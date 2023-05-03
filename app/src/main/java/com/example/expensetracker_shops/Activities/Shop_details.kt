package com.example.expensetracker_shops.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.expensetracker_shops.R

class Shop_details : AppCompatActivity() {

    private lateinit var tvShopId : TextView
    private lateinit var tvShopName :TextView
    private lateinit var tvShopAddress :TextView
    private lateinit var tvShopMob :TextView
    private lateinit var btnUpdate :TextView
    private lateinit var btnDelete :TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_details)

        //tvShopId = findViewById(R.id.tvShopId)
        initView()
        setValuesToviews()
    }

    private fun initView(){
        tvShopId = findViewById(R.id.tvShopId)
        tvShopName = findViewById(R.id.tvShopName)
        tvShopAddress = findViewById(R.id.tvShopAddress)
        tvShopMob = findViewById(R.id.tvShopMob)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToviews(){
        tvShopId.text = intent.getStringExtra("shopId")
        tvShopName.text = intent.getStringExtra("shopName")
        tvShopAddress.text = intent.getStringExtra("shopAddress")
        tvShopMob.text = intent.getStringExtra("shopMobNo")

    }

}