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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.finalprojectmakeup.Destinations.Destination
import com.example.finalprojectmakeup.R


@Composable
fun MakeupScreen(modifier: Modifier = Modifier, makeupManager: MakeupManager, navController: NavController){
    val makeups = makeupManager.makeupResponse.value

    if (makeups.isEmpty()) {
        Text("No makeup products available")
    } else {
        LazyColumn {
            items(makeups) { makeup ->
                MakeupCard(product = makeup, navController)
            }
        }
    }
}


@Composable
fun MakeupCard(product: MakeupDataItem, navController: NavController) {
    val imageUrl = if (!product.apiFeaturedImage.isNullOrEmpty()) {
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
        shape = RoundedCornerShape(12.dp),
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

                Button(
                    onClick = {
                        navController.navigate(Destination.Search.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("View Details", color = Color.White)
                }
            }
        }
    }
}