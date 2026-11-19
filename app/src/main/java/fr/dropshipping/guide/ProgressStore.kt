package fr.dropshipping.guide

import android.content.Context
import androidx.compose.runtime.mutableStateListOf

/** Sauvegarde locale des tâches cochées (SharedPreferences). */
class ProgressStore(context: Context) {
    private val prefs = context.getSharedPreferences("progress", Context.MODE_PRIVATE)
    private val done = mutableStateListOf<String>().apply {
        addAll(prefs.getStringSet(KEY, emptySet()) ?: emptySet())
    }

    fun isDone(id: String): Boolean = id in done

    fun toggle(id: String) {
        if (id in done) done.remove(id) else done.add(id)
        prefs.edit().putStringSet(KEY, done.toSet()).apply()
    }

    fun doneCount(step: Step): Int = step.tasks.count { it.id in done }

    fun totalDone(): Int = steps.sumOf { doneCount(it) }

    fun reset() {
        done.clear()
        prefs.edit().remove(KEY).apply()
    }

    private companion object {
        const val KEY = "done_tasks"
    }
}
