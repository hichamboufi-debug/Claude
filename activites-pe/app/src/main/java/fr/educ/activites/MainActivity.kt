package fr.educ.activites

import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Icônes claires dans la barre d'état : chaque écran commence par une photo sombre.
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT))
        val store = FavoritesStore(applicationContext)
        setContent {
            AppTheme {
                App(store)
            }
        }
    }
}

@Composable
fun App(store: FavoritesStore) {
    var tab by rememberSaveable { mutableIntStateOf(0) }
    var openId by rememberSaveable { mutableIntStateOf(0) } // 0 = aucune activité ouverte

    BackHandler(enabled = openId != 0) { openId = 0 }

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        AnimatedContent(
            targetState = openId,
            transitionSpec = {
                if (targetState != 0 && initialState == 0) {
                    (slideInHorizontally { it / 5 } + fadeIn()) togetherWith fadeOut()
                } else if (targetState == 0) {
                    fadeIn() togetherWith (slideOutHorizontally { it / 5 } + fadeOut())
                } else {
                    fadeIn() togetherWith fadeOut()
                }
            },
            label = "screen",
        ) { id ->
            if (id != 0) {
                DetailScreen(
                    activity = activities.first { it.id == id },
                    store = store,
                    onBack = { openId = 0 },
                    onOpen = { openId = it },
                )
            } else {
                Crossfade(targetState = tab, label = "tab") { t ->
                    when (t) {
                        0 -> HomeScreen(store, onOpen = { openId = it })
                        1 -> FavoritesScreen(store, onOpen = { openId = it }, onExplore = { tab = 0 })
                        2 -> ProScreen()
                        else -> PrinciplesScreen()
                    }
                }
            }
        }

        if (openId == 0) {
            FloatingNavBar(
                selected = tab,
                onSelect = { tab = it },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}
