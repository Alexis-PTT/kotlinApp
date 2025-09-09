package com.mainProject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.mainProject.Db.DataClassGraphic.SessionPlanData
import com.mainProject.ViewModel.ViewModelMain
import com.mainProject.navigate.NavMain

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val viewModelMain = ViewModelMain(application)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    MaterialTheme {
        NavMain(rememberNavController())
    }
}