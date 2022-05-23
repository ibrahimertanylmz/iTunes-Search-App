package com.finanskatilim.itunessearchapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finanskatilim.itunessearchapp.databinding.FragmentSearchBinding
import com.finanskatilim.itunessearchapp.model.LoadState
import com.finanskatilim.itunessearchapp.model.SearchResponse
import com.finanskatilim.itunessearchapp.service.iTunesApiService
import com.finanskatilim.itunessearchapp.ui.base.SearchViewModelFactory
import com.finanskatilim.itunessearchapp.ui.viewmodel.SearchViewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)

        initializeViewModel()
        initializeObservers()

        binding.button.setOnClickListener {
            val action =
                    SearchFragmentDirections
                            .actionSearchFragmentToDetailsFragment()
            findNavController().navigate(action)
        }



        return binding.root
    }

    private fun initializeViewModel() {
        val iTunesApiService = iTunesApiService()
        viewModelFactory = SearchViewModelFactory(iTunesApiService)
        searchViewModel =
            ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    private fun initializeObservers() {
        searchViewModel.onSearchQuery("song")
        searchViewModel.searchLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onLoadingStateChanged(it)
        })

    }

    private fun onLoadingStateChanged(it: LoadState?) {
        if (it == LoadState.Loaded) {
            searchViewModel.searchLiveData.observe(viewLifecycleOwner, Observer {
                onItemsLoaded(it)
            })
        } else {

        }
    }

    private fun onItemsLoaded(it: SearchResponse?) {
        it!!.results.forEach {
            it

            println(it)



            println(it.wrapperType)
        }
    }

}