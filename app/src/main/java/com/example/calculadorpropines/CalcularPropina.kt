package com.example.calculadorpropines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadorpropines.ui.theme.CalculadorPropinesTheme

class CalcularPropina : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadorPropinesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Propina(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

/**
 * This function renders an activity including different types of objects
 * Allows to the user to calculate the amount of a dinner including the bartender tip
 * @author Raimon Izard
 * @since 12/10/2024
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Propina(modifier: Modifier = Modifier) {
    var import: Float by remember { mutableStateOf(0.0f) }
    var percentatge: Int by remember { mutableStateOf(0) }
    var preuFinal: Float by remember { mutableStateOf(0.0f) }
    var propinaCambrers: Float by remember { mutableStateOf(0.0f) }
    var stars: Int by remember { mutableStateOf(0) }
    var coins: Int by remember { mutableStateOf(0) }

    // Definim el contenidor del fons de la imatge
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.wooden_restaurant),
            contentDescription = "Background restaurant",
            modifier = Modifier
                .fillMaxSize()
                .matchParentSize()
        )
    }

    Box(
        modifier = Modifier
            .padding(top = 50.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 40.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            Text(
                "Benvingut/da al calculador de propines!",
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.7f))
                    .fillMaxWidth()
                    .padding(10.dp), color = Color.Black
            )

            TextField(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .padding(10.dp),
                value = import.toString(),
                onValueChange = { contingutTextField ->
                    import = contingutTextField.toFloatOrNull() ?: 0.0f
                    if (import < 0.0f) import = 0.0f
                    preuFinal = 0.0f
                },
                label = { Text("Introdueix l'import del sopar...") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextField(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .padding(10.dp),
                value = percentatge.toString(),
                onValueChange = { contingutTextField ->
                    percentatge = contingutTextField.toIntOrNull() ?: 0
                    if (percentatge > 100) percentatge = 100
                    else if (percentatge < 0) percentatge = 0
                    preuFinal = 0.0f
                },
                label = { Text("Introdueix el percentatge de propina que vols deixar [0 - 100%]") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Row(modifier = Modifier.padding(8.dp)) {

                Button(
                    onClick = { if (percentatge >= 5) percentatge -= 5 },
                    //modifier = Modifier.padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(/*contentColor = Color.Red*/ containerColor = Color.Red)
                ) { Text("-5%") }

                Button(
                    onClick = { if (percentatge <= 95) percentatge += 5 },
                    modifier = Modifier.padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(/*contentColor = Color.Green*/
                        containerColor = Color.Green
                    )
                ) { Text("+5%") }

                Row(modifier = Modifier.padding(10.dp)) {
                    when (percentatge) {
                        in 1..5 -> stars = 1
                        in 5..10 -> stars = 2
                        in 10..15 -> stars = 3
                        in 15..20 -> stars = 4
                        in 25..100 -> stars = 5
                        else -> stars = 0
                    }
                    for (i in 1..stars) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Icon Example",
                            tint = Color.Yellow
                        )
                    }
                }
            }

            Row {
                Button(
                    onClick = {
                        import = 0.0f
                        percentatge = 0
                        propinaCambrers = 0.0f
                        preuFinal = 0.0f
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    modifier = Modifier.padding(8.dp)
                ) { Text("Esborrar dades") }

                Button(
                    onClick = {
                        preuFinal = import * (1.0f + (percentatge.toFloat() / 100.0f))
                        propinaCambrers = import * (percentatge.toFloat() / 100.0f)
                    },
                    modifier = Modifier.padding(8.dp),
                    enabled = import > 0.0f && percentatge in 0..100, // Only enable the button if import > 0 :)
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) { Text("Calcular import") }
            }
            Column(
                modifier = Modifier
                    .padding(top = 40.dp)
            ) {
                Text(
                    //text = "El preu final a pagar és: ${preuFinal.toString()} €",
                    text = "La propina que rebrà el cambrer/a és de: ${
                        String.format(
                            "%.2f",
                            propinaCambrers
                        )
                    } €",
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.7f))
                        .fillMaxWidth()
                        .padding(10.dp), color = Color.Black
                )

                Text(
                    //text = "El preu final a pagar és: ${preuFinal.toString()} €",
                    text = "El preu final a pagar és: ${String.format("%.2f", preuFinal)} €",
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.7f))
                        .fillMaxWidth()
                        .padding(10.dp), color = Color.Black
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 0.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                when {
                    propinaCambrers in 0.5f..5.0f -> coins = 1
                    propinaCambrers in 5.0f..15.0f -> coins = 2
                    propinaCambrers in 15.0f..30.0f -> coins = 3
                    propinaCambrers in 30.0f..40.0f -> coins = 4
                    propinaCambrers > 40.0f -> coins = 5
                    else -> coins = 0
                }
                for (i in 1..coins) {
                    Image(contentScale = ContentScale.None,
                        painter = painterResource(id = R.drawable.euro_coin),
                        contentDescription = "euro coin",
                        alignment = Alignment.BottomCenter,
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = (coins - i) * 8f // Adjust
                            }
                    )
                }
            }
        }
    }
}