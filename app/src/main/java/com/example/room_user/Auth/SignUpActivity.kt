package com.example.room_user.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.room_user.R
import com.example.room_user.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var switchlogin:TextView
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var signupBtn:Button

    private lateinit var reg_email:EditText
    private lateinit var reg_password:EditText
    private lateinit var reenter_password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        switchlogin = findViewById(R.id.switch_login)
        signupBtn = findViewById(R.id.signup)

        binding.switchLogin.setOnClickListener{
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }

        reg_email = findViewById(R.id.reg_email)
        reg_password = findViewById(R.id.reg_password)
        reenter_password = findViewById(R.id.reg_again)


        firebaseAuth = FirebaseAuth.getInstance()
        binding.signup.setOnClickListener{
            val email = binding.regEmail.text.toString()
            val pass = binding.regPassword.text.toString()
            val conPass = binding.regAgain.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && conPass.isNotEmpty()){

                if(pass == conPass){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent = Intent(this,SigninActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    Toast.makeText(this,"Password is not matching",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"Empty fields are not allowed",Toast.LENGTH_LONG).show()
            }
        }


    }
}