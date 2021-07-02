package com.theagilemonkeys.musictam

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.theagilemonkeys.musictam.adapters.RandomArtistsAdapter
import com.theagilemonkeys.musictam.databinding.FragmentHomeBinding
import com.theagilemonkeys.musictam.models.Artist
import com.theagilemonkeys.musictam.network.clients.MusicClient
import com.theagilemonkeys.musictam.network.repositories.ArtistsRepository
import com.theagilemonkeys.musictam.utils.BaseBindingFragment
import com.theagilemonkeys.musictam.viewmodels.MainViewModel
import com.theagilemonkeys.musictam.viewmodels.RandomArtistsViewModel
import kotlinx.coroutines.Dispatchers


class HomeFragment : BaseBindingFragment() {

    private lateinit var mainViewModel: MainViewModel

    private val randomArtistsViewModel: RandomArtistsViewModel by lazy {
        RandomArtistsViewModel(ArtistsRepository(MusicClient().artistsService), Dispatchers.IO)
    }

    private val artistsAdapter: RandomArtistsAdapter by lazy {
        RandomArtistsAdapter { artist: Artist, view: View ->
            onArtistClicked(artist, view)
        }
    }

    override fun setUpBinding() {
        return setBinding(
            R.layout.fragment_home,
            mapOf(
                BR.artistAdapter to artistsAdapter,
                BR.randomArtistViewModel to randomArtistsViewModel
            )
        )
    }

    override fun setUpUI() {
        activity?.run {
            mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Throwable("invalid activity")
        mainViewModel.updateActionBarTitle(getString(R.string.app_name))

        randomArtistsViewModel.contentLiveData.observe(viewLifecycleOwner, {
            artistsAdapter.listOfItems = it

            with(getBinding() as FragmentHomeBinding) {
                val controller =
                    AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_left_to_right)
                randomArtistsRecyclerView.layoutAnimation = controller
                randomArtistsRecyclerView.scheduleLayoutAnimation()
            }
        })
        (getBinding() as FragmentHomeBinding).searchBar.containerSearch.setOnClickListener {
            onSearchClicked(it)
        }
    }

    override fun executeViewModels() {
        randomArtistsViewModel.executeAPI()
    }

    private fun onSearchClicked(view: View) {
        view.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
    }

    private fun onArtistClicked(artist: Artist, view: View) {
        val bundle = Bundle()
        artist.amgArtistId?.let { bundle.putInt(AMG_ARTIST_ID, it) }
        artist.artistName?.let { bundle.putString(ARTIST_NAME, it) }
        view.findNavController().navigate(R.id.action_homeFragment_to_albumsFragment, bundle)
    }

}