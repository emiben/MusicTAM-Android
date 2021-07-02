package com.theagilemonkeys.musictam.adapters

import android.view.View
import com.theagilemonkeys.musictam.R
import com.theagilemonkeys.musictam.databinding.ArtistItemBinding
import com.theagilemonkeys.musictam.models.Artist
import com.theagilemonkeys.musictam.utils.BaseRecyclerAdapter

class RandomArtistsAdapter(val onItemClicked: (artist: Artist, view: View) -> Unit) :
    BaseRecyclerAdapter<ArtistItemBinding, Artist>(R.layout.artist_item) {

    override fun bindItem(binding: ArtistItemBinding, item: Artist, position: Int) {
        binding.artistNameTextView.text = item.artistName
        binding.artistGenreTextView.text = item.primaryGenreName

        binding.root.setOnClickListener {
            onItemClicked(item, it)
        }
    }
}