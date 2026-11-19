package fr.educ.activites

import android.content.Context
import androidx.compose.runtime.mutableStateListOf

/** Favoris sauvegardés localement. */
class FavoritesStore(context: Context) {
    private val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val ids = mutableStateListOf<Int>().apply {
        addAll((prefs.getStringSet(KEY, emptySet()) ?: emptySet()).mapNotNull { it.toIntOrNull() })
    }

    fun isFavorite(id: Int): Boolean = id in ids

    fun toggle(id: Int) {
        if (id in ids) ids.remove(id) else ids.add(id)
        prefs.edit().putStringSet(KEY, ids.map { it.toString() }.toSet()).apply()
    }

    private companion object {
        const val KEY = "favorite_ids"
    }
}
