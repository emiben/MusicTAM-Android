package com.theagilemonkeys.musictam.adapters

import android.view.View
import com.theagilemonkeys.musictam.R
import com.theagilemonkeys.musictam.databinding.ArtistItemBinding
import com.theagilemonkeys.musictam.databinding.ArtistSearchItemBinding
import com.theagilemonkeys.musictam.models.Artist
import com.theagilemonkeys.musictam.utils.BaseRecyclerAdapter

class SearchArtistsAdapter(val onItemClicked: (artist: Artist, view: View) -> Unit) :
    BaseRecyclerAdapter<ArtistSearchItemBinding, Artist>(R.layout.artist_search_item) {

    override fun bindItem(binding: ArtistSearchItemBinding, item: Artist, position: Int) {
        binding.artistSearchTextView.text = item.artistName
        binding.genreSearchTextView.text = item.primaryGenreName

        binding.root.setOnClickListener {
            onItemClicked(item, it)
        }
    }
}