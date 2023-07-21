package com.odc.dataapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.odc.dataapp.MainActivity
import com.odc.dataapp.databinding.DispositionLigneCovidBinding
import com.odc.dataapp.models.Comments

class ListAdapterCovid(val ctx: MainActivity, val mCommentsData: List<Comments>) :
    RecyclerView.Adapter<ListAdapterCovid.MainViewHolder>() {

    inner class MainViewHolder(val binding: DispositionLigneCovidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(comments: Comments) {
            // attacher les valeurs aux vues avec item
            binding?.dateCovidID?.text = "Nom : ${comments.name}"
            binding?.casCovidID?.text = "email : ${comments.email}"
            binding?.ID?.text = "ID : ${comments.id}"
            binding.root.setOnClickListener {

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainViewHolder(DispositionLigneCovidBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val covid = mCommentsData[position]
        holder.bindItem(covid)
    }

    override fun getItemCount(): Int {
        return mCommentsData.size
    }
}