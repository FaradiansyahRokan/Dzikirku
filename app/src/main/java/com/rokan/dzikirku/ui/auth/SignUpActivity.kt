package com.rokan.dzikirku.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.rokan.dzikirku.R
import com.rokan.dzikirku.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        supportActionBar?.hide()
        binding.textView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val firstNameEditText = binding.fName.text.toString()
            val lastNameEditText = binding.lName.text.toString()

            if (email.isEmpty() || pass.isEmpty() || firstNameEditText.isEmpty() || lastNameEditText.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            saveUserDataToDatabase(user?.uid, firstNameEditText, lastNameEditText)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Registrasi gagal: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    private fun saveUserDataToDatabase(uid: String?, firstName: String, lastName: String) {
        if (uid != null) {
            val userRef = FirebaseDatabase.getInstance().reference.child("Users").child(uid)

            val userData = HashMap<String, Any>()
            userData["firstName"] = firstName
            userData["lastName"] = lastName

            userRef.updateChildren(userData)
                .addOnSuccessListener {
                    Toast.makeText(this , "Registrasi Berhasil" , Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this , "Gagal" , Toast.LENGTH_SHORT).show()
                }
        }
    }
}