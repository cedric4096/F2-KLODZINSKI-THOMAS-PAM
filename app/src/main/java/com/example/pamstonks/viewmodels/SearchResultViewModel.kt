package com.example.pamstonks.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pamstonks.dataclasses.SearchResult

/**
 * SearchResult view model. Stores the search results in a LiveData.
 */
class SearchResultViewModel : ViewModel() {
    val currentResults: MutableLiveData<SearchResult> by lazy {
        MutableLiveData<SearchResult>()
    }
}