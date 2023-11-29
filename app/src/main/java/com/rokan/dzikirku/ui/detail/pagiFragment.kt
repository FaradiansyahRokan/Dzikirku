package com.rokan.dzikirku.ui.detail

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rokan.dzikirku.R
import com.rokan.dzikirku.adapter.DzikirkuAdapter
import com.rokan.dzikirku.databinding.ActivityDzikirHarianBinding
import com.rokan.dzikirku.databinding.FragmentPagiBinding
import com.rokan.dzikirku.model.DataDzikirku
class pagiFragment : Fragment() {
    lateinit var binding: FragmentPagiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentPagiBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvDzikirPagi.layoutManager = LinearLayoutManager(context)
        binding.rvDzikirPagi.adapter = DzikirkuAdapter(DataDzikirku.listDzikirPagi)
    }
}

