package com.rokan.dzikirku.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.rokan.dzikirku.MainActivity
import com.rokan.dzikirku.R
import com.rokan.dzikirku.adapter.DzikirkuAdapter
import com.rokan.dzikirku.databinding.ActivityDzikirSholatBinding
import com.rokan.dzikirku.model.DataDzikirku
import com.rokan.dzikirku.model.DzikirkuModel
import java.util.*
import kotlin.collections.ArrayList

class DzikirSholatActivity : AppCompatActivity() {
    lateinit var toogle : ActionBarDrawerToggle
    lateinit var binding: ActivityDzikirSholatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<DzikirkuModel>()
    private lateinit var adapter: DzikirkuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDzikirSholatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        adapter = DzikirkuAdapter(mList)
        recyclerView = findViewById(R.id.rv_qauliyah_shalat)
        adapter = DzikirkuAdapter(mList)

        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    val intentDOS = Intent(this@DzikirSholatActivity , MainActivity::class.java)
                    startActivity(intentDOS)
                    Toast.makeText(applicationContext, "Menuju Ke Halaman Home", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_dzikirshlat -> {
                    if (isOnHomePage()){
                        Toast.makeText(applicationContext , "Kamu Sudah Di Dzikir Sholat" , Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.nav_pagipetang -> {
                    val intentDOS = Intent(this@DzikirSholatActivity, DzikirPagiPetangActivity::class.java)
                    startActivity(intentDOS)
                    Toast.makeText(applicationContext , "Menuju Halaman Dzikir Pagi Dan Petang" , Toast.LENGTH_SHORT).show()
                }
                R.id.nav_harian -> {
                    val intentDOS = Intent(this@DzikirSholatActivity, DzikirHarianActivity::class.java)
                    startActivity(intentDOS)
                    Toast.makeText(applicationContext , "Menuju Halaman Dzikir Harian" , Toast.LENGTH_SHORT).show()
                }
                R.id.nav_setiapsaat -> {
                    val intentDOS = Intent(this@DzikirSholatActivity, DzikirSetiapSaat::class.java)
                    startActivity(intentDOS)
                    Toast.makeText(applicationContext , "Menuju Halaman Dzikir Setiap Saat" , Toast.LENGTH_SHORT).show()
                }
                R.id.nav_login -> {
                    if (isOnHomePage()){
                        val intentDOS = Intent(this@DzikirSholatActivity , ProfileActivity::class.java)
                        startActivity(intentDOS)
                    }
                }
            }
            true
        }
        binding.apply {
            rvQauliyahShalat.layoutManager = LinearLayoutManager(this@DzikirSholatActivity)
            rvQauliyahShalat.adapter = DzikirkuAdapter(DataDzikirku.listQauliyah)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
    private fun isOnHomePage(): Boolean {
        return this::class == DzikirSholatActivity::class
    }
}