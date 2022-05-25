package com.finanskatilim.itunessearchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finanskatilim.itunessearchapp.databinding.AudiobookItemBinding
import com.finanskatilim.itunessearchapp.model.AudioBook
import com.finanskatilim.itunessearchapp.viewholder.AudioBookViewHolder

class AudioBookAdapter (
        var context: Context,
        var liste: ArrayList<AudioBook>,
        var itemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<AudioBookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioBookViewHolder {
        val from = LayoutInflater.from(context)
        val binding = AudiobookItemBinding.inflate(from, parent, false)
        return AudioBookViewHolder(context, binding, itemClick)
    }

    override fun onBindViewHolder(holder: AudioBookViewHolder, position: Int) {
        holder.bindData(liste.get(position))
    }

    override fun getItemCount(): Int {
        return liste.size
    }
}