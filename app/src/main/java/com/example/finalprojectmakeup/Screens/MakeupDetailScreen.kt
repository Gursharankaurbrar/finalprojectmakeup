package com.example.finalprojectmakeup.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.finalprojectmakeup.api.model.MakeupDataItem


@Composable
fun MakeupDetailScreen(makeupDataItem: MakeupDataItem) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Cyan)
        ){
            Text(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center),
                text = "${makeupDataItem.description}"
            )
        }
}