package com.rokan.dzikirku.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rokan.dzikirku.databinding.RowItemDzikirBinding
import com.rokan.dzikirku.model.DzikirkuModel

class DzikirkuAdapter (private var listDzikirku : ArrayList<DzikirkuModel>) : RecyclerView.Adapter<DzikirkuAdapter.MyViewHolder>(){
    class MyViewHolder(val binding: RowItemDzikirBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val bindingDzikirDoa = RowItemDzikirBinding.inflate(LayoutInflater.from(parent.context) , parent , false)

        return MyViewHolder(bindingDzikirDoa)
    }

    override fun getItemCount(): Int = listDzikirku.size


    override fun onBindViewHolder(holder: DzikirkuAdapter.MyViewHolder, position: Int) {
        val dataDzikirku = listDzikirku[position]

        holder.binding.apply {
            tvDesc.text = dataDzikirku.desc
            tvLafaz.text = dataDzikirku.lafadz
            tvTerjemah.text = dataDzikirku.terjemah
        }
    }
}