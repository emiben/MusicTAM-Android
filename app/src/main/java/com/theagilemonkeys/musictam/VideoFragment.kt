package com.theagilemonkeys.musictam

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.VideoView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.theagilemonkeys.musictam.databinding.FragmentVideoBinding
import com.theagilemonkeys.musictam.models.Song

const val SONG = "song"

class VideoFragment : Fragment() {
    private var song: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            song = it.getParcelable(SONG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewRoot = inflater.inflate(R.layout.fragment_video, container, false)
        val binding = DataBindingUtil.bind<FragmentVideoBinding>(viewRoot)

        if(song != null && song?.previewUrl != null){
            binding?.setVariable(BR.error, false)
            playSong(binding?.videoContainer)

            binding?.albumImageView?.context?.let { Glide.with(it).load(song?.artworkUrl100).into(
                        binding.albumImageView
                    )
            }
            binding?.songNameTextView?.text = song?.trackName

            binding?.albumImageView?.animation = AnimationUtils.loadAnimation(context, R.anim.up_to_down)
            binding?.videoContainer?.animation = AnimationUtils.loadAnimation(context, R.anim.up_to_down)
            binding?.nowPlayingTextView?.animation = AnimationUtils.loadAnimation(context, R.anim.left_to_right)
            binding?.songNameTextView?.animation = AnimationUtils.loadAnimation(context, R.anim.right_to_left)
            binding?.playImageView?.animation = AnimationUtils.loadAnimation(context, R.anim.down_to_up)


            binding?.playImageView?.setOnClickListener {
                playSong(binding.videoContainer)
            }

        } else {
            binding?.setVariable(BR.error, true)
        }

        return binding?.root
    }

    private fun playSong(videoView: VideoView?) {
        videoView?.setVideoPath(song?.previewUrl)
        videoView?.start()
    }

}