package com.example.finalprojectmakeup.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.finalprojectmakeup.R
import com.example.finalprojectmakeup.api.db.AppDatabase
import com.example.finalprojectmakeup.api.model.MakeupDataItem
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import coil3.compose.AsyncImage

@Composable
fun MakeupDetailScreen(
    modifier: Modifier = Modifier,
    makeupDataItem: MakeupDataItem,
    db: AppDatabase,
    navController: NavController? = null
) {
    val context = LocalContext.current
    val imageUrl = if (!makeupDataItem.apiFeaturedImage.isNullOrEmpty()) {
        if (makeupDataItem.apiFeaturedImage!!.startsWith("//")) {
            "https:${makeupDataItem.apiFeaturedImage}"
        } else {
            makeupDataItem.apiFeaturedImage
        }
    } else {
        null
    }

    fun shareProduct() {
        val shareText = buildString {
            append("Check out this makeup product!\n\n")
            append("Name: ${makeupDataItem.name ?: "N/A"}\n")
            append("Description: ${makeupDataItem.description ?: "N/A"}\n")
            append("Brand: ${makeupDataItem.brand ?: "N/A"}\n")
            append("Price: ${makeupDataItem.priceSign ?: "$"}${makeupDataItem.price ?: "N/A"}\n")
            append("Type: ${makeupDataItem.productType ?: "N/A"}\n")
            if (!imageUrl.isNullOrEmpty()) {
                append("Image: $imageUrl\n")
            }
        }


        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        context.startActivity(Intent.createChooser(intent, "Share via"))
    }

    Scaffold(
        topBar = {
            if (navController != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color(0xFFF8BBD0))
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Color(0xFFE91E63)
                        )
                    }

                    Text(
                        text = "Product Details",
                        color = Color(0xFFE91E63),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    IconButton(
                        onClick = { shareProduct() },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color(0xFFE91E63)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(13.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = Color(0xFF880E4F)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF8BBD0)),
                contentAlignment = Alignment.Center
            ) {
                if (!imageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = makeupDataItem.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(R.drawable.default_image),
                        error = painterResource(R.drawable.default_image)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.default_image),
                        contentDescription = "Default Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(12.dp),
                        spotColor = Color(0xFF880E4F)
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8BBD0))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = makeupDataItem.name ?: "No name available",
                        color = Color(0xFFE91E63),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Brand: ")
                            }
                            append(makeupDataItem.brand.orEmpty())
                        },
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Category: ")
                            }
                            append(makeupDataItem.category.orEmpty())
                        },
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Type: ")
                            }
                            append(makeupDataItem.productType.orEmpty())
                        },
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    val priceWithSymbol = buildString {
                        makeupDataItem.priceSign?.let { append(it) }
                        append(makeupDataItem.price.orEmpty())
                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Price: ")
                            }
                            append(priceWithSymbol)
                        },
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Description:\n")
                            }
                            append(makeupDataItem.description ?: "No description available")
                        },
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {   },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE91E63)
                            ),
                            modifier = Modifier.weight(1f)
                                .padding(end = 8.dp)
                        ) {
                            Text("Add to Favorites", color = Color.White)
                        }

                        Button(
                            onClick = {  },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE91E63)
                            ),
                            modifier = Modifier.weight(1f)
                                .padding(start = 8.dp)
                        ) {
                            Text("Buy Now", color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}