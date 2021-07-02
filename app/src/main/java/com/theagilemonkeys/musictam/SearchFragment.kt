package com.theagilemonkeys.musictam

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.theagilemonkeys.musictam.adapters.SearchArtistsAdapter
import com.theagilemonkeys.musictam.databinding.FragmentSearchBinding
import com.theagilemonkeys.musictam.models.Artist
import com.theagilemonkeys.musictam.network.clients.MusicClient
import com.theagilemonkeys.musictam.network.repositories.ArtistsRepository
import com.theagilemonkeys.musictam.utils.BaseBindingFragment
import com.theagilemonkeys.musictam.utils.getRandomLetter
import com.theagilemonkeys.musictam.utils.hideKeyboard
import com.theagilemonkeys.musictam.viewmodels.MainViewModel
import com.theagilemonkeys.musictam.viewmodels.SearchArtistsViewModel
import com.theagilemonkeys.musictam.viewmodels.SearchFragmentViewModel
import kotlinx.coroutines.Dispatchers


class SearchFragment : BaseBindingFragment() {

    private lateinit var mainViewModel: MainViewModel

    private val searchFragmentViewModel = SearchFragmentViewModel()

    private val searchArtistsViewModel: SearchArtistsViewModel by lazy {
        SearchArtistsViewModel(ArtistsRepository(MusicClient().artistsService), Dispatchers.IO)
    }

    private val searchArtistsAdapter: SearchArtistsAdapter by lazy {
        SearchArtistsAdapter { artist: Artist, view: View ->
            onArtistClicked(artist, view)
        }
    }

    override fun setUpBinding() {
        return setBinding(
            R.layout.fragment_search,
            mapOf(
                BR.searchFragmentViewModel to searchFragmentViewModel,
                BR.searchArtistsViewModel to searchArtistsViewModel,
                BR.searchArtistsAdapter to searchArtistsAdapter
            )
        )
    }

    override fun setUpUI() {
        activity?.run {
            mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Throwable("invalid activity")
        mainViewModel.updateActionBarTitle(getString(R.string.app_name))

        searchFragmentViewModel.searchQueryLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                searchArtistsViewModel.executeAPI(it)
            }
        })

        searchArtistsViewModel.contentLiveData.observe(viewLifecycleOwner, Observer {
            searchArtistsAdapter.listOfItems = it

            with(getBinding() as FragmentSearchBinding) {
                val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_up_to_down_search)
                searchRecyclerView.layoutAnimation = controller
                searchRecyclerView.scheduleLayoutAnimation()
            }
        })
        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    override fun executeViewModels() {
        searchArtistsViewModel.executeAPI(getRandomLetter())
    }

    private fun onArtistClicked(artist: Artist, view: View) {
        val bundle = Bundle()
        artist.amgArtistId?.let { bundle.putInt(AMG_ARTIST_ID, it) }
        artist.artistName?.let { bundle.putString(ARTIST_NAME, it) }
        view.findNavController().navigate(R.id.action_searchFragment_to_albumsFragment, bundle)
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }


}