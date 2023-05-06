package com.example.room_user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.room_user.fragments.list.ListAdapter
import com.example.room_user.model.User
import junit.framework.Assert
import org.junit.Test
import org.mockito.Mockito


//unit test for Listadapter class
class ListAdapterUnitTest {

    // Test onCreateViewHolder() function
    @Test
    fun testCreateViewHolder() {
        //this unit test verifies that the onCreateViewHolder method of the
        // ListAdapter correctly creates and returns a new ViewHolder with the expected item view.
        val parent = Mockito.mock(ViewGroup::class.java)
        val layoutInflater = Mockito.mock(LayoutInflater::class.java)
        val itemView = Mockito.mock(View::class.java)
        Mockito.`when`(layoutInflater.inflate(R.layout.custom_raw, parent, false)).thenReturn(itemView)
        Mockito.`when`(parent.context).thenReturn(Mockito.mock(Context::class.java))
        val viewHolder = ListAdapter().onCreateViewHolder(parent, 0)

        Assert.assertNotNull(viewHolder)
        Assert.assertEquals(itemView, viewHolder.itemView)
    }

    // Test getItemCount() function
    @Test
    fun testGetItemCount() {
        val adapter = ListAdapter()
        val userList = listOf(User(1, "John", "Doe", 30), User(2, "Jane", "Doe", 25))
        adapter.setData(userList)
        //this unit test verifies that the getItemCount method of the ListAdapter returns the
        // correct number of items in the
        Assert.assertEquals(userList.size, adapter.itemCount)

        adapter.setData(emptyList())

    }

    // Test onBindViewHolder() function
    @Test
    fun testBindViewHolder() {
        // test  that the onBindViewHolder method of the ListAdapter class correctly
        // sets the data of a MyViewHolder instance with the data of a User object.
        val adapter = ListAdapter()
        val userList = listOf(User(1, "John", "Doe", 30), User(2, "Jane", "Doe", 25))
        adapter.setData(userList)

        val itemView = Mockito.mock(View::class.java)
        val txtId = Mockito.mock(TextView::class.java)
        val firstName = Mockito.mock(TextView::class.java)
        val lastName = Mockito.mock(TextView::class.java)
        val age = Mockito.mock(TextView::class.java)
        val rootLayout = Mockito.mock(ConstraintLayout::class.java)
        Mockito.`when`(itemView.findViewById<TextView>(R.id.txtid)).thenReturn(txtId)
        Mockito.`when`(itemView.findViewById<TextView>(R.id.name_first)).thenReturn(firstName)
        Mockito.`when`(itemView.findViewById<TextView>(R.id.name_last)).thenReturn(lastName)
        Mockito.`when`(itemView.findViewById<TextView>(R.id.age1)).thenReturn(age)
        Mockito.`when`(itemView.findViewById<ConstraintLayout>(R.id.rootLayout)).thenReturn(rootLayout)

        val viewHolder = ListAdapter().MyViewHolder(itemView)
        adapter.onBindViewHolder(viewHolder, 0)

        Assert.assertEquals("1", txtId.text)
        Assert.assertEquals("John", firstName.text)
        Assert.assertEquals("Doe", lastName.text)
        Assert.assertEquals("30", age.text)
    }

    // Test setData() function

    // Test setData() function
    @Test
    fun testSetData() {
        val adapter = ListAdapter()
        val userList = listOf(User(1, "John", "Doe", 30), User(2, "Jane", "Doe", 25))
    }
}