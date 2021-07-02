package com.theagilemonkeys.musictam.adapters

import android.view.View
import com.bumptech.glide.Glide
import com.theagilemonkeys.musictam.R
import com.theagilemonkeys.musictam.databinding.AlbumItemBinding
import com.theagilemonkeys.musictam.databinding.ArtistItemBinding
import com.theagilemonkeys.musictam.databinding.ArtistSearchItemBinding
import com.theagilemonkeys.musictam.databinding.SongItemBinding
import com.theagilemonkeys.musictam.models.Album
import com.theagilemonkeys.musictam.models.Artist
import com.theagilemonkeys.musictam.models.Song
import com.theagilemonkeys.musictam.utils.BaseRecyclerAdapter

class SongsAdapter(val onItemClicked: (song: Song, view: View) -> Unit) :
    BaseRecyclerAdapter<SongItemBinding, Song>(R.layout.song_item) {

    override fun bindItem(binding: SongItemBinding, item: Song, position: Int) {
        binding.songNameTextView.text = item.trackName
        Glide.with(binding.songImageView.context).load(item.artworkUrl100).into(binding.songImageView)

        binding.root.setOnClickListener {
            onItemClicked(item, it)
        }
    }
}