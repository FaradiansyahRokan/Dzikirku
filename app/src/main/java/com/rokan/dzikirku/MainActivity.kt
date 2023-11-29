package com.rokan.dzikirku

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.rokan.dzikirku.databinding.ActivityMainBinding
import com.rokan.dzikirku.ui.*
import com.rokan.dzikirku.ui.auth.LoginActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setView(){
        binding.apply {
            colHarian.setOnClickListener(this@MainActivity)
            colSholat.setOnClickListener(this@MainActivity)
            colSetiapsaat.setOnClickListener(this@MainActivity)
            colHarian.setOnClickListener(this@MainActivity)
            colpagipetang.setOnClickListener(this@MainActivity)
            profileBtn.setOnClickListener(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("" , false)
                searchView.onActionViewCollapsed()
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return true
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.colSetiapsaat -> {
                val intentDOS = Intent(this@MainActivity , DzikirSetiapSaat::class.java)
                startActivity(intentDOS)
                Toast.makeText(applicationContext, "Menuju Ke Halaman Dzikir Setiap Saat", Toast.LENGTH_SHORT).show()
            }
            R.id.colSholat -> {
                val intentDOS = Intent(this@MainActivity , DzikirSholatActivity::class.java)
                startActivity(intentDOS)
                Toast.makeText(applicationContext, "Menuju Ke Halaman Dzikir Sholat", Toast.LENGTH_SHORT).show()
            }
            R.id.colHarian-> {
                val intentDOS = Intent(this@MainActivity , DzikirHarianActivity::class.java)
                startActivity(intentDOS)
                Toast.makeText(applicationContext, "Menuju Ke Halaman Dzikir Harian", Toast.LENGTH_SHORT).show()
            }
            R.id.colpagipetang -> {
                val intentDOS = Intent(this@MainActivity , DzikirPagiPetangActivity::class.java)
                startActivity(intentDOS)
                Toast.makeText(applicationContext, "Menuju Ke Halaman Dzikir Pagi Petang", Toast.LENGTH_SHORT).show()
            }
            R.id.profileBtn -> {
                val intentDOS = Intent(this , ProfileActivity::class.java)
                startActivity(intentDOS)
            }
        }
    }
}