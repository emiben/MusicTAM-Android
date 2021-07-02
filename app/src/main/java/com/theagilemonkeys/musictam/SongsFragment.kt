package com.theagilemonkeys.musictam

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.theagilemonkeys.musictam.adapters.SongsAdapter
import com.theagilemonkeys.musictam.databinding.FragmentSongsBinding
import com.theagilemonkeys.musictam.models.Song
import com.theagilemonkeys.musictam.network.clients.MusicClient
import com.theagilemonkeys.musictam.network.repositories.SongsRepository
import com.theagilemonkeys.musictam.utils.BaseBindingFragment
import com.theagilemonkeys.musictam.utils.Constants.TRACK
import com.theagilemonkeys.musictam.viewmodels.MainViewModel
import com.theagilemonkeys.musictam.viewmodels.SongsViewModel
import kotlinx.coroutines.Dispatchers

const val COLLECTION_ID = "collection_id"
const val COLLECTION_NAME = "collection_name"

class SongsFragment : BaseBindingFragment() {

    private lateinit var mainViewModel: MainViewModel

    private val songsViewModel: SongsViewModel by lazy {
        SongsViewModel(SongsRepository(MusicClient().songsService), Dispatchers.IO)
    }

    private val songsAdapter: SongsAdapter by lazy {
        SongsAdapter { song: Song, view: View ->
            onSongClicked(song, view)
        }
    }

    override fun setUpBinding() {
        return setBinding(R.layout.fragment_songs, mapOf(
            BR.songsViewModel to songsViewModel,
            BR.songsAdapter to songsAdapter
        ))
    }

    override fun setUpUI() {
        activity?.run {
            mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Throwable("invalid activity")
        arguments?.let {
            mainViewModel.updateActionBarTitle(it.get(COLLECTION_NAME).toString())
        }

        songsViewModel.contentLiveData.observe(viewLifecycleOwner, Observer {
            songsAdapter.listOfItems = it.filter { song ->  song.wrapperType == TRACK}

            with(getBinding() as FragmentSongsBinding){
                val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_up_to_down)
                songsRecyclerView.layoutAnimation = controller
                songsRecyclerView.scheduleLayoutAnimation()
            }
        })
    }

    override fun executeViewModels() {
        arguments?.let {
            songsViewModel.executeAPI(it.get(COLLECTION_ID).toString())
        }
    }

    private fun onSongClicked(song: Song, view: View) {
        val bundle = Bundle()
        bundle.putParcelable(SONG, song)
        view.findNavController().navigate(R.id.action_songsFragment_to_videoFragment, bundle)
    }
}