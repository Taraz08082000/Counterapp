package com.example.myapplication

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibratorManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import android.os.Vibrator
import android.os.VibrationEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CounterScreen(
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CounterScreen(modifier: Modifier = Modifier) {
    var count by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val vibrator = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
          val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
          manager.defaultVibrator
        } else {
          @Suppress("DEPRECATION")
          context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val message = when {
            count > 12 -> "Сан: $count (Көп!)"
            count < 0 -> "Сан: $count (Теріс!)"
            else -> "Сан: $count"
        }
        Text(
            text = message,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                count++
               if (count==12) {
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   vibrator.vibrate(
                       VibrationEffect.createOneShot(
                           200,
                           VibrationEffect.DEFAULT_AMPLITUDE
                       )
                   )
               } else {
                   @Suppress("DEPRECATION")
                   vibrator.vibrate(200)
               }
             }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green
            )
        ) {
            Text("Қосу")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { count-- },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text("Азайту")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { count = 0 },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )
        ) {
            Text("Нөлге қайтару")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterPreview() {
    MyApplicationTheme {
        CounterScreen()
    }
}