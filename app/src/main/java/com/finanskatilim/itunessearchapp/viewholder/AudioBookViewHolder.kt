package com.finanskatilim.itunessearchapp.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.finanskatilim.itunessearchapp.R
import com.finanskatilim.itunessearchapp.databinding.AudiobookItemBinding
import com.finanskatilim.itunessearchapp.model.AudioBook

class AudioBookViewHolder(
        var context: Context,
        private val binding: AudiobookItemBinding,
        var itemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            itemClick(adapterPosition)
        }
    }

    fun bindData(audioBook: AudioBook) {
        Glide.with(context)
                .load(audioBook.artworkUrl)
                .transform(RoundedCorners(12))
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error_loading_image)
                .into(binding.imageAudioBook);
        binding.tvCollection.text = audioBook.collectionName
        binding.tvArtist.text = audioBook.artistName
        binding.tvPrimaryGenreName.text = audioBook.primaryGenre
    }
}