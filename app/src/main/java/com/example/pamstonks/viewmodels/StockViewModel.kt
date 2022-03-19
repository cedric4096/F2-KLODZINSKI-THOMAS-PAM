package com.example.pamstonks.viewmodels

import androidx.lifecycle.*
import com.example.pamstonks.repositories.StockRepository
import com.example.pamstonks.dataclasses.Stock
import kotlinx.coroutines.launch

class StockViewModel(private val repository: StockRepository) : ViewModel() {
    val allStocks: LiveData<List<Stock>> = repository.allStocks.asLiveData()

    fun insert(stock: Stock) = viewModelScope.launch {
        repository.insertAll(stock)
    }

    fun delete(stock: Stock) = viewModelScope.launch {
        repository.delete(stock)
    }
}

class StockViewModelFactory(private val repository: StockRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            return StockViewModel(repository) as T
        }
        throw IllegalArgumentException()
    }
}