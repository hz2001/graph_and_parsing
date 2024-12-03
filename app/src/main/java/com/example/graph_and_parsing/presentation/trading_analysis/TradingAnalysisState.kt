package com.example.graph_and_parsing.presentation.trading_analysis

import com.example.graph_and_parsing.domain.model.TradingDataEntry

data class TradingAnalysisState (
    var tradingData: List<TradingDataEntry>  = emptyList(),
    val showDailyVolumeTrend: Boolean = false,
    val showTransactionAmountDistribution: Boolean = false,
    val showUserActivePeriods: Boolean = false,
    val showUserCategoryPreferences: Boolean = false,
    val showMonthlyTransactionAnalysis: Boolean = false,
    val showProfitLossDistribution: Boolean = false
)

