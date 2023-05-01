package com.example.room_user.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.room_user.R
import com.example.room_user.model.User
import com.example.room_user.viewmodel.UserViewModel


class AddFragment : Fragment() {

    private lateinit var addButton: Button
    private lateinit var mUserViewModel: UserViewModel

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var age: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        addButton = view.findViewById(R.id.addButton)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Initialize EditText views
        firstName = view.findViewById(R.id.firstname)
        lastName = view.findViewById(R.id.lastname)
        age = view.findViewById(R.id.age)

        addButton.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val firstName = firstName.text.toString()
        val lastName = lastName.text.toString()
        val age = age.text.toString() // Convert Editable to String

        if (inputCheck(firstName, lastName, age)) {
            // Create User Object
            val user = User(0, firstName, lastName, age.toInt())
            // Add Data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added to database", Toast.LENGTH_LONG).show()
            // Navigate to List fragment
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_LONG).show()
        }

    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(age))
    }
}