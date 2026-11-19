package fr.educ.activites

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Casino
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.util.Locale

private val BottomBarSpace = 120.dp

@Composable
fun HomeScreen(store: FavoritesStore, onOpen: (Int) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    var categoryIndex by rememberSaveable { mutableIntStateOf(-1) } // -1 = toutes
    var ageIndex by rememberSaveable { mutableIntStateOf(-1) } // -1 = tous

    val q = query.trim().lowercase(Locale.ROOT)
    val category = Category.entries.getOrNull(categoryIndex)
    val age = ageGroups.getOrNull(ageIndex)
    val filtersActive = q.isNotEmpty() || category != null || age != null

    val filtered = activities.filter { a ->
        (category == null || a.category == category) &&
            (age == null || (a.ageMin <= age.max && a.ageMax >= age.min)) &&
            (q.isEmpty() ||
                a.title.lowercase(Locale.ROOT).contains(q) ||
                a.goal.lowercase(Locale.ROOT).contains(q) ||
                a.category.label.lowercase(Locale.ROOT).contains(q) ||
                a.materials.any { it.lowercase(Locale.ROOT).contains(q) })
    }
    val ideaOfTheDay = remember { activities[LocalDate.now().dayOfYear % activities.size] }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = BottomBarSpace),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item { Hero() }

        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Rechercher une activité, un matériel…") },
                leadingIcon = { Icon(Icons.Rounded.Search, null) },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) { Icon(Icons.Rounded.Close, "Effacer") }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            )
        }

        item {
            SectionHeader("Explorer par thème", Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(10.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(Category.entries.size) { i ->
                    val c = Category.entries[i]
                    ThemeTile(
                        c,
                        selected = categoryIndex == i,
                        onClick = { categoryIndex = if (categoryIndex == i) -1 else i },
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
                    AgeChip("Tous âges", ageIndex == -1) { ageIndex = -1 }
                }
                items(ageGroups.size) { i ->
                    AgeChip(ageGroups[i].label, ageIndex == i) { ageIndex = if (ageIndex == i) -1 else i }
                }
            }
        }

        if (!filtersActive) {
            item {
                IdeaOfTheDay(ideaOfTheDay, onClick = { onOpen(ideaOfTheDay.id) })
            }
        }

        item {
            Row(
                Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SectionHeader(
                    if (filtersActive) "${filtered.size} résultat(s)" else "Toutes les activités",
                    Modifier.weight(1f),
                )
                if (filtered.isNotEmpty()) {
                    TextButton(onClick = { onOpen(filtered.random().id) }) {
                        Icon(Icons.Rounded.Casino, null, Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Au hasard", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        if (filtered.isEmpty()) {
            item {
                Text(
                    "Aucune activité ne correspond à ces filtres. Essayez d'en retirer un.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                )
            }
        }

        items(filtered, key = { it.id }) { a ->
            ActivityCard(
                a,
                favorite = store.isFavorite(a.id),
                onToggleFavorite = { store.toggle(a.id) },
                onClick = { onOpen(a.id) },
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        item {
            Text(
                DISCLAIMER,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            )
        }
    }
}

@Composable
private fun Hero() {
    PhotoBox(
        photo = R.drawable.photo_hero,
        scrim = ScrimTopAndBottom,
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
            .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp)),
    ) {
        Column(
            Modifier.align(Alignment.BottomStart).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                "PROTECTION DE L'ENFANCE",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.5.sp,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.22f))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            )
            Text(
                "Des activités qui font du bien",
                color = Color.White,
                fontSize = 32.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            Text(
                "${activities.size} idées prêtes à animer, pensées pour les enfants et adolescents accompagnés.",
                color = Color.White.copy(alpha = 0.92f),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun ThemeTile(category: Category, selected: Boolean, onClick: () -> Unit) {
    val source = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(24.dp)
    PhotoBox(
        photo = category.photo,
        modifier = Modifier
            .width(138.dp)
            .height(176.dp)
            .pressScale(source)
            .clip(shape)
            .then(
                if (selected) Modifier.border(3.dp, MaterialTheme.colorScheme.secondary, shape) else Modifier,
            )
            .clickable(interactionSource = source, indication = null, onClick = onClick),
    ) {
        Text(
            category.emoji,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.85f))
                .padding(8.dp),
        )
        Column(Modifier.align(Alignment.BottomStart).padding(14.dp)) {
            Text(
                category.label,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                "${activities.count { it.category == category }} idées",
                color = Color.White.copy(alpha = 0.85f),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Composable
private fun AgeChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label, fontWeight = FontWeight.SemiBold) },
        shape = CircleShape,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = MaterialTheme.colorScheme.outline,
            selectedBorderColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Composable
private fun IdeaOfTheDay(a: Activity, onClick: () -> Unit) {
    val source = remember { MutableInteractionSource() }
    PhotoBox(
        photo = a.category.photo,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(210.dp)
            .pressScale(source, 0.98f)
            .clip(RoundedCornerShape(28.dp))
            .clickable(interactionSource = source, indication = null, onClick = onClick),
    ) {
        Column(Modifier.align(Alignment.BottomStart).padding(20.dp)) {
            Text(
                "✨ IDÉE DU JOUR",
                color = Color.White,
                fontSize = 11.sp,
                letterSpacing = 1.5.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 12.dp, vertical = 5.dp),
            )
            Spacer(Modifier.height(10.dp))
            Text(
                a.title,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
            )
            Text(
                "${a.ages} • ${a.minutes} min • ${a.category.label}",
                color = Color.White.copy(alpha = 0.9f),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

// ───────────────────────── Favoris ─────────────────────────

@Composable
fun FavoritesScreen(store: FavoritesStore, onOpen: (Int) -> Unit, onExplore: () -> Unit) {
    val favs = activities.filter { store.isFavorite(it.id) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = BottomBarSpace),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Box(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
                    .background(
                        androidx.compose.ui.graphics.Brush.linearGradient(
                            listOf(Color(0xFF0F766E), Color(0xFFE76F51)),
                        ),
                    )
                    .statusBarsPadding()
                    .padding(24.dp),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Mes favoris", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                    Text(
                        if (favs.isEmpty()) "Gardez ici vos activités préférées."
                        else "${favs.size} activité(s) enregistrée(s).",
                        color = Color.White.copy(alpha = 0.92f),
                    )
                }
            }
        }

        if (favs.isEmpty()) {
            item {
                Column(
                    Modifier.fillMaxWidth().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        Icons.Rounded.Favorite,
                        null,
                        tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                        modifier = Modifier.size(72.dp),
                    )
                    Text(
                        "Rien pour l'instant",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        "Touchez le cœur sur une activité pour la retrouver ici.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    TextButton(onClick = onExplore) { Text("Découvrir les activités", fontWeight = FontWeight.Bold) }
                }
            }
        }

        items(favs, key = { it.id }) { a ->
            ActivityCard(
                a,
                favorite = true,
                onToggleFavorite = { store.toggle(a.id) },
                onClick = { onOpen(a.id) },
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}
