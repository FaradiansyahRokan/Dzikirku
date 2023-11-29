package com.rokan.dzikirku.ui


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.rokan.dzikirku.MainActivity
import com.rokan.dzikirku.R
import com.rokan.dzikirku.adapter.DzikirkuAdapter
import com.rokan.dzikirku.databinding.ActivityDzikirHarianBinding
import com.rokan.dzikirku.model.DataDzikirku
import com.rokan.dzikirku.model.DzikirkuModel

class DzikirHarianActivity : AppCompatActivity() {
    lateinit var toogle : ActionBarDrawerToggle
    lateinit var binding: ActivityDzikirHarianBinding
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDzikirHarianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val title = arrayListOf<DataDzikirku>()
        val userAdapter : ArrayAdapter<DataDzikirku> = ArrayAdapter(
            this,android.R.layout.simple_list_item_1, title
        )

        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    val intentDOS = Intent(this@DzikirHarianActivity , MainActivity::class.java)
                    startActivity(intentDOS)
                    Toast.makeText(applicationContext, "Menuju Ke Halaman Home", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_dzikirshlat -> {
                   val intentDos = Intent(this@DzikirHarianActivity , DzikirSholatActivity::class.java)
                    startActivity(intentDos)
                    Toast.makeText(applicationContext , "Menuju Halaman Dzikir Sholat" , Toast.LENGTH_SHORT).show()
                }
                R.id.nav_pagipetang -> {
                    val intentDos = Intent(this@DzikirHarianActivity , DzikirPagiPetangActivity::class.java)
                    startActivity(intentDos)
                    Toast.makeText(applicationContext , "Menuju Halaman Dzikir Pagi Petang" , Toast.LENGTH_SHORT).show()
                }
                R.id.nav_harian -> {
                   if (isOnHomePage()){
                       Toast.makeText(applicationContext , "Anda Sudah Di Halaman Dzikir Harian" , Toast.LENGTH_SHORT).show()
                   }
                }
                R.id.nav_setiapsaat -> {
                    val intentDOS = Intent(this@DzikirHarianActivity, DzikirSetiapSaat::class.java)
                    startActivity(intentDOS)
                    Toast.makeText(applicationContext , "Menuju Halaman Dzikir Setiap Saat" , Toast.LENGTH_SHORT).show()
                }
                R.id.nav_login -> {
                    if (isOnHomePage()){
                        val intentDOS = Intent(this@DzikirHarianActivity , ProfileActivity::class.java)
                        startActivity(intentDOS)
                    }
                }
            }
            true
        }

        binding.apply {
            rvHarian.layoutManager = LinearLayoutManager(this@DzikirHarianActivity)
            rvHarian.adapter = DzikirkuAdapter(getDzikirData())
        }

    }
        fun getDzikirData() : ArrayList<DzikirkuModel>{
            val desc = resources.getStringArray(R.array.arr_dzikir_doa_harian)
            val lafadz = resources.getStringArray(R.array.arr_lafaz_dzikir_doa_harian)
            val terjemah = resources.getStringArray(R.array.arr_terjemah_dzikir_doa_harian)

            val getAll = arrayListOf<DzikirkuModel>()

            for ( i in desc.indices){
                val dzikirDoa = DzikirkuModel(
                    desc[i],
                    lafadz[i],
                    terjemah[i]
                )
                getAll.add(dzikirDoa)
            }
            return getAll
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
    private fun isOnHomePage(): Boolean {
        // Tambahkan logika di sini untuk memeriksa apakah pengguna berada di halaman "Home"
        // Misalnya, Anda dapat memeriksa apakah Activity saat ini adalah MainActivity
        return this::class == DzikirSholatActivity::class
    }
    }