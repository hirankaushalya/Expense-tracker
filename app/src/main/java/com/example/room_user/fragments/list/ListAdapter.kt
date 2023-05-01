package com.example.room_user.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.room_user.R
import com.example.room_user.model.User


class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtid: TextView = itemView.findViewById(R.id.txtid)
        val name_first: TextView = itemView.findViewById(R.id.name_first)
        val name_last: TextView = itemView.findViewById(R.id.name_last)
        val age1: TextView = itemView.findViewById(R.id.age1)
        val rootLayout:ConstraintLayout = itemView.findViewById(R.id.rootLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_raw, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.txtid.text = currentItem.id.toString()
        holder.name_first.text = currentItem.firstName
        holder.name_last.text = currentItem.lastName
        holder.age1.text = currentItem.age.toString()

        holder.rootLayout.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }
}


