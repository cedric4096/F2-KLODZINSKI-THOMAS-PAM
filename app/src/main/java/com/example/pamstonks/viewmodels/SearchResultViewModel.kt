package com.example.pamstonks.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pamstonks.dataclasses.SearchResult

class SearchResultViewModel : ViewModel() {
    val currentResults: MutableLiveData<SearchResult> by lazy {
        MutableLiveData<SearchResult>()
    }
}