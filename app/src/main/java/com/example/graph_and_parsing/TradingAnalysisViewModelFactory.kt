package com.example.graph_and_parsing


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.graph_and_parsing.domain.model.TradingDataEntry
import com.example.graph_and_parsing.presentation.trading_analysis.TradingAnalysisViewModel


class TradingAnalysisViewModelFactory(
    private val tradingData: List<TradingDataEntry>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TradingAnalysisViewModel::class.java)) {
            return TradingAnalysisViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}