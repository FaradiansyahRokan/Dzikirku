package com.rokan.dzikirku.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rokan.dzikirku.R
import com.rokan.dzikirku.adapter.DzikirkuAdapter
import com.rokan.dzikirku.databinding.FragmentSoreBinding
import com.rokan.dzikirku.model.DataDzikirku

class soreFragment : Fragment() {
    lateinit var binding: FragmentSoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSoreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvDzikirSore.layoutManager = LinearLayoutManager(context)
        binding.rvDzikirSore.adapter = DzikirkuAdapter(DataDzikirku.listDzikirPetang)
    }
}