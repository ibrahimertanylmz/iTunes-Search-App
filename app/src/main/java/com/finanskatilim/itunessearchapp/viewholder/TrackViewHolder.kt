package com.finanskatilim.itunessearchapp.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.finanskatilim.itunessearchapp.R
import com.finanskatilim.itunessearchapp.databinding.TrackItemBinding
import com.finanskatilim.itunessearchapp.model.Track

class TrackViewHolder (
        var context: Context,
        private val binding: TrackItemBinding,
        var itemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            itemClick(adapterPosition)
        }
    }

    fun bindData(track: Track) {
       Glide.with(context)
                .load(track.artworkUrl)
                .transform(RoundedCorners(12))
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error_loading_image)
                .into(binding.imageTrack);
        binding.tvTitle.text = track.trackName
        binding.tvKind.text = track.kind
        binding.tvPrimaryGenreName.text = track.primaryGenre
    }
}