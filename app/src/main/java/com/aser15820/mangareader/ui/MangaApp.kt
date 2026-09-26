package com.aser15820.mangareader

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.aser15820.mangareader.ui.theme.MangaReaderTheme
import com.aser15820.mangareader.ui.screens.HomeScreen

@Composable
fun MangaApp() {
    MangaReaderTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen()
        }
    }
}
