package com.aser15820.mangareader.data.repository

import com.aser15820.mangareader.data.model.Chapter
import com.aser15820.mangareader.data.model.Manga

class MangaRepository {
    private val mangas = listOf(
        Manga(
            id = "solo-leveling",
            title = "Solo Leveling",
            author = "Chugong",
            description = "A weak hunter gains incredible power while facing monsters from the shadows.",
            thumbnailUrl = "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?auto=format&fit=crop&w=400&q=80",
            coverUrl = "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?auto=format&fit=crop&w=1200&q=80",
            status = "Ongoing",
            genres = listOf("Action", "Fantasy", "Adventure"),
            chapters = listOf(
                Chapter(1, "Awakening", listOf(
                    "https://images.unsplash.com/photo-1526379095098-d400fd0bf935?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1504384308090-c894fdcc538d?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=900&q=80"
                )),
                Chapter(2, "The Gate", listOf(
                    "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?auto=format&fit=crop&w=900&q=80"
                )),
                Chapter(3, "A Hidden Power", listOf(
                    "https://images.unsplash.com/photo-1493246507139-91e8fad9978e?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1524492412937-b28074a5d7da?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1470770841072-f978cf4d019e?auto=format&fit=crop&w=900&q=80"
                ))
            )
        ),
        Manga(
            id = "attack-on-titan",
            title = "Attack on Titan",
            author = "Hajime Isayama",
            description = "Humans fight for survival against towering monsters in a world filled with secrets.",
            thumbnailUrl = "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?auto=format&fit=crop&w=400&q=80",
            coverUrl = "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?auto=format&fit=crop&w=1200&q=80",
            status = "Completed",
            genres = listOf("Drama", "Action", "Thriller"),
            chapters = listOf(
                Chapter(1, "The Fall of Wall Maria", listOf(
                    "https://images.unsplash.com/photo-1529156069898-49953e39b3ac?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1493246507139-91e8fad9978e?auto=format&fit=crop&w=900&q=80"
                )),
                Chapter(2, "The Man Who Looks At You", listOf(
                    "https://images.unsplash.com/photo-1504384308090-c894fdcc538d?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=900&q=80"
                ))
            )
        ),
        Manga(
            id = "chainsaw-man",
            title = "Chainsaw Man",
            author = "Tatsuki Fujimoto",
            description = "A troubled young man becomes a devil hunter in a gritty, unpredictable world.",
            thumbnailUrl = "https://images.unsplash.com/photo-1524985069026-dd778a71c7b4?auto=format&fit=crop&w=400&q=80",
            coverUrl = "https://images.unsplash.com/photo-1524985069026-dd778a71c7b4?auto=format&fit=crop&w=1200&q=80",
            status = "Ongoing",
            genres = listOf("Dark Fantasy", "Action", "Comedy"),
            chapters = listOf(
                Chapter(1, "Public Safety", listOf(
                    "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=900&q=80"
                )),
                Chapter(2, "The Blood Fiend", listOf(
                    "https://images.unsplash.com/photo-1526379095098-d400fd0bf935?auto=format&fit=crop&w=900&q=80",
                    "https://images.unsplash.com/photo-1493246507139-91e8fad9978e?auto=format&fit=crop&w=900&q=80"
                ))
            )
        )
    )

    fun getMangas(): List<Manga> = mangas

    fun getMangaById(id: String?): Manga? = mangas.firstOrNull { it.id == id }
}
