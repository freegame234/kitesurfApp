package com.example.kitesurf.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.kitesurf.viewmodel.VideoViewModel


@Composable
fun VideoListScreen() {
    val viewModel: VideoViewModel = viewModel()
    val videos by viewModel.videos.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchVideos()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(videos) { video ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
                            context.startActivity(intent)
                        }
                        .padding(vertical = 8.dp)
                ) {
                    BasicText(text = "Description: ${video.description ?: "Aucune"}")
                    BasicText(text = "Date: ${video.date_upload ?: "Inconnue"}")
                }
            }
        }
    }
}

