package com.mainProject.NotUsedForNow


import android.os.SystemClock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class Workout {

    @Preview
    @Composable
    fun chronoSport(){

        var totalTime1 by remember { mutableStateOf(0L)}
        var startTime1 by remember { mutableStateOf(0L)}
        var currentTime1 by remember { mutableStateOf(0L)}
        var boolChrono1 by remember { mutableStateOf(false)}

        var startTime2 by remember { mutableStateOf(0L)}
        var currentTime2 by remember { mutableStateOf(0L)}
        var boolChrono2 by remember { mutableStateOf(false) }


        LaunchedEffect(boolChrono1) {
            if (boolChrono1) {
                startTime1 = SystemClock.elapsedRealtime()
                while (boolChrono1) {
                    currentTime1 = SystemClock.elapsedRealtime()
                    delay(16L) // Roughly 60fps; UI update delay, not time tracking
                }
                totalTime1+= currentTime1 - startTime1
                currentTime1=0
                startTime1=0
            }
        }
        LaunchedEffect(boolChrono2) {
            if (boolChrono2) {
                startTime2 = SystemClock.elapsedRealtime()
                while (boolChrono2) {
                    currentTime2 = SystemClock.elapsedRealtime()
                    delay(16L) // Roughly 60fps; UI update delay, not time tracking
                }
            }
        }

        val timeInSecond1 = totalTime1 + currentTime1 - startTime1
        val seconds1 = (timeInSecond1 / 1000) % 60
        val minutes1 = (timeInSecond1 / (1000 * 60)) % 60
        val hours1 = (timeInSecond1 / (1000 * 3600)) % 60

        val timeInSecond2 = currentTime2 - startTime2
        val seconds2 = (timeInSecond2 / 1000) % 60
        val minutes2 = (timeInSecond2 / (1000 * 60)) % 60
        val hours2 = (timeInSecond2 / (1000 * 3600)) % 60

        Column (modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text( text = "${hours1/10}${hours1%10}:${minutes1/10}${minutes1%10}:${seconds1/10}${seconds1%10}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold)
            Text( text = "---------------------------------------")
            Text( text = "${hours2/10}${hours2%10}:${minutes2/10}${minutes2%10}:${seconds2/10}${seconds2%10}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold)


            Row{
                Button(onClick = { boolChrono1 = !boolChrono1 },
                    modifier = Modifier.padding(4.dp)) {
                    Text(if (boolChrono1) "stop1" else "go1")
                }
                Button(onClick = { boolChrono2 = !boolChrono2 },
                    modifier = Modifier.padding(4.dp)) {
                    Text(if (boolChrono2) "stop2" else "go2")
                }
            }
            Row{
                Button(onClick = { boolChrono1 = !boolChrono1 },
                    modifier = Modifier.padding(4.dp)) {
                    val status = if (boolChrono1) "stop" else "go"
                    Text(status)
                }
                Button(onClick = { boolChrono1 = !boolChrono1 },
                    modifier = Modifier.padding(4.dp)) {
                    val status = if (boolChrono1) "stop" else "go"
                    Text(status)
                }
            }


        }
    }
}