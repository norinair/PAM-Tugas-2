package com.example.tugas2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tugas2.ui.theme.Tugas2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tugas2Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "calculator") {
                    composable("calculator") {
                        CalculatorScreen(navController)
                    }
                    composable("result/{result}") { backStackEntry ->
                        ResultScreen(
                            result = backStackEntry.arguments?.getString("result") ?: "",
                            name = "Norina Izza Ramadhanti",
                            nim = "225150400111039"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(navController: NavHostController) {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var selectedOperation by remember { mutableStateOf("+") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Input fields for numbers
        TextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Input Number 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Input Number 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Radio buttons for operations
        Row(verticalAlignment = Alignment.CenterVertically) {
            listOf("+", "-", "*", "/").forEach { operation ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (selectedOperation == operation),
                        onClick = { selectedOperation = operation }
                    )
                    Text(text = operation)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to calculate
        Button(
            onClick = {
                val result = calculateResult(num1.toDoubleOrNull(), num2.toDoubleOrNull(), selectedOperation)
                navController.navigate("result/$result")
            }
        ) {
            Text("Hitung")
        }
    }
}

@Composable
fun ResultScreen(result: String, name: String, nim: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Result: $result", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Nama: $name", style = MaterialTheme.typography.bodyMedium)
        Text(text = "NIM: $nim", style = MaterialTheme.typography.bodyMedium)
    }
}

fun calculateResult(num1: Double?, num2: Double?, operation: String): String {
    if (num1 == null || num2 == null) return "Invalid Input"
    return when (operation) {
        "+" -> (num1 + num2).toString()
        "-" -> (num1 - num2).toString()
        "*" -> (num1 * num2).toString()
        "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Cannot divide by zero"
        else -> "Unknown Operation"
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    Tugas2Theme {
        CalculatorScreen(rememberNavController())
    }
}