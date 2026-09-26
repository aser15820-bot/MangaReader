package com.aser15820.mangareader

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aser15820.mangareader.data.repository.MangaRepository
import com.aser15820.mangareader.ui.screens.HomeScreen
import com.aser15820.mangareader.ui.screens.MangaDetailScreen
import com.aser15820.mangareader.ui.screens.ReaderScreen

@Composable
fun MangaReaderApp() {
    val navController: NavHostController = rememberNavController()
    val repository = MangaRepository()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                repository = repository,
                onOpenManga = { manga ->
                    navController.navigate("detail/${manga.id}")
                }
            )
        }

        composable(
            route = "detail/{mangaId}",
            arguments = listOf(navArgument("mangaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mangaId = backStackEntry.arguments?.getString("mangaId")
            val manga = repository.getMangaById(mangaId)

            if (manga != null) {
                MangaDetailScreen(
                    manga = manga,
                    onBack = { navController.popBackStack() },
                    onRead = { navController.navigate("reader/${manga.id}") }
                )
            }
        }

        composable(
            route = "reader/{mangaId}",
            arguments = listOf(navArgument("mangaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mangaId = backStackEntry.arguments?.getString("mangaId")
            val manga = repository.getMangaById(mangaId)

            if (manga != null) {
                ReaderScreen(
                    manga = manga,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
