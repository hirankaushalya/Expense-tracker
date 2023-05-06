package com.example.room_user

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.room_user.model.Transaction

class TransactionAdapter(private var transactions: List<Transaction>)
    : RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {

    class TransactionHolder(view: View):RecyclerView.ViewHolder(view){

        val label : TextView = view.findViewById(R.id.label)//get textview by id
        val amount : TextView = view.findViewById(R.id.amount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout,parent,false)
        return TransactionHolder(view)//inflate the layout
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transaction = transactions[position]
        val context = holder.amount.context
        //calculation part for dashboard
        if(transaction.amount >= 0){
            holder.amount.text = "+ Rs.%.2f".format(transaction.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.teal_700))
        }else{
            holder.amount.text = "- Rs.%.2f".format(Math.abs(transaction.amount))
            holder.amount.setTextColor(
                ContextCompat.getColor(context,
                    android.R.color.holo_red_dark
                ))
        }

        holder.label.text =transaction.label

        holder.itemView.setOnClickListener{
            val intent = Intent(context,DetailedActivity::class.java)
            intent.putExtra("transaction",transaction)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun setData(transactions: List<Transaction>){
        this.transactions = transactions
        notifyDataSetChanged()
    }

}