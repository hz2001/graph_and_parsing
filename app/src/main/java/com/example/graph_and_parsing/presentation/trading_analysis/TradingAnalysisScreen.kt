package com.example.graph_and_parsing.presentation.trading_analysis

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.*
import ir.ehsannarmani.compose_charts.models.*


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush


@Composable
fun TradingAnalysisScreen(viewModel: TradingAnalysisViewModel) {
    val state = viewModel.state.collectAsState().value

    Column(modifier = Modifier.padding(16.dp)) {
        // Checkboxes
        CheckboxWithLabel(
            label = "每日交易量趋势",
            isChecked = state.showDailyVolumeTrend,
            onCheckedChange = { viewModel.toggleChart { it.copy(showDailyVolumeTrend = !it.showDailyVolumeTrend) }}
        )
        CheckboxWithLabel(
            label = "交易金额分布",
            isChecked = state.showTransactionAmountDistribution,
            onCheckedChange = { viewModel.toggleChart { it.copy(showTransactionAmountDistribution = !it.showTransactionAmountDistribution) } }
        )
        CheckboxWithLabel(
            label = "用户活跃时间段",
            isChecked = state.showUserActivePeriods,
            onCheckedChange = { viewModel.toggleChart { it.copy(showUserActivePeriods = !it.showUserActivePeriods) } }
        )
        CheckboxWithLabel(
            label = "用户交易品类偏好",
            isChecked = state.showUserCategoryPreferences,
            onCheckedChange = { viewModel.toggleChart { it.copy(showUserCategoryPreferences = !it.showUserCategoryPreferences) } }
        )
        CheckboxWithLabel(
            label = "用户月度交易行为分析",
            isChecked = state.showMonthlyTransactionAnalysis,
            onCheckedChange = { viewModel.toggleChart { it.copy(showMonthlyTransactionAnalysis = !it.showMonthlyTransactionAnalysis) } }
        )
        CheckboxWithLabel(
            label = "交易盈亏分布",
            isChecked = state.showProfitLossDistribution,
            onCheckedChange = { viewModel.toggleChart { it.copy(showProfitLossDistribution = !it.showProfitLossDistribution) } }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Charts
        if (state.showDailyVolumeTrend) {
            Log.d("testing", "TradingAnalysisScreen: ${viewModel.dailyVolumeTrend}")
            MyLineChart(title = "每日交易量趋势", data = viewModel.dailyVolumeTrend)
        }
        if (state.showTransactionAmountDistribution) {
            Text("交易金额分布")
//            Chart(title = "交易金额分布", data = viewModel.transactionAmountDistribution.entries.toList())
        }
        if (state.showUserActivePeriods) {
            Text("用户活跃时间段")
//            Chart(title = "用户活跃时间段", data = viewModel.userActivePeriods)
        }
        if (state.showUserCategoryPreferences) {
            Text("用户交易品类偏好")
//            Chart(title = "用户交易品类偏好", data = viewModel.userCategoryPreferences.entries.toList())
        }
        if (state.showMonthlyTransactionAnalysis) {
            Text("用户月度交易行为分析")
//            Chart(title = "用户月度交易行为分析", data = viewModel.monthlyTransactionAnalysis.entries.toList())
        }
        if (state.showProfitLossDistribution) {
            Text("交易盈亏分布")
//            Chart(title = "交易盈亏分布", data = viewModel.profitLossDistribution.entries.toList())
        }
    }
}

@Composable
fun CheckboxWithLabel(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked -> onCheckedChange(isChecked) }
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun MyLineChart(title: String, data: List<Pair<String, Double>>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
        // Placeholder for actual chart
        data.map { it.second }.maxOrNull()?.let {
            LineChart(
                data = remember {
                    listOf(
                        Line(
                            label = "Temperature",
    //                        values = listOf(28.0, 41.0, -5.0, 10.0, 35.0),
                            values = data.map { it.second },
                            color = SolidColor(Color.Red)
                    ),
                    )
                },
                zeroLineProperties = ZeroLineProperties(
                    enabled = true,
                    color = SolidColor(Color.Red),
                ),
                minValue = 0.0,
                maxValue = it + 100
            )
        }
    }
}


