package com.rokan.dzikirku.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.rokan.dzikirku.R
import com.rokan.dzikirku.databinding.ActivityProfileBinding
import com.rokan.dzikirku.model.User
import com.rokan.dzikirku.ui.auth.LoginActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var auth: FirebaseAuth
    lateinit var user: User
    lateinit var uid: String
    lateinit var databaseReference: DatabaseReference
    lateinit var storageReference: StorageReference
    lateinit var progressDialog: ProgressDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menampilkan tombol kembali (back) pada ActionBar jika ada
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Updating Profile...")
        progressDialog.setCancelable(false)

        // Menggunakan metode yang sesuai dari View Binding
        val editProfile = binding.edtProfile
        editProfile.setOnClickListener {
            val intentDOS = Intent(this, EditProfileActivity::class.java)
            startActivity(intentDOS)
        }

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val email = user.email
            binding.emailTextView.text = email
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        if (uid.isNotEmpty()) {
            getUserData()
        }

        val logoutButton = findViewById<Button>(R.id.logoutBtn)
        logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun logout() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun showLogoutConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                logout()
            }
            .setNegativeButton("Batal", null)
            .create()
        alertDialog.show()
    }

    private fun getUserData() {
        // Menampilkan ProgressDialog sebelum mengambil data
        progressDialog.show()
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                binding.profileName.text = user.firstName + " " + user.lastName
                getUserProfile()
                progressDialog.dismiss() // Menutup ProgressDialog setelah selesai
            }

            override fun onCancelled(error: DatabaseError) {
                progressDialog.dismiss()
                Toast.makeText(this@ProfileActivity, "Canceled Update Profile Data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUserProfile() {
        storageReference = FirebaseStorage.getInstance().reference.child("Users/$uid")
        val localFile = File.createTempFile("tempImage", "jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.pro.setImageBitmap(bitmap)
            progressDialog.dismiss()
        }
            .addOnFailureListener { exception ->
                Log.e("StorageException", exception.toString())
                progressDialog.dismiss()
                // Menggunakan Toast dengan konteks yang sesuai
                Toast.makeText(this@ProfileActivity, "Failed retrieve Profile", Toast.LENGTH_SHORT).show()
            }
    }






    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}