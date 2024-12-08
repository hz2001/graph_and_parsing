package com.example.graph_and_parsing

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.graph_and_parsing.ui.theme.Graph_and_parsingTheme

import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp

// Android tool
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.*

import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.graph_and_parsing.data.csv.TradingDataParser
import com.example.graph_and_parsing.domain.model.TradingDataEntry
//import com.example.graph_and_parsing.

import com.example.graph_and_parsing.presentation.trading_analysis.TradingAnalysisScreen
import com.example.graph_and_parsing.presentation.trading_analysis.TradingAnalysisViewModel


import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 示例数据集
        val tradingDataParser = TradingDataParser()
        val tradingDataList = mutableStateOf<List<TradingDataEntry>>(emptyList())
        val viewModel: TradingAnalysisViewModel = TradingAnalysisViewModel()
        CoroutineScope(Dispatchers.IO).launch {
            val inputStream = resources.openRawResource(R.raw.trading_data) // Load the file
            val parsedData = tradingDataParser.parse(inputStream) // Parse the file
            tradingDataList.value = parsedData
            viewModel.loadTradingData(tradingDataList.value)
            Log.d("testing", "onCreate tradingDataList.value: ${tradingDataList.value}")
        }

        setContent {

            // Pass ViewModel to Screen
            TradingAnalysisScreen(viewModel)
        }
    }
}



