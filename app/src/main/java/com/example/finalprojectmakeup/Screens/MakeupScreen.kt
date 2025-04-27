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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.example.finalprojectmakeup.mvvm.MakeupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Purpose - MakeupScreen - Displays a list of makeup products fetched from the API.
 * @param modifier: Modifier - Modifier to customize the layout of the composable (default is Modifier).
 * @param makeupManager: MakeupManager - Responsible for fetching makeup data from the API.
 * @param navController: NavController - Navigation controller to manage navigation between screens.
 * @param db: AppDatabase - Database instance for accessing and manipulating local data.
 * @param authViewModel: AuthViewModel - ViewModel to manage user authentication state.
 * @param viewModel: MakeupViewModel - ViewModel to manage makeup-related state and interactions.
 * @return Unit
 */
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

/**
 * Purpose - MakeupCard - Displays individual makeup product details in a card format.
 * @param product: MakeupDataItem - The makeup product data to display.
 * @param navController: NavController - Navigation controller for handling navigation.
 * @param db: AppDatabase - Database instance to interact with local data for editing or deleting the product.
 * @param makeupManager: MakeupManager? - An instance to refresh makeup data after modifying the product.
 * @param viewModel: MakeupViewModel - ViewModel to manage the state of the makeup product's favorite status.
 * @return Unit
 */
@Composable
fun MakeupCard(product: MakeupDataItem, navController: NavController, db: AppDatabase, makeupManager: MakeupManager?, viewModel: MakeupViewModel) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Product") },
            text = { Text("Are you sure you want to delete this product?") },
            confirmButton = {
                Button(
                    onClick = {

                        CoroutineScope(Dispatchers.IO).launch {
                            db.makeupDao().deleteMakeup(product.id!!)
                            makeupManager?.refreshMakeups()

                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD81B60))
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD81B60))
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showEditDialog) {
        var newPrice by remember { mutableStateOf(product.price ?: "") }
        var newCategory by remember { mutableStateOf(product.category ?: "") }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit Product") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newPrice,
                        onValueChange = { newPrice = it },
                        label = { Text("Price") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newCategory,
                        onValueChange = { newCategory = it },
                        label = { Text("Category") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPrice.isNotBlank() && newCategory.isNotBlank()) {
                            CoroutineScope(Dispatchers.IO).launch {
                                db.makeupDao().updateMakeup(product.id!!, newPrice, newCategory)
                                makeupManager?.refreshMakeups()
                            }
                            showEditDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD81B60))
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showEditDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD81B60))
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
                    color = Color(0xFF9C144D),
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
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD81B60)),
                        ) {
                            Text("Details", color = Color.White)
                        }


                        Spacer(modifier = Modifier.padding(2.dp))

                        // Edit Icon
                        IconButton(

                            onClick = { showEditDialog = true }

                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit",
                                tint = Color(0xFFE91E63)
                            )

                        }

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

                        Spacer(modifier = Modifier.padding(2.dp))



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