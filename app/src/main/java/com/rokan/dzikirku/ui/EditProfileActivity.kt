package com.rokan.dzikirku.ui

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.rokan.dzikirku.R
import com.rokan.dzikirku.databinding.ActivityEditProfileBinding
import com.rokan.dzikirku.model.User
class EditProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditProfileBinding
    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var storageReference: StorageReference
    lateinit var imageUri: Uri
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading Profile...")
        progressDialog.setCancelable(false)

        binding.saveBtn.setOnClickListener {
            progressDialog.show()

            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()

            val user = User(firstName, lastName)
            val uid = auth.currentUser?.uid
            if (uid != null) {
                databaseReference.child(uid).setValue(user).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        progressDialog.dismiss()
                        uploadProfilePic()
                        val intent = Intent(this , ProfileActivity::class.java)
                        startActivity(intent)
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed To Update Profile", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.profile}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/${auth.currentUser?.uid}")
        storageReference.putFile(imageUri).addOnSuccessListener {
            progressDialog.dismiss()
            Toast.makeText(this, "Successfully Update Profile", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            progressDialog.dismiss()
            Toast.makeText(this, "Failed Update Profile", Toast.LENGTH_SHORT).show()
        }
    }
}
