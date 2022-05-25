package com.finanskatilim.itunessearchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finanskatilim.itunessearchapp.databinding.TrackItemBinding
import com.finanskatilim.itunessearchapp.model.Result
import com.finanskatilim.itunessearchapp.model.Track
import com.finanskatilim.itunessearchapp.viewholder.TrackViewHolder

class TrackAdapter (
        var context: Context,
        var liste: ArrayList<Track>,
        var itemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<TrackViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val from = LayoutInflater.from(context)
        val binding = TrackItemBinding.inflate(from, parent, false)
        return TrackViewHolder(context, binding, itemClick)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bindData(liste.get(position))
    }

    override fun getItemCount(): Int {
        return liste.size
    }
}