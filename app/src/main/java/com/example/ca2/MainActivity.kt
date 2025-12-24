package com.example.ca2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ca2.ui.theme.CA2Theme
import java.lang.Integer.parseInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CA2Theme {
                NavFun()
            }
        }
    }
}

@Composable
fun NavFun(){
    val nav = rememberNavController()
    NavHost(nav, "SCREEN_1"){
        composable ("SCREEN_1"){
            Screen1(nav)
        }

        composable("SCREEN_2/{name}/{quantity}") { backstack->
            val name = backstack.arguments?.getString("name") ?: ""
            val quan = backstack.arguments?.getString("quantity") ?: "0"
            Screen2(nav, name, quan.toInt())
        }
    }
}

@Composable
fun Screen1(navController: NavController){
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var quantity by remember {mutableStateOf(0)}
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    )
    {
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {Text("Name")}
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        TextField(
            value = quantity.toString(),
            onValueChange = {
                quantity = it.toInt()
            },
            label = {Text("Quantity")}
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Button(
            onClick = {
                if (quantity > 0){
                    navController.navigate("SCREEN_2/$name/$quantity")
                }else{
                    Toast.makeText(context, "Quantity should be greater than 0", Toast.LENGTH_SHORT).show()
                }
            }
        ) {Text("Submit") }
    }
}

@Composable
fun Screen2(navController: NavController, name: String, quantity: Int){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Item name: $name")
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Text("Quantity: $quantity")
    }
}
