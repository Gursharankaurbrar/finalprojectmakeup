package com.example.finalprojectmakeup.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalprojectmakeup.Destinations.Destination



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC0CB)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFE4E1)
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to GlamGuide",
                    color = Color(0xFFE91E63),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Name", fontWeight = FontWeight.Bold, color = Color(0xFFE91E63)) },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color(0xFFE91E63),
                        focusedIndicatorColor = Color(0xFF8B0000),
                        unfocusedIndicatorColor = Color(0x80E91E63),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Email", fontWeight = FontWeight.Bold, color = Color(0xFFE91E63)) },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color(0xFFE91E63),
                        focusedIndicatorColor = Color(0xFF8B0000),
                        unfocusedIndicatorColor = Color(0x80E91E63),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Password", fontWeight = FontWeight.Bold, color = Color(0xFFE91E63)) },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color(0xFFE91E63),
                        focusedIndicatorColor = Color(0xFF8B0000),
                        unfocusedIndicatorColor = Color(0x80E91E63),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Confirm Password", fontWeight = FontWeight.Bold, color = Color(0xFFE91E63)) },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color(0xFFE91E63),
                        focusedIndicatorColor = Color(0xFF8B0000),
                        unfocusedIndicatorColor = Color(0x80E91E63),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        navController.navigate(Destination.Makeup.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login", color = Color.White)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        navController.navigate(Destination.Makeup.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B0000)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create Account", color = Color.White)
                }
            }
        }
    }
}
