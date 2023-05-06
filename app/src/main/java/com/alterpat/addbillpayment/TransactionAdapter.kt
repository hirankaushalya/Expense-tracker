package com.alterpat.addbillpayment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class TransactionAdapter(private var transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {

    // Define TransactionHolder class
    class TransactionHolder(view: View) : RecyclerView.ViewHolder(view){
       val label : TextView = view.findViewById(R.id.label)
        val amount : TextView = view.findViewById(R.id.amount)
    }

    // Create a new ViewHolder by inflating a view from transaction_layout.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder{
         val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout, parent, false)
         return TransactionHolder(view)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        // Get the transaction at the given position
        val transaction = transactions[position]
        // Get the context of the TextView
        val context = holder.amount.context

        // Set the amount text and color based on its sign
        if(transaction.amount >= 0) {
            holder.amount.text = "+Rs %.2f".format(transaction.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green))
        }else{
            holder.amount.text = "-Rs %.2f".format(Math.abs(transaction.amount))
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }

        // Set the label text
        holder.label.text = transaction.label

        // Set an onClickListener to open the DetailedActivity when a transaction is clicked
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailedActivity::class.java)
            intent.putExtra("transaction", transaction)
            context.startActivity(intent)
        }

    }

    // Return the number of transactions in the list
    override fun getItemCount(): Int {
        return transactions.size
    }

    // Update the list of transactions
    fun setData(transactions: List<Transaction>){
        this.transactions = transactions
        notifyDataSetChanged()
    }
}