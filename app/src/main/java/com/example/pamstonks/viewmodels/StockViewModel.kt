package com.example.pamstonks.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pamstonks.dataclasses.Stock

class StockViewModel : ViewModel() {
    val currentStock: MutableLiveData<Stock> by lazy {
        MutableLiveData<Stock>()
    }
}