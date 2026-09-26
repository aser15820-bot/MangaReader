package com.aser15820.mangareader.data.model

data class Chapter(
    val number: Int,
    val title: String,
    val pages: List<String>
)

data class Manga(
    val id: String,
    val title: String,
    val author: String,
    val description: String,
    val thumbnailUrl: String,
    val coverUrl: String,
    val status: String,
    val genres: List<String>,
    val chapters: List<Chapter>
)
