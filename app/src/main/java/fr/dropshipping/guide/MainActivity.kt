package fr.dropshipping.guide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val store = ProgressStore(applicationContext)
        setContent {
            DropTheme {
                App(store)
            }
        }
    }
}

@Composable
fun App(store: ProgressStore) {
    var tab by rememberSaveable { mutableIntStateOf(0) }
    var openStep by rememberSaveable { mutableIntStateOf(0) } // 0 = aucune étape ouverte

    BackHandler(enabled = openStep != 0) { openStep = 0 }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (openStep == 0) {
                NavigationBar {
                    NavigationBarItem(
                        selected = tab == 0,
                        onClick = { tab = 0 },
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text("Parcours") },
                    )
                    NavigationBarItem(
                        selected = tab == 1,
                        onClick = { tab = 1 },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                        label = { Text("Ressources") },
                    )
                    NavigationBarItem(
                        selected = tab == 2,
                        onClick = { tab = 2 },
                        icon = { Icon(Icons.Filled.Build, contentDescription = null) },
                        label = { Text("Calculateur") },
                    )
                }
            }
        },
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when {
                openStep != 0 -> StepScreen(
                    step = steps.first { it.id == openStep },
                    store = store,
                    onBack = { openStep = 0 },
                    onNext = { id -> openStep = id },
                )
                tab == 0 -> HomeScreen(store, onOpen = { openStep = it })
                tab == 1 -> ResourcesScreen()
                else -> CalculatorScreen()
            }
        }
    }
}

// ───────────────────────── Accueil / parcours ─────────────────────────

@Composable
fun HomeScreen(store: ProgressStore, onOpen: (Int) -> Unit) {
    var confirmReset by rememberSaveable { mutableStateOf(false) }
    val done = store.totalDone()
    val total = allTasksCount
    val progress = if (total == 0) 0f else done.toFloat() / total

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        "Lancez votre dropshipping en France",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "11 étapes, de l'idée à la première vente et au-delà. Cochez les tâches au fur et à mesure.",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                    )
                    Spacer(Modifier.height(16.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                        color = MaterialTheme.colorScheme.onPrimary,
                        trackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.25f),
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "$done / $total tâches • ${(progress * 100).toInt()} %",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }

        items(steps, key = { it.id }) { step ->
            StepCard(step, store.doneCount(step), onClick = { onOpen(step.id) })
        }

        item {
            Spacer(Modifier.height(4.dp))
            Text(
                DISCLAIMER,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            TextButton(onClick = { confirmReset = true }) { Text("Réinitialiser ma progression") }
        }
    }

    if (confirmReset) {
        AlertDialog(
            onDismissRequest = { confirmReset = false },
            title = { Text("Réinitialiser ?") },
            text = { Text("Toutes les tâches cochées seront décochées.") },
            confirmButton = {
                TextButton(onClick = { store.reset(); confirmReset = false }) { Text("Réinitialiser") }
            },
            dismissButton = {
                TextButton(onClick = { confirmReset = false }) { Text("Annuler") }
            },
        )
    }
}

@Composable
fun StepCard(step: Step, doneCount: Int, onClick: () -> Unit) {
    val total = step.tasks.size
    val finished = doneCount == total
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (finished) MaterialTheme.colorScheme.secondaryContainer
                        else MaterialTheme.colorScheme.primaryContainer,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(if (finished) "✅" else step.emoji, fontSize = 24.sp)
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    "Étape ${step.id}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(step.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    step.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { doneCount.toFloat() / total },
                    modifier = Modifier.fillMaxWidth().height(5.dp).clip(CircleShape),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            Spacer(Modifier.width(12.dp))
            Text(
                "$doneCount/$total",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ───────────────────────── Détail d'une étape ─────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepScreen(step: Step, store: ProgressStore, onBack: () -> Unit, onNext: (Int) -> Unit) {
    val next = steps.firstOrNull { it.id == step.id + 1 }
    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Étape ${step.id}", fontWeight = FontWeight.SemiBold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                }
            },
        )
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(step.emoji, fontSize = 40.sp)
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(step.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(
                        step.subtitle,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Text(step.intro, style = MaterialTheme.typography.bodyLarge)

            SectionTitle("💡 Conseils")
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    step.tips.forEach { tip ->
                        Row {
                            Text("•  ", color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Text(tip, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                }
            }

            SectionTitle("✅ À faire (${store.doneCount(step)}/${step.tasks.size})")
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            ) {
                Column(Modifier.padding(vertical = 4.dp)) {
                    step.tasks.forEach { task ->
                        val checked = store.isDone(task.id)
                        Row(
                            Modifier.fillMaxWidth().clickable { store.toggle(task.id) }.padding(horizontal = 8.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(checked = checked, onCheckedChange = { store.toggle(task.id) })
                            Text(
                                task.text,
                                modifier = Modifier.weight(1f),
                                color = if (checked) MaterialTheme.colorScheme.onSurfaceVariant
                                else MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            }

            SectionTitle("🔗 Sites utiles")
            step.links.forEach { LinkCard(it) }

            if (next != null) {
                Button(
                    onClick = { onNext(next.id) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text("Étape suivante : ${next.title}", maxLines = 1)
                }
            } else {
                Text(
                    "🎉 Dernière étape ! Revenez régulièrement sur le parcours pour optimiser votre boutique.",
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium,
                )
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
}

@Composable
fun LinkCard(link: Link) {
    val uriHandler = LocalUriHandler.current
    Card(
        onClick = { runCatching { uriHandler.openUri(link.url) } },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(link.title, fontWeight = FontWeight.SemiBold)
                Text(
                    link.desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(2.dp))
                Text(link.domain, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            }
            Text("↗", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
        }
    }
}

// ───────────────────────── Ressources ─────────────────────────

@Composable
fun ResourcesScreen() {
    var query by rememberSaveable { mutableStateOf("") }
    val q = query.trim().lowercase(Locale.ROOT)

    Column(Modifier.fillMaxSize()) {
        Text(
            "Tous les liens utiles",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        )
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Rechercher (Shopify, URSSAF, TikTok…)") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )
        LazyColumn(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            steps.forEach { step ->
                val matches = step.links.filter {
                    q.isEmpty() ||
                        it.title.lowercase(Locale.ROOT).contains(q) ||
                        it.desc.lowercase(Locale.ROOT).contains(q) ||
                        it.url.lowercase(Locale.ROOT).contains(q)
                }
                if (matches.isNotEmpty()) {
                    item(key = "h${step.id}") {
                        Text(
                            "${step.emoji}  ${step.title}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp),
                        )
                    }
                    items(matches, key = { "${step.id}-${it.url}" }) { LinkCard(it) }
                }
            }
        }
    }
}

// ───────────────────────── Calculateur de marge ─────────────────────────

private fun num(s: String): Double = s.replace(',', '.').toDoubleOrNull() ?: 0.0

private fun euro(v: Double): String = String.format(Locale.FRANCE, "%.2f €", v)

@Composable
fun CalculatorScreen() {
    var price by rememberSaveable { mutableStateOf("39.90") }
    var cost by rememberSaveable { mutableStateOf("12") }
    var shipping by rememberSaveable { mutableStateOf("3") }
    var ads by rememberSaveable { mutableStateOf("10") }
    var vat by rememberSaveable { mutableStateOf("0") }
    var fee by rememberSaveable { mutableStateOf("2.9") }
    var social by rememberSaveable { mutableStateOf("12.3") }

    val ttc = num(price)
    val vatRate = num(vat) / 100
    val ht = ttc / (1 + vatRate)
    val vatAmount = ttc - ht
    val payFees = ttc * num(fee) / 100 + if (ttc > 0) 0.25 else 0.0
    val grossBeforeAds = ht - num(cost) - num(shipping) - payFees
    val netBeforeTax = grossBeforeAds - num(ads)
    val socialCharges = ht * num(social) / 100
    val net = netBeforeTax - socialCharges
    val marginPct = if (ht > 0) net / ht * 100 else 0.0
    val roasBreakEven = if (grossBeforeAds - socialCharges > 0) ttc / (grossBeforeAds - socialCharges) else 0.0

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Calculateur de marge", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(
            "Estimez votre bénéfice réel par vente, avant de lancer un produit.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        NumField("Prix de vente TTC (€)", price) { price = it }
        NumField("Prix d'achat fournisseur (€)", cost) { cost = it }
        NumField("Frais de livraison fournisseur (€)", shipping) { shipping = it }
        NumField("Coût publicitaire par vente (€)", ads) { ads = it }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(Modifier.weight(1f)) { NumField("TVA (%)", vat) { vat = it } }
            Box(Modifier.weight(1f)) { NumField("Frais paiement (%)", fee) { fee = it } }
        }
        NumField("Cotisations sociales (% du CA)", social) { social = it }

        Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (net >= 0) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.errorContainer,
        ) {
            Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Bénéfice net par vente", style = MaterialTheme.typography.labelLarge)
                Text(euro(net), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text("Marge nette : ${String.format(Locale.FRANCE, "%.1f", marginPct)} %")
                Text(if (net >= 0) "✅ Rentable" else "⚠️ Vous perdez de l'argent sur chaque vente")
            }
        }

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                ResultRow("Prix HT", euro(ht))
                ResultRow("TVA collectée", euro(vatAmount))
                ResultRow("Frais de paiement", euro(payFees))
                ResultRow("Marge avant publicité", euro(grossBeforeAds))
                ResultRow("Cotisations estimées", euro(socialCharges))
                ResultRow(
                    "ROAS minimum pour être rentable",
                    if (roasBreakEven > 0) String.format(Locale.FRANCE, "%.2f", roasBreakEven) else "—",
                )
            }
        }

        Text(
            "Estimation indicative : la TVA à 0 % correspond à la franchise en base. Vérifiez vos taux réels sur service-public.fr.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun NumField(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun ResultRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
        Text(value, fontWeight = FontWeight.SemiBold)
    }
}
