package com.theagilemonkeys.musictam.adapters

import android.view.View
import com.bumptech.glide.Glide
import com.theagilemonkeys.musictam.R
import com.theagilemonkeys.musictam.databinding.AlbumItemBinding
import com.theagilemonkeys.musictam.databinding.ArtistItemBinding
import com.theagilemonkeys.musictam.databinding.ArtistSearchItemBinding
import com.theagilemonkeys.musictam.models.Album
import com.theagilemonkeys.musictam.models.Artist
import com.theagilemonkeys.musictam.utils.BaseRecyclerAdapter

class AlbumsAdapter(val onItemClicked: (album: Album, view: View) -> Unit) :
    BaseRecyclerAdapter<AlbumItemBinding, Album>(R.layout.album_item) {

    override fun bindItem(binding: AlbumItemBinding, item: Album, position: Int) {
        binding.albumNameTextView.text = item.collectionName
        binding.albumYearTextView.text = item.releaseDate?.substring(0, 4)

        Glide.with(binding.albumImageView.context).load(item.artworkUrl60).into(binding.albumImageView)

        binding.root.setOnClickListener {
            onItemClicked(item, it)
        }
    }
}