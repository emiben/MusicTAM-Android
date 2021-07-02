package com.theagilemonkeys.musictam

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.theagilemonkeys.musictam.adapters.AlbumsAdapter
import com.theagilemonkeys.musictam.databinding.FragmentAlbumsBinding
import com.theagilemonkeys.musictam.models.Album
import com.theagilemonkeys.musictam.network.clients.MusicClient
import com.theagilemonkeys.musictam.network.repositories.AlbumsRepository
import com.theagilemonkeys.musictam.utils.BaseBindingFragment
import com.theagilemonkeys.musictam.utils.Constants.COLLECTION
import com.theagilemonkeys.musictam.viewmodels.AlbumsViewModel
import com.theagilemonkeys.musictam.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers

const val AMG_ARTIST_ID = "artist_id"
const val ARTIST_NAME = "artist_name"

class AlbumsFragment : BaseBindingFragment() {

    private lateinit var mainViewModel: MainViewModel

    private val albumsViewModel: AlbumsViewModel by lazy {
        AlbumsViewModel(AlbumsRepository(MusicClient().albumsService), Dispatchers.IO)
    }

    private val albumsAdapter: AlbumsAdapter by lazy {
        AlbumsAdapter { album: Album, view: View ->
            onAlbumClicked(album, view)
        }
    }

    override fun setUpBinding() {
        return setBinding(
            R.layout.fragment_albums, mapOf(
                BR.albumsViewModel to albumsViewModel,
                BR.albumsAdapter to albumsAdapter
            )
        )
    }

    override fun setUpUI() {
        activity?.run {
            mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Throwable("invalid activity")
        arguments?.let {
            mainViewModel.updateActionBarTitle(it.get(ARTIST_NAME).toString())
        }
        albumsViewModel.contentLiveData.observe(viewLifecycleOwner, Observer {
            albumsAdapter.listOfItems = it.filter { album -> album.wrapperType == COLLECTION }

            with(getBinding() as FragmentAlbumsBinding) {
                val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_right_to_left)
                albumsRecyclerView.layoutAnimation = controller
                albumsRecyclerView.scheduleLayoutAnimation()
            }
        })
    }

    override fun executeViewModels() {
        arguments?.let {
            albumsViewModel.executeAPI(it.get(AMG_ARTIST_ID).toString())
        }
    }

    private fun onAlbumClicked(album: Album, view: View) {
        val bundle = Bundle()
        album.collectionId?.let { bundle.putInt(COLLECTION_ID, it) }
        album.collectionName?.let { bundle.putString(COLLECTION_NAME, it) }
        view.findNavController().navigate(R.id.action_albumsFragment_to_songsFragment, bundle)
    }

}