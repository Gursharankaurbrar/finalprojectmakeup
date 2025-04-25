package com.example.finalprojectmakeup.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.finalprojectmakeup.api.MakeupManager
import com.example.finalprojectmakeup.api.model.MakeupDataItem
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.finalprojectmakeup.AuthViewModel
import com.example.finalprojectmakeup.R
import com.example.finalprojectmakeup.api.db.AppDatabase
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.finalprojectmakeup.mvvm.MakeupViewModel


@Composable
fun MakeupScreen(modifier: Modifier = Modifier, makeupManager: MakeupManager, navController: NavController, db:AppDatabase, authViewModel: AuthViewModel, viewModel: MakeupViewModel){

    val makeups = makeupManager.makeupResponse.value


    if (makeups.isEmpty()) {
        Text("No makeup products available", color = Color.White)

    } else {
        LazyColumn {
            items(makeups) { makeup ->
                MakeupCard(product = makeup, navController, db, makeupManager, viewModel)
            }
        }
    }
}


@Composable
fun MakeupCard(product: MakeupDataItem, navController: NavController, db: AppDatabase, makeupManager: MakeupManager?, viewModel: MakeupViewModel) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Product") },
            text = { Text("Are you sure you want to delete this product?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteMakeup(product.id!!, db)
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    val imageUrl = if (!product.apiFeaturedImage.isNullOrEmpty()) {
        if (product.apiFeaturedImage!!.startsWith("//")) {
            "https:${product.apiFeaturedImage}"
        } else {
            product.apiFeaturedImage
        }
    } else {
        null
    }

    val iconState by viewModel.makeupIconState.collectAsState() // Observe state from ViewModel
    var isFavorite = iconState[product.id] ?: false // Get the latest state

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color(0xFF880E4F)
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8BBD0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (!imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .width(160.dp)
                        .height(160.dp)
                        .padding(end = 16.dp),
                    placeholder = painterResource(R.drawable.default_image),
                    error = painterResource(R.drawable.default_image)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.default_image),
                    contentDescription = "Default Image",
                    modifier = Modifier
                        .width(160.dp)
                        .height(160.dp)
                        .padding(end = 16.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Brand: ${product.brand.orEmpty()}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE91E63),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Category: ")
                        }
                        append(product.category.orEmpty())
                    },

                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                val priceWithSymbol = buildString {
                    product.priceSign?.let { append(it) }
                    append(product.price.orEmpty())
                }

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Price: ")
                        }
                        append(priceWithSymbol)
                    },
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Product Type: ")
                        }
                        append(product.productType.orEmpty())
                    },
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("makeupDetail/${product.id}")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                        ) {
                            Text("View Details", color = Color.White)
                        }

                        Spacer(modifier = Modifier.padding(5.dp))

                        // Delete Icon
                        IconButton(

                                onClick = { showDeleteDialog = true }

                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete",
                                tint = Color(0xFFE91E63)
                            )

                        }

                        IconButton(
                            onClick = {
                                viewModel.updateMakeupIcon(product.id!!, db)
                            }
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = if (isFavorite) "Unfavorite" else "Favorite",
                                tint = Color(0xFFE91E63)
                            )
                        }

                    }
                }

                }
        }
    }
}