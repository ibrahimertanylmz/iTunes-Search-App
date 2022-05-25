package com.finanskatilim.itunessearchapp.ui.view

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.finanskatilim.itunessearchapp.R
import com.finanskatilim.itunessearchapp.databinding.FragmentTrackDetailsBinding
import com.finanskatilim.itunessearchapp.model.LoadState
import com.finanskatilim.itunessearchapp.model.Track
import com.finanskatilim.itunessearchapp.service.TunesApiService
import com.finanskatilim.itunessearchapp.ui.base.TunesViewModelFactory
import com.finanskatilim.itunessearchapp.ui.viewmodel.TunesViewModel
import java.io.IOException
import java.lang.Exception


class TrackDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTrackDetailsBinding
    lateinit var viewModelFactoryTunes: ViewModelProvider.Factory
    private lateinit var tunesViewModel: TunesViewModel
    private var trackId: Int = -1
    private val args: TrackDetailsFragmentArgs by navArgs()
    private var mediaPlayer : MediaPlayer?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackDetailsBinding.inflate(inflater)

        trackId = args.trackId

        initializeViewModel()
        initializeObservers()



        return binding.root
    }

    private fun initializeViewModel() {
        val tunesApiService = TunesApiService()
        viewModelFactoryTunes = TunesViewModelFactory(tunesApiService)
        tunesViewModel =
            ViewModelProvider(this, viewModelFactoryTunes).get(TunesViewModel::class.java)
    }

    private fun initializeObservers() {
        tunesViewModel.getTrackDetailsById(trackId.toString())
        tunesViewModel.trackDetailsLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onTrackDetailsLoadingStateChanged(it)
        })
    }

    private fun onTrackDetailsLoadingStateChanged(it: LoadState) {
        if (it == LoadState.Loaded) {
            tunesViewModel.trackDetailsLiveData.observe(viewLifecycleOwner, Observer {
                onTrackLoaded(it)
            })
        } else {

        }
    }

    private fun onTrackLoaded(track: Track) {
        Glide.with(requireActivity())
            .load(track.artworkUrl)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error_loading_image)
            .into(binding.detailImageTrack)

        binding.tvTrackName.text = track.trackName
        binding.tvTrackGenre.text = track.primaryGenre
        
            if (track.kind == "song"){
                controlSound(track.previewUrl)
            }


        

    }

    private fun controlSound(audioUrl: String){
        binding.btnPlay.setOnClickListener {
            if(mediaPlayer==null){
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
                try {
                    mediaPlayer?.setDataSource(audioUrl)
                    mediaPlayer?.prepare()
                } catch (e: IOException) {
                    binding.seekBar.progress = 0
                    e.printStackTrace()
                }
                initializeSeekBar()
            }
            mediaPlayer?.start()
        }
        binding.btnPause.setOnClickListener {
            if(mediaPlayer!= null){
                mediaPlayer?.pause()
            }
        }
        binding.btnStop.setOnClickListener {
            if (mediaPlayer!= null){
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2){
                    mediaPlayer?.seekTo(p1)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
    }

    private fun initializeSeekBar(){
        binding.seekBar.max = mediaPlayer!!.duration
        val handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                try {
                    binding.seekBar.progress = mediaPlayer!!.currentPosition
                    handler.postDelayed(this, 1000)
                }catch (e: Exception){
                    binding.seekBar.progress = 0
                }
            }
        },0)
    }

}