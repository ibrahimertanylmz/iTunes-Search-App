package com.finanskatilim.itunessearchapp.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.finanskatilim.itunessearchapp.adapter.AudioBookAdapter
import com.finanskatilim.itunessearchapp.adapter.TrackAdapter
import com.finanskatilim.itunessearchapp.databinding.FragmentSearchBinding
import com.finanskatilim.itunessearchapp.model.AudioBook
import com.finanskatilim.itunessearchapp.model.LoadState
import com.finanskatilim.itunessearchapp.model.Result
import com.finanskatilim.itunessearchapp.model.Track
import com.finanskatilim.itunessearchapp.service.TunesApiService
import com.finanskatilim.itunessearchapp.ui.base.TunesViewModelFactory
import com.finanskatilim.itunessearchapp.ui.viewmodel.TunesViewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var tunesViewModel: TunesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)

        initializeViewModel()
        initializeSearchEvents()
        initializeObservers()

        /*binding.button.setOnClickListener {
            val action =
                    SearchFragmentDirections.actionSearchFragmentToDetailsFragment()
            findNavController().navigate(action)
        }*/





        return binding.root
    }

    private fun initializeSearchEvents() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.length > 2) {
                        tunesViewModel.onSearchQuery(binding.edtSearch.text.toString())
                    } else {
                        updateViewsForResults(0,0)
                    }
                }
            }
        })
    }

    private fun initializeViewModel() {
        val iTunesApiService = TunesApiService()
        viewModelFactory = TunesViewModelFactory(iTunesApiService)
        tunesViewModel =
            ViewModelProvider(this, viewModelFactory).get(TunesViewModel::class.java)
    }

    private fun initializeObservers() {
        tunesViewModel.searchLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onLoadingStateChanged(it)
        })

    }

    private fun onLoadingStateChanged(it: LoadState?) {
        if (it == LoadState.Loaded) {
            tunesViewModel.tracksLiveData.observe(viewLifecycleOwner, Observer {
                onTracksLoaded(it)
            })
            tunesViewModel.audioBooksLiveData.observe(viewLifecycleOwner, Observer {
                onAudioBooksLoaded(it)
            })
        } else {
            println()
        }
    }

    private fun onAudioBooksLoaded(it: ArrayList<AudioBook>) {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvAudioBook.layoutManager = layoutManager
        binding.rvAudioBook.adapter = AudioBookAdapter(requireContext(), tunesViewModel.getAudioBookList(), ::audioBookItemClick)
        updateViewsForResults(tunesViewModel.getTrackListSize(),tunesViewModel.getAudioBookListSize())
    }

    private fun onTracksLoaded(it: ArrayList<Track>) {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvTracks.layoutManager = layoutManager
        binding.rvTracks.adapter = TrackAdapter(requireContext(), tunesViewModel.getTrackList(), ::trackItemClick)
        updateViewsForResults(tunesViewModel.getTrackListSize(),tunesViewModel.getAudioBookListSize())
    }

    private fun updateViewsForResults(sizeTrack: Int, sizeAudioBook: Int){
        if (sizeTrack == 0) {
            binding.textTracks.visibility = View.GONE
            binding.rvTracks.visibility = View.GONE
        } else {
            binding.textTracks.visibility = View.VISIBLE
            binding.rvTracks.visibility = View.VISIBLE
        }

        if (sizeAudioBook == 0) {
            binding.textAudioBook.visibility = View.GONE
            binding.rvAudioBook.visibility = View.GONE
        } else {
            binding.textAudioBook.visibility = View.VISIBLE
            binding.rvAudioBook.visibility = View.VISIBLE
        }
    }

    private fun trackItemClick(position: Int) {
        val action =
                SearchFragmentDirections.actionSearchFragmentToTrackDetailsFragment(tunesViewModel.getTrackByPosition(position).id)
        findNavController().navigate(action)
    }

    private fun audioBookItemClick(position: Int) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToTrackDetailsFragment(tunesViewModel.getAudioBookByPosition(position).id)
        findNavController().navigate(action)
    }

}