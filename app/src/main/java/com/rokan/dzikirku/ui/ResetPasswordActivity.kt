package com.rokan.dzikirku.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.rokan.dzikirku.R

class ResetPasswordActivity : AppCompatActivity() {
    lateinit var resPassword : TextInputEditText
    lateinit var btnResetPassword: Button
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        supportActionBar?.hide()

        resPassword = findViewById(R.id.resetEmail)
        btnResetPassword = findViewById(R.id.btnReset)
        auth = FirebaseAuth.getInstance()

        btnResetPassword.setOnClickListener{
            val sPassword = resPassword.text.toString()
            auth.sendPasswordResetEmail(sPassword)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext , "Check Email" , Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(applicationContext , "Error" , Toast.LENGTH_SHORT).show()
                }
        }
    }
}