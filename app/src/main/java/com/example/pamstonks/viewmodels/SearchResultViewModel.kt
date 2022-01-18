package com.example.pamstonks.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pamstonks.dataclasses.SearchResult
import com.example.pamstonks.dataclasses.Stock
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class SearchResultViewModel : ViewModel() {
    val currentResults: MutableLiveData<SearchResult> by lazy {
        MutableLiveData<SearchResult>()
    }
}