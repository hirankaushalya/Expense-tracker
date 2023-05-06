package com.example.expensetracker_shops.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker_shops.R
import com.example.expensetracker_shops.R.*
import com.example.expensetracker_shops.Models.ShopModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity(){
    lateinit var shopName: EditText
    lateinit var shopAddress :EditText
    lateinit var shopMobNo: EditText
    private lateinit var btnSaveData : Button

    lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_insertion)

        shopName = findViewById(R.id.shopname)
        shopAddress = findViewById(R.id.shopAddress)
        shopMobNo = findViewById(R.id.shopMobNo)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Shops")

        btnSaveData.setOnClickListener {
            saveShopsData()
        }

    }
    fun saveShopsData(){
        val sName = shopName.text.toString()
        val sAddress = shopAddress.text.toString()
        val sMob = shopMobNo.text.toString()

        if(sName.isEmpty()){
            shopName.error = "please enter shop name"
        }
        if(sAddress.isEmpty()){
            shopAddress.error = "please enter shop address"
        }
        if(sMob.isEmpty()){
            shopMobNo.error = "please enter shop's contact number"
        }
        val shopId = dbRef.push().key!!

        val shop = ShopModel(shopId, sName , sAddress, sMob  )

        dbRef.child(shopId).setValue(shop)
            .addOnCompleteListener {
                Toast.makeText(this,"Data Inserted Successfully!",Toast.LENGTH_LONG).show()
                shopName.text.clear()
                shopAddress.text.clear()
                shopMobNo.text.clear()
            }.addOnFailureListener{ error ->
                Toast.makeText(this, "Error ${error.message}",Toast.LENGTH_LONG).show()
            }
    }

}