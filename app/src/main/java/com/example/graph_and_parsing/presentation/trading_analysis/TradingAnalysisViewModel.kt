package com.example.graph_and_parsing.presentation.trading_analysis


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graph_and_parsing.domain.model.TradeSide
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import com.example.graph_and_parsing.domain.model.TradingDataEntry



class TradingAnalysisViewModel() : ViewModel() {

    private val _state = MutableStateFlow(TradingAnalysisState())
    val state: StateFlow<TradingAnalysisState> = _state


    var dailyVolumeTrend = calculateDailyVolumeTrend()
    var transactionAmountDistribution: Map<TradeSide, Double> = calculateTransactionAmountDistribution()
    var userActivePeriods = calculateUserActivePeriods()
    var userCategoryPreferences = calculateUserCategoryPreferences()
    var monthlyTransactionAnalysis = calculateMonthlyTransactionAnalysis()
    var profitLossDistribution = calculateProfitLossDistribution()

    fun loadTradingData(tradingData: List<TradingDataEntry>) {
        viewModelScope.launch {
            Log.d("testing", "tradingData ${tradingData}")
            _state.emit(_state.value.copy(tradingData = tradingData))
            Log.d("testing", "_state.value.tradingData: ${_state.value.tradingData[0]}")
            dailyVolumeTrend = calculateDailyVolumeTrend()
            transactionAmountDistribution = calculateTransactionAmountDistribution()
            userActivePeriods = calculateUserActivePeriods()
            userCategoryPreferences = calculateUserCategoryPreferences()
            monthlyTransactionAnalysis = calculateMonthlyTransactionAnalysis()
            profitLossDistribution = calculateProfitLossDistribution()
        }
    }


    // Toggle chart visibility
    fun toggleChart(chart: (TradingAnalysisState) -> TradingAnalysisState) {
        viewModelScope.launch {
            _state.value = chart(_state.value)
        }
    }

    // Add methods for processing tradingData and returning chart data
    private fun calculateDailyVolumeTrend(): List<Pair<String, Double>> {
        Log.d("testing", "_state.value.tradingData: ${_state.value.tradingData}")
        val dailyVT = _state.value.tradingData.groupBy { it.createdAt.substring(0, 10) } // Group by date
            .map { (date, entries) ->
                date to entries.sumOf { it.filledAssetQuantity }
            }
        Log.d("testing", "calculateDailyVolumeTrend: ${dailyVT.size}")
        return dailyVT
    }

    private fun calculateTransactionAmountDistribution(): Map<TradeSide, Double> {
        return _state.value.tradingData.groupBy { it.side } // Group by TradeSide (BUY/SELL)
            .mapValues { (_, entries) ->
                entries.sumOf { entry ->
                    entry.averagePrice * entry.filledAssetQuantity
                }
            }
    }

    private fun calculateUserActivePeriods(): List<Pair<String, Double>> {
        return _state.value.tradingData.groupBy { it.createdAt.substring(11, 13) } // Group by hour
            .map { (hour, entries) -> hour to entries.size.toDouble() }
            .sortedBy { it.first }
    }

    private fun calculateUserCategoryPreferences(): Map<String, Double> {
        return _state.value.tradingData.groupBy { it.symbol }
            .mapValues { (_, entries) -> entries.size.toDouble()  }
    }

    private fun calculateMonthlyTransactionAnalysis(): Map<String, Double> {
        return _state.value.tradingData.groupBy { it.createdAt.substring(0, 7) } // Group by year-month
            .mapValues { (_, entries) -> entries.size.toDouble()  }
    }

    private fun calculateProfitLossDistribution(): Map<TradeSide, Double> {
        return _state.value.tradingData.groupBy { it.side }
            .mapValues { (side, entries) ->
                entries.sumOf { entry ->
                    val profitLoss = entry.averagePrice * entry.filledAssetQuantity
                    if (side == TradeSide.BUY) -profitLoss else profitLoss
                }
            }
    }
}