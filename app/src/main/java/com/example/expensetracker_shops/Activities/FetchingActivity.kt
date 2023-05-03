package com.example.expensetracker_shops.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker_shops.Adapters.ShopAdapter
import com.example.expensetracker_shops.Models.ShopModel
import com.example.expensetracker_shops.R
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {
    private lateinit var shopRView : RecyclerView
    private lateinit var tvLoadingData:TextView
    private lateinit var shopList : ArrayList<ShopModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        shopRView = findViewById(R.id.rvShop)
        shopRView.layoutManager = LinearLayoutManager(this)
        shopRView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        shopList = arrayListOf<ShopModel>()

        getShopsData()
    }
    private fun getShopsData(){
        shopRView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Shops")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                shopList.clear()
                if(snapshot.exists()){
                    for(shopSnap in snapshot.children){
                        val shopData = shopSnap.getValue(ShopModel::class.java)
                        shopList.add(shopData!!)
                    }
                    val mAdapter = ShopAdapter(shopList)
                    shopRView.adapter = mAdapter

                    shopRView.visibility = View.VISIBLE
                    tvLoadingData.visibility= View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}