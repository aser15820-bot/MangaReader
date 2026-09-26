package com.aser15820.mangareader.data.repository

import com.aser15820.mangareader.data.model.Manga

// Simple repository with hardcoded sample data to make the app build and show a UI.
class MangaRepository {
    fun getMangas(): List<Manga> {
        return listOf(
            Manga(
                id = "1",
                title = "Sample Manga 1",
                thumbnailUrl = "https://via.placeholder.com/300x420.png?text=Manga+1",
                sourceUrl = "https://example.com/manga/1"
            ),
            Manga(
                id = "2",
                title = "Sample Manga 2",
                thumbnailUrl = "https://via.placeholder.com/300x420.png?text=Manga+2",
                sourceUrl = "https://example.com/manga/2"
            ),
            Manga(
                id = "3",
                title = "Sample Manga 3",
                thumbnailUrl = "https://via.placeholder.com/300x420.png?text=Manga+3",
                sourceUrl = "https://example.com/manga/3"
            ),
            Manga(
                id = "4",
                title = "Sample Manga 4",
                thumbnailUrl = "https://via.placeholder.com/300x420.png?text=Manga+4",
                sourceUrl = "https://example.com/manga/4"
            ),
            Manga(
                id = "5",
                title = "Sample Manga 5",
                thumbnailUrl = "https://via.placeholder.com/300x420.png?text=Manga+5",
                sourceUrl = "https://example.com/manga/5"
            ),
            Manga(
                id = "6",
                title = "Sample Manga 6",
                thumbnailUrl = "https://via.placeholder.com/300x420.png?text=Manga+6",
                sourceUrl = "https://example.com/manga/6"
            )
        )
    }
}
