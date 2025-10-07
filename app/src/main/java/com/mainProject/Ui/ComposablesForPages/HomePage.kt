package com.mainProject.Ui.ComposablesForPages

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.mainProject.ViewModel.ViewModelMain

class HomePage {


    @OptIn(UnstableApi::class)
    @SuppressLint("NewApi")
    @Composable
    fun homeGraphs( viewModel : ViewModelMain,){

        var selectedIndex by remember { mutableIntStateOf(0) }
        val options = listOf("Jours","Semaines", "Mois")
        val graph = GraphPileXIntYInt()

        LaunchedEffect(Unit) {}

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(8.dp)){

                item{
                    Text(text = "Statistiques")
                }
                item{
                    Column(modifier = Modifier.padding(16.dp)){
                        SingleChoiceSegmentedButtonRow {
                            options.forEachIndexed { index, label ->
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
                                        count = 3
                                    ),
                                    onClick = {
                                        selectedIndex = index
                                    },
                                    selected = index == selectedIndex,
                                    label = { Text(label) }
                                )
                            }
                        }
                        graph.SimpleGraphMain(options[selectedIndex],viewModel)
                    }
                }
            }
        }
    }
}