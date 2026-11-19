package fr.educ.activites

import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ChildCare
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Handyman
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(activity: Activity, store: FavoritesStore, onBack: () -> Unit, onOpen: (Int) -> Unit) {
    val context = LocalContext.current
    val scroll = rememberScrollState()
    val doneSteps = remember(activity.id) { mutableStateListOf<Int>() }
    val similar = activities.filter { it.category == activity.category && it.id != activity.id }.take(4)

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().verticalScroll(scroll)) {
            // ── Photo d'en-tête avec effet de parallaxe ──
            Box(Modifier.fillMaxWidth().height(340.dp).clip(RoundedCornerShape(0.dp))) {
                PhotoBox(
                    photo = activity.category.photo,
                    scrim = ScrimTopAndBottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { translationY = scroll.value * 0.45f },
                )
                Column(
                    Modifier.align(Alignment.BottomStart).padding(start = 22.dp, end = 22.dp, bottom = 52.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        "${activity.category.emoji}  ${activity.category.label.uppercase()}",
                        color = Color.White,
                        fontSize = 11.sp,
                        letterSpacing = 1.5.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.22f))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                    )
                    Text(
                        activity.title,
                        color = Color.White,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }

            // ── Fiche qui recouvre la photo ──
            Column(
                Modifier
                    .offset(y = (-32).dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 18.dp, vertical = 22.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    InfoChip(Icons.Rounded.ChildCare, activity.ages)
                    InfoChip(Icons.Rounded.Schedule, "${activity.minutes} min")
                    InfoChip(Icons.Rounded.Groups, activity.group)
                }

                // Objectif
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    Row(Modifier.padding(18.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Rounded.Lightbulb, null, tint = Color.White, modifier = Modifier.size(26.dp))
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                "Objectif",
                                color = Color.White.copy(alpha = 0.85f),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                activity.goal,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }

                // Matériel
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.Handyman, null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(8.dp))
                        SectionHeader("Matériel")
                    }
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        activity.materials.forEach { m ->
                            Text(
                                m,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                            )
                        }
                    }
                }

                // Déroulé
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    SectionHeader("Déroulé")
                    Text(
                        "Touchez une étape pour la cocher pendant l'animation.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(6.dp))
                    activity.steps.forEachIndexed { i, step ->
                        StepRow(
                            number = i + 1,
                            text = step,
                            done = i in doneSteps,
                            isLast = i == activity.steps.lastIndex,
                            onClick = { if (i in doneSteps) doneSteps.remove(i) else doneSteps.add(i) },
                        )
                    }
                }

                // Vigilance
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                ) {
                    Row(Modifier.padding(18.dp), verticalAlignment = Alignment.Top) {
                        Icon(
                            Icons.Rounded.Shield,
                            null,
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.size(26.dp),
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                "Points de vigilance",
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                            )
                            Text(activity.vigilance, color = MaterialTheme.colorScheme.onTertiaryContainer)
                        }
                    }
                }

                // Activités similaires
                if (similar.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        SectionHeader("Dans le même thème")
                        LazyRow(
                            contentPadding = PaddingValues(end = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(similar, key = { it.id }) { s ->
                                SimilarCard(s, onClick = { onOpen(s.id) })
                            }
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
            }
        }

        // ── Boutons flottants ──
        Row(
            Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Retour")
            }
            Spacer(Modifier.weight(1f))
            RoundButton(onClick = {
                val send = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText(activity))
                }
                context.startActivity(Intent.createChooser(send, "Partager l'activité"))
            }) {
                Icon(Icons.Rounded.Share, contentDescription = "Partager")
            }
            Spacer(Modifier.width(10.dp))
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                shadowElevation = 4.dp,
            ) {
                HeartButton(store.isFavorite(activity.id), onClick = { store.toggle(activity.id) })
            }
        }
    }
}

@Composable
private fun RoundButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        shadowElevation = 4.dp,
        modifier = Modifier.size(44.dp).clip(CircleShape).clickable(onClick = onClick),
    ) {
        Box(contentAlignment = Alignment.Center) { content() }
    }
}

@Composable
private fun StepRow(number: Int, text: String, done: Boolean, isLast: Boolean, onClick: () -> Unit) {
    val circle by animateColorAsState(
        if (done) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
        label = "stepCircle",
    )
    val textColor by animateColorAsState(
        if (done) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onBackground,
        label = "stepText",
    )
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier.size(32.dp).clip(CircleShape).background(circle),
                contentAlignment = Alignment.Center,
            ) {
                if (done) {
                    Icon(Icons.Rounded.Check, null, tint = Color.White, modifier = Modifier.size(18.dp))
                } else {
                    Text("$number", color = Color.White, fontWeight = FontWeight.ExtraBold)
                }
            }
            if (!isLast) {
                Box(
                    Modifier
                        .width(2.dp)
                        .height(28.dp)
                        .background(MaterialTheme.colorScheme.outline),
                )
            }
        }
        Spacer(Modifier.width(14.dp))
        Text(
            text,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
            textDecoration = if (done) TextDecoration.LineThrough else TextDecoration.None,
            modifier = Modifier.weight(1f).padding(top = 4.dp, bottom = 8.dp),
        )
    }
}

@Composable
private fun SimilarCard(a: Activity, onClick: () -> Unit) {
    val source = remember { MutableInteractionSource() }
    PhotoBox(
        photo = a.category.photo,
        modifier = Modifier
            .width(210.dp)
            .height(130.dp)
            .pressScale(source)
            .clip(RoundedCornerShape(22.dp))
            .clickable(interactionSource = source, indication = null, onClick = onClick),
    ) {
        Column(Modifier.align(Alignment.BottomStart).padding(14.dp)) {
            Text(
                a.title,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
            )
            Text(
                "${a.ages} • ${a.minutes} min",
                color = Color.White.copy(alpha = 0.85f),
                style = MaterialTheme.typography.labelSmall,
            )
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
