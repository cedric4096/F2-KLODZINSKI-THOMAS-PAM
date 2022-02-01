package com.example.pamstonks.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pamstonks.dataclasses.Stock

class StockViewModel : ViewModel() {
    val currentStocks: MutableLiveData<MutableList<Stock>> by lazy {
        MutableLiveData<MutableList<Stock>>(mutableListOf())
    }
}