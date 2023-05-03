package com.example.expensetracker_shops.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.expensetracker_shops.Models.ShopModel
import com.example.expensetracker_shops.R
import com.google.firebase.database.FirebaseDatabase

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

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("shopId").toString(),
                intent.getStringExtra("shopName").toString(),
                intent.getStringExtra("shopAddress").toString(),
                intent.getStringExtra("shopMobNo").toString()
            )
        }
    }
    private fun openUpdateDialog(
        shopId:String,
        shopName:String,
        shopAddress:String,
        shopMobNo:String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater=layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_dialog,null)

        mDialog.setView(mDialogView)

        val upShopname = mDialogView.findViewById<EditText>(R.id.upShopName)
        val upShopAddress = mDialogView.findViewById<EditText>(R.id.upShopAddress)
        val upShopMob = mDialogView.findViewById<EditText>(R.id.upShopMob)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        upShopname.setText(intent.getStringExtra("shopName").toString())
        upShopAddress.setText(intent.getStringExtra("shopAddress").toString())
        upShopMob.setText(intent.getStringExtra("shopMobNo").toString())

        mDialog.setTitle("Updating $shopName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateShopData(
                shopId,
                upShopname.text.toString(),
                upShopAddress.text.toString(),
                upShopMob.text.toString()
            )
            Toast.makeText(applicationContext, "Shop Data Updated",Toast.LENGTH_LONG).show()

            tvShopName.text = upShopname.text.toString()
            tvShopAddress.text = upShopAddress.text.toString()
            tvShopMob.text = upShopMob.text.toString()

            alertDialog.dismiss()
        }


    }
    private fun updateShopData(
        shopId:String,
        shopName: String,
        shopAddress: String,
        shopMobNo : String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Shops").child(shopId)
        val shopInfo = ShopModel(shopId, shopName, shopAddress, shopMobNo)
        dbRef.setValue(shopInfo)
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