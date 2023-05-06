package com.example.room_user.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.room_user.R
import com.example.room_user.SecondActivity
import com.example.room_user.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var signinBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()//create firbase instance
        binding.switchSignup.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        signinBtn = findViewById(R.id.login)

        //set onclicklister for login button
        binding.login.setOnClickListener{
            val email = binding.loginEmail.text.toString()
            val pass = binding.loginPwd.text.toString()


            if(email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this,SecondActivity::class.java)
                        startActivity(intent)//set successfull intenet
                    }else{
                        Toast.makeText(this,it.exception.toString(),Toast.LENGTH_LONG).show()
                    }
                }

            }else{
                Toast.makeText(this,"Empty fields are not allowed",Toast.LENGTH_LONG).show()
            }
        }



    }
}