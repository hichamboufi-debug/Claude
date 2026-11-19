package fr.educ.activites

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (openId == 0) {
                NavigationBar {
                    NavigationBarItem(
                        selected = tab == 0,
                        onClick = { tab = 0 },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                        label = { Text("Activités") },
                    )
                    NavigationBarItem(
                        selected = tab == 1,
                        onClick = { tab = 1 },
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text("Favoris") },
                    )
                    NavigationBarItem(
                        selected = tab == 2,
                        onClick = { tab = 2 },
                        icon = { Icon(Icons.Filled.Info, contentDescription = null) },
                        label = { Text("Repères") },
                    )
                }
            }
        },
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when {
                openId != 0 -> DetailScreen(
                    activity = activities.first { it.id == openId },
                    store = store,
                    onBack = { openId = 0 },
                )
                tab == 0 -> ListScreen(store, favoritesOnly = false, onOpen = { openId = it })
                tab == 1 -> ListScreen(store, favoritesOnly = true, onOpen = { openId = it })
                else -> PrinciplesScreen()
            }
        }
    }
}

// ───────────────────────── Liste + filtres ─────────────────────────

@Composable
fun ListScreen(store: FavoritesStore, favoritesOnly: Boolean, onOpen: (Int) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    var categoryIndex by rememberSaveable { mutableIntStateOf(-1) } // -1 = toutes
    var ageIndex by rememberSaveable { mutableIntStateOf(-1) } // -1 = tous

    val q = query.trim().lowercase(Locale.ROOT)
    val category = Category.entries.getOrNull(categoryIndex)
    val age = ageGroups.getOrNull(ageIndex)

    val filtered = activities.filter { a ->
        (!favoritesOnly || store.isFavorite(a.id)) &&
            (category == null || a.category == category) &&
            (age == null || (a.ageMin <= age.max && a.ageMax >= age.min)) &&
            (q.isEmpty() ||
                a.title.lowercase(Locale.ROOT).contains(q) ||
                a.goal.lowercase(Locale.ROOT).contains(q) ||
                a.materials.any { it.lowercase(Locale.ROOT).contains(q) })
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            Column(Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
                Text(
                    if (favoritesOnly) "Mes favoris" else "Idées d'activités",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    if (favoritesOnly) "Les activités que vous avez enregistrées."
                    else "Protection de l'enfance : ${activities.size} activités à animer avec les enfants et adolescents.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Rechercher (émotions, cuisine, ballon…)") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            )
        }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    FilterChip(
                        selected = ageIndex == -1,
                        onClick = { ageIndex = -1 },
                        label = { Text("Tous âges") },
                    )
                }
                items(ageGroups.size) { i ->
                    FilterChip(
                        selected = ageIndex == i,
                        onClick = { ageIndex = if (ageIndex == i) -1 else i },
                        label = { Text(ageGroups[i].label) },
                    )
                }
            }
        }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    FilterChip(
                        selected = categoryIndex == -1,
                        onClick = { categoryIndex = -1 },
                        label = { Text("Toutes") },
                    )
                }
                items(Category.entries.size) { i ->
                    val c = Category.entries[i]
                    FilterChip(
                        selected = categoryIndex == i,
                        onClick = { categoryIndex = if (categoryIndex == i) -1 else i },
                        label = { Text("${c.emoji} ${c.label}") },
                    )
                }
            }
        }
        if (filtered.isNotEmpty() && !favoritesOnly) {
            item {
                Button(
                    onClick = { onOpen(filtered.random().id) },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                ) {
                    Text("🎲  Une idée au hasard (${filtered.size})")
                }
            }
        }
        if (filtered.isEmpty()) {
            item {
                Text(
                    if (favoritesOnly && store.let { s -> activities.none { s.isFavorite(it.id) } })
                        "Aucun favori pour l'instant. Touchez le cœur dans une activité pour l'enregistrer."
                    else "Aucune activité ne correspond à ces filtres.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(24.dp),
                )
            }
        }
        items(filtered, key = { it.id }) { a ->
            ActivityCard(
                a,
                favorite = store.isFavorite(a.id),
                onClick = { onOpen(a.id) },
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
        item {
            Text(
                DISCLAIMER,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
    }
}

@Composable
fun ActivityCard(a: Activity, favorite: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(a.category.emoji, fontSize = 24.sp)
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(a.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    "${a.category.label} • ${a.ages} • ${a.minutes} min",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (favorite) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favori", tint = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

// ───────────────────────── Détail ─────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(activity: Activity, store: FavoritesStore, onBack: () -> Unit) {
    val context = LocalContext.current
    val favorite = store.isFavorite(activity.id)

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(activity.category.label, fontWeight = FontWeight.SemiBold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                }
            },
            actions = {
                IconButton(onClick = {
                    val send = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, shareText(activity))
                    }
                    context.startActivity(Intent.createChooser(send, "Partager l'activité"))
                }) {
                    Icon(Icons.Filled.Share, contentDescription = "Partager")
                }
                IconButton(onClick = { store.toggle(activity.id) }) {
                    Icon(
                        if (favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (favorite) "Retirer des favoris" else "Ajouter aux favoris",
                        tint = if (favorite) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface,
                    )
                }
            },
        )
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(activity.category.emoji, fontSize = 40.sp)
                Spacer(Modifier.width(12.dp))
                Text(
                    activity.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoPill("👶 ${activity.ages}", Modifier.weight(1f))
                InfoPill("⏱ ${activity.minutes} min", Modifier.weight(1f))
            }
            InfoPill("👥 ${activity.group}", Modifier.fillMaxWidth())

            Section("🎯 Objectif") {
                Text(activity.goal, style = MaterialTheme.typography.bodyLarge)
            }

            Section("🧰 Matériel") {
                activity.materials.forEach { Text("•  $it") }
            }

            Section("📝 Déroulé") {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    activity.steps.forEachIndexed { i, s ->
                        Row {
                            Box(
                                Modifier
                                    .size(26.dp)
                                    .clip(RoundedCornerShape(13.dp))
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text("${i + 1}", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                            }
                            Spacer(Modifier.width(10.dp))
                            Text(s, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        "⚠️ Points de vigilance",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                    Text(activity.vigilance, color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun InfoPill(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(text, color = MaterialTheme.colorScheme.onPrimaryContainer, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun Section(title: String, content: @Composable () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            content()
        }
    }
}

private fun shareText(a: Activity): String = buildString {
    appendLine("${a.category.emoji} ${a.title}")
    appendLine("${a.ages} • ${a.minutes} min • ${a.group}")
    appendLine()
    appendLine("Objectif : ${a.goal}")
    appendLine()
    appendLine("Matériel : ${a.materials.joinToString(", ")}")
    appendLine()
    a.steps.forEachIndexed { i, s -> appendLine("${i + 1}. $s") }
    appendLine()
    append("Vigilance : ${a.vigilance}")
}

// ───────────────────────── Repères ─────────────────────────

@Composable
fun PrinciplesScreen() {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            "Repères pour animer",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Text(
            "Des principes simples pour proposer des activités sécurisantes à des enfants qui ont connu des ruptures ou des traumatismes.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        principles.forEachIndexed { i, (title, text) ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        "${i + 1}. $title",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(text)
                }
            }
        }
        Text(
            DISCLAIMER,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
