package com.example.finalprojectmakeup.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextAlign
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.finalprojectmakeup.R


@Composable
fun MakeupScreen(modifier: Modifier = Modifier, makeupManager: MakeupManager){
    val makeups = makeupManager.makeupResponse.value

    if (makeups.isEmpty()) {
        Text("No makeup products available")
    } else {
        LazyColumn {
            items(makeups) { makeup ->
                MakeupCard(product = makeup)
            }
        }
    }
}


@Composable
fun MakeupCard(product: MakeupDataItem) {
    val imageUrl = if (!product.apiFeaturedImage.isNullOrEmpty()) {
        // Prepend "https:" to the URL if it starts with "//"
        if (product.apiFeaturedImage!!.startsWith("//")) {
            "https:${product.apiFeaturedImage}"
        } else {
            product.apiFeaturedImage
        }
    } else {
        null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color(0xFF880E4F)
            ),
        shape = RoundedCornerShape(12.dp), // Rounded corners for card
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8BBD0)) // Darker pink background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Display product image or default image on the left
            if (!imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .width(160.dp) // Increased image width
                        .height(160.dp) // Increased image height
                        .padding(end = 16.dp), // Add padding to separate image and details
                    placeholder = painterResource(R.drawable.default_image), // Placeholder
                    error = painterResource(R.drawable.default_image) // Error image
                )
            } else {
                // Display default image if no URL is available
                Image(
                    painter = painterResource(R.drawable.default_image),
                    contentDescription = "Default Image",
                    modifier = Modifier
                        .width(160.dp) // Increased image width
                        .height(160.dp) // Increased image height
                        .padding(end = 16.dp) // Add padding to separate image and details
                )
            }

            // Display product details on the right
            Column(
                modifier = Modifier.weight(1f) // Take remaining space
            ) {
                // Display Brand
                Text(
                    text = "Brand: ${product.brand.orEmpty()}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE91E63), // Pink color for headings
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Display Category
                Text(
                    text = "Category: ${product.category.orEmpty()}",
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Display Price with currency symbol
                val priceWithSymbol = buildString {
                    product.priceSign?.let { append(it) } // Add currency symbol
                    append(product.price.orEmpty()) // Add price
                }
                Text(
                    text = "Price: $priceWithSymbol",
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Display Product Type
                Text(
                    text = "Product Type: ${product.productType.orEmpty()}",
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // View Details button
                Button(
                    onClick = {
                        // Navigate to detail screen
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)), // Pink button
                    modifier = Modifier.align(Alignment.End) // Align button to the right
                ) {
                    Text("View Details", color = Color.White)
                }
            }
        }
    }
}