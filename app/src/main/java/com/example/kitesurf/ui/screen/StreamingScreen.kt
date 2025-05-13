package com.example.kitesurf.ui.screen

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import kotlin.text.trimIndent

@SuppressLint("SetJavaScriptEnabled") // Be cautious with enabling JavaScript
@Composable
fun YouTubeStreamingScreen(videoId: String) { // Pass the YouTube video ID
    val context = LocalContext.current

    // HTML template for the YouTube IFrame Player API
    val htmlTemplate = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <style>
                body { margin: 0; overflow: hidden; background-color: black; }
                #player { width: 100%; height: 100vh; } /* 100vh for full viewport height */
            </style>
        </head>
        <body>
            <div id="player"></div>
            <script>
                var tag = document.createElement('script');
                tag.src = "https://www.youtube.com/iframe_api";
                var firstScriptTag = document.getElementsByTagName('script')[0];
                firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

                var player;
                function onYouTubeIframeAPIReady() {
                    player = new YT.Player('player', {
                        videoId: '$videoId', // Insert the video ID here
                        events: {
                            'onReady': onPlayerReady,
                            'onError': onPlayerError
                        }
                    });
                }

                function onPlayerReady(event) {
                    event.target.playVideo();
                }

                function onPlayerError(event) {
                    // Handle errors (e.g., show an error message)
                    console.error('YouTube Player Error:', event.data);
                }
            </script>
        </body>
        </html>
    """.trimIndent()

    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient() // Optional: Handle link clicks within the WebView
                webChromeClient = WebChromeClient() // Required for full-screen video
                settings.javaScriptEnabled = true // Enable JavaScript to run the YouTube API
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true

                // Load the HTML content
                loadDataWithBaseURL("https://www.youtube.com", htmlTemplate, "text/html", "UTF-8", null)
            }
        },
        update = { webView ->
            // You can update the WebView if needed (e.g., change the video ID)
            // This would involve reloading the HTML or using JavaScript to control the player.
            // For a simple screen that just plays one video, the factory is sufficient.
        },
        modifier = Modifier.fillMaxSize()
    )
}