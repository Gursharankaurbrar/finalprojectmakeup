package com.example.finalprojectmakeup.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalprojectmakeup.AuthViewModel
import com.example.finalprojectmakeup.Destinations.Destination

/**
 * Purpose - Login Screen - Displays a login form
 * where users can sign in with an email and password or navigate to the authentication screen
 *
 * @param navController: NavController - used for navigating between screens
 * @param authViewModel: AuthViewModel - handles user registration and authentication logic
 * @return Unit
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val signupSuccess = navController
        .previousBackStackEntry
        ?.savedStateHandle
        ?.get<Boolean>("signupSuccess") == true



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
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (signupSuccess) {
                    Text(
                        text = "Account created successfully! Please log in!",
                        color = Color(0xFF388E3C),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFC8E6C9))
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = "Welcome Back to GlamGuide",
                    color = Color(0xFF9C144D),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Email Field
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text("Email", fontWeight = FontWeight.Bold, color = Color(0xFF9C144D))
                    },
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

                // Password Field
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text("Password", fontWeight = FontWeight.Bold, color = Color(0xFF9C144D))
                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color(0xFFE91E63),
                        focusedIndicatorColor = Color(0xFF8B0000),
                        unfocusedIndicatorColor = Color(0x80E91E63),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))


                // Error Message
                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                // Login Button
                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            errorMessage = "Email and password cannot be empty!"
                        } else {
                            authViewModel.login(
                                email = email,
                                password = password,
                                onSuccess = { navController.navigate(Destination.Makeup.route) },
                                onError = { error -> errorMessage = error }
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD81B60)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Forgot password
                Text(
                    text = "Forgot Password?",
                    color = Color(0xFF8B0000),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {

                    }
                )

                Spacer(modifier = Modifier.height(12.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color(0x80E91E63)
                    )
                    Text(
                        text = "OR",
                        color = Color(0xFF9C144D),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color(0x80E91E63)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Navigation to Authentication Screen
                Button(
                    onClick = {
                        navController.navigate(Destination.Authentication.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B0000)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create New Account", color = Color.White)
                }

                Spacer(modifier = Modifier.height(12.dp))

                val context =  LocalContext.current

                // Second sign in method
                Button(
                    onClick = {
                        authViewModel.signInAnonymously(
                            onSuccess = {
                                navController.navigate(Destination.Makeup.route)
                            },
                            onError = { error ->

                                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continue as Guest", color = Color.White)
                }

            }
        }
    }
}