package com.rokan.dzikirku.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.rokan.dzikirku.MainActivity
import com.rokan.dzikirku.R
import com.rokan.dzikirku.adapter.pagipetangAdapter
import com.rokan.dzikirku.ui.detail.pagiFragment
import com.rokan.dzikirku.ui.detail.soreFragment

class DzikirPagiPetangActivity : AppCompatActivity() {
    lateinit var toogle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dzikir_pagi_petang)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    val intentDOS = Intent(this@DzikirPagiPetangActivity , MainActivity::class.java)
                    startActivity(intentDOS)
                    Toast.makeText(applicationContext, "Menuju Ke Halaman Home", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_dzikirshlat -> {
                    val intentDOS = Intent(this@DzikirPagiPetangActivity , DzikirSholatActivity::class.java)
                    startActivity(intentDOS)
                    Toast.makeText(applicationContext , "Menuju Halaman Dzikir Sholat" , Toast.LENGTH_SHORT).show()
                }
                R.id.nav_harian -> {
                    val intentDOS = Intent(this@DzikirPagiPetangActivity, DzikirHarianActivity::class.java)
                    startActivity(intentDOS)
                    Toast.makeText(applicationContext , "Menuju Halaman Dzikir Harian" , Toast.LENGTH_SHORT).show()
                }
                R.id.nav_pagipetang -> {
                    if (isOnHomePage()){
                        Toast.makeText(applicationContext , "Kamu Sudah Di halaman Dzikir Pagi Petang" , Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.nav_setiapsaat -> {
                    val intentDOS = Intent(this@DzikirPagiPetangActivity, DzikirSetiapSaat::class.java)
                    startActivity(intentDOS)
                    Toast.makeText(applicationContext , "Menuju Halaman Dzikir Setiap Saat" , Toast.LENGTH_SHORT).show()
                }
                R.id.nav_login -> {
                    if (isOnHomePage()){
                        val intentDOS = Intent(this@DzikirPagiPetangActivity , ProfileActivity::class.java)
                        startActivity(intentDOS)
                    }
                }
            }
            true
        }

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tablayout = findViewById<TabLayout>(R.id.tablayout)

        val fragmentAdapter = pagipetangAdapter(supportFragmentManager)
        fragmentAdapter.addFragment(pagiFragment(),"Pagi")
        fragmentAdapter.addFragment(soreFragment(),"Sore")

        viewPager.adapter = fragmentAdapter
        tablayout.setupWithViewPager(viewPager)
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
        return this::class == DzikirPagiPetangActivity::class
    }
}

