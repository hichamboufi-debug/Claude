package fr.dropshipping.guide

data class Link(val title: String, val url: String, val desc: String)

data class Task(val id: String, val text: String)

data class Step(
    val id: Int,
    val emoji: String,
    val title: String,
    val subtitle: String,
    val intro: String,
    val tips: List<String>,
    val tasks: List<Task>,
    val links: List<Link>,
)

val Link.domain: String
    get() = url.removePrefix("https://").removePrefix("http://").removePrefix("www.").substringBefore("/")

const val DISCLAIMER =
    "Les informations de cette application sont données à titre pédagogique et ne remplacent pas " +
        "un conseil juridique, fiscal ou comptable. Les taux, seuils et règles évoluent : vérifiez " +
        "toujours sur les sites officiels (liens fournis) avant de vous lancer."

val steps: List<Step> = listOf(
    Step(
        id = 1,
        emoji = "🎓",
        title = "Comprendre le dropshipping",
        subtitle = "Le modèle, les marges, les risques",
        intro = "En dropshipping, vous vendez des produits en ligne sans les stocker : quand un client commande, " +
            "le fournisseur expédie directement le colis. Vous gérez la boutique, le marketing et le service client. " +
            "C'est facile à démarrer, mais les marges sont souvent faibles et la concurrence forte : " +
            "la réussite vient de la méthode, pas de la chance.",
        tips = listOf(
            "Comptez 300 à 1 500 € de budget de départ (boutique, publicité de test, outils).",
            "Visez une marge brute de 30 % minimum avant publicité.",
            "Méfiez-vous des formations « miracle » vendues très cher : tout ce qu'il faut est gratuit ou presque.",
        ),
        tasks = listOf(
            Task("1.1", "Comprendre le flux : client → ma boutique → fournisseur → colis chez le client"),
            Task("1.2", "Lister mes motivations, mon budget et le temps hebdomadaire disponible"),
            Task("1.3", "Définir un objectif chiffré pour les 3 premiers mois"),
        ),
        links = listOf(
            Link("Shopify – Guide du dropshipping", "https://www.shopify.com/fr/blog/dropshipping", "Guide gratuit pour débutants, en français"),
            Link("Google – Ressources e-commerce", "https://grow.google/intl/fr/", "Formations gratuites en marketing digital"),
            Link("Bpifrance Création", "https://bpifrance-creation.fr/", "Conseils et outils pour créer son entreprise"),
        ),
    ),
    Step(
        id = 2,
        emoji = "🏢",
        title = "Choisir son statut juridique",
        subtitle = "Micro-entreprise, SASU, EURL…",
        intro = "En France, vendre régulièrement en ligne demande une activité déclarée. Le plus simple pour débuter " +
            "est la micro-entreprise (ex auto-entrepreneur) : création gratuite en ligne, comptabilité allégée, " +
            "cotisations proportionnelles au chiffre d'affaires. Si vous prévoyez de gros volumes, une société " +
            "(SASU, EURL) peut être plus adaptée.",
        tips = listOf(
            "Activité à déclarer : « vente à distance / commerce de détail sur internet » (achat-revente).",
            "En micro-entreprise de vente de marchandises, les cotisations sociales sont d'environ 12 % du chiffre d'affaires (vérifiez le taux en vigueur).",
            "Les plafonds de chiffre d'affaires et le régime de TVA (franchise en base) changent : consultez service-public.fr.",
        ),
        tasks = listOf(
            Task("2.1", "Comparer micro-entreprise et société selon mon projet"),
            Task("2.2", "Créer mon entreprise sur le guichet officiel des formalités"),
            Task("2.3", "Récupérer mon numéro SIRET et mon accès URSSAF"),
            Task("2.4", "Vérifier mon régime de TVA (franchise en base ou assujetti)"),
        ),
        links = listOf(
            Link("Guichet des formalités d'entreprises", "https://formalites.entreprises.gouv.fr/", "Création officielle de votre entreprise"),
            Link("Service-Public Entreprendre", "https://entreprendre.service-public.fr/", "Fiches pratiques officielles : statuts, TVA, obligations"),
            Link("URSSAF – Auto-entrepreneur", "https://www.autoentrepreneur.urssaf.fr/", "Déclarer son chiffre d'affaires et payer ses cotisations"),
            Link("Impots.gouv – Professionnels", "https://www.impots.gouv.fr/professionnel", "Espace professionnel : TVA, impôts, CFE"),
        ),
    ),
    Step(
        id = 3,
        emoji = "🔍",
        title = "Trouver sa niche et ses produits",
        subtitle = "Étudier la demande et la concurrence",
        intro = "Une bonne niche répond à un besoin ou une passion précise (animaux, sport, maison, bébé…). " +
            "Cherchez des produits qui se démarquent, ni trop chers ni trop fragiles, avec une marge confortable " +
            "et peu de risque réglementaire. Validez toujours la demande avec des données réelles.",
        tips = listOf(
            "Évitez au départ : électronique, cosmétiques, jouets, produits de santé (normes CE, sécurité, réglementation stricte).",
            "Prix de vente idéal : 25 à 80 € pour absorber la publicité.",
            "Regardez ce que les concurrents vendent depuis longtemps : une pub active depuis des mois = produit rentable.",
        ),
        tasks = listOf(
            Task("3.1", "Choisir 3 niches candidates"),
            Task("3.2", "Valider la demande sur Google Trends"),
            Task("3.3", "Espionner la concurrence (pubs actives, sites concurrents)"),
            Task("3.4", "Sélectionner 5 à 10 produits à tester"),
            Task("3.5", "Vérifier que ces produits sont autorisés à la vente en France / UE (norme CE si besoin)"),
        ),
        links = listOf(
            Link("Google Trends", "https://trends.google.fr/", "Tendances de recherche en France"),
            Link("Meta Ad Library", "https://www.facebook.com/ads/library/", "Toutes les publicités actives de vos concurrents"),
            Link("TikTok Creative Center", "https://ads.tiktok.com/business/creativecenter/", "Pubs et produits tendance sur TikTok"),
            Link("Amazon – Meilleures ventes", "https://www.amazon.fr/gp/bestsellers", "Les produits qui se vendent le plus"),
            Link("Google Keyword Planner", "https://ads.google.com/home/tools/keyword-planner/", "Volumes de recherche par mot-clé"),
        ),
    ),
    Step(
        id = 4,
        emoji = "📦",
        title = "Trouver ses fournisseurs",
        subtitle = "Qualité, délais de livraison, fiabilité",
        intro = "Votre fournisseur fait votre réputation. Pour la France, privilégiez les fournisseurs avec " +
            "stock en Europe (livraison en 2 à 7 jours) plutôt qu'en Chine (10 à 30 jours), qui génèrent " +
            "beaucoup de retours et de litiges. Commandez toujours un échantillon avant de vendre.",
        tips = listOf(
            "Testez la qualité et le délai en commandant vous-même 1 à 2 produits.",
            "Demandez au fournisseur la facture, le packaging neutre (sans mention de sa marque) et la gestion des retours.",
            "Gardez 2 fournisseurs par produit pour sécuriser vos stocks.",
        ),
        tasks = listOf(
            Task("4.1", "Comparer au moins 3 fournisseurs pour mes produits"),
            Task("4.2", "Commander des échantillons et mesurer le délai réel"),
            Task("4.3", "Négocier les prix et vérifier les conditions de retour"),
            Task("4.4", "Calculer ma marge avec l'onglet Calculateur"),
        ),
        links = listOf(
            Link("BigBuy (stock en Europe)", "https://www.bigbuy.eu/", "Grossiste européen avec dropshipping, livraison rapide"),
            Link("CJ Dropshipping", "https://cjdropshipping.com/", "Fournisseur avec entrepôts en Europe et service d'achat"),
            Link("Spocket", "https://www.spocket.co/", "Fournisseurs européens et américains sélectionnés"),
            Link("AliExpress", "https://www.aliexpress.com/", "Immense catalogue, délais plus longs"),
            Link("Printful (impression à la demande)", "https://www.printful.com/fr", "Textile, mugs, posters personnalisés, production en Europe"),
            Link("Alibaba", "https://www.alibaba.com/", "Pour négocier directement avec les usines"),
        ),
    ),
    Step(
        id = 5,
        emoji = "🛍️",
        title = "Créer sa boutique en ligne",
        subtitle = "Plateforme, nom, design, pages produits",
        intro = "Choisissez une plateforme simple. Shopify est la plus utilisée pour le dropshipping (essai gratuit, " +
            "applications d'import produits). Soignez le nom, le logo, des photos de qualité et des descriptions " +
            "claires en français : une boutique crédible rassure et convertit mieux.",
        tips = listOf(
            "Vérifiez la disponibilité du nom de domaine et de la marque (INPI) avant de vous lancer.",
            "Une page produit = bonnes photos, bénéfices clairs, délais de livraison annoncés honnêtement, avis clients.",
            "Rédigez en français impeccable : évitez les traductions automatiques brutes.",
        ),
        tasks = listOf(
            Task("5.1", "Choisir le nom de la boutique et vérifier le nom de domaine"),
            Task("5.2", "Créer la boutique sur la plateforme choisie"),
            Task("5.3", "Importer et rédiger les fiches produits"),
            Task("5.4", "Créer le logo et les visuels"),
            Task("5.5", "Ajouter les pages : accueil, contact, livraison, retours, FAQ, à propos"),
            Task("5.6", "Passer une commande test de bout en bout"),
        ),
        links = listOf(
            Link("Shopify", "https://www.shopify.com/fr", "Plateforme n°1, essai gratuit"),
            Link("WooCommerce", "https://woocommerce.com/", "Solution gratuite sur WordPress"),
            Link("PrestaShop", "https://www.prestashop.com/fr", "Solution e-commerce française"),
            Link("Canva", "https://www.canva.com/", "Logos, visuels, bannières"),
            Link("INPI – Recherche de marque", "https://www.inpi.fr/", "Vérifier et protéger le nom de votre marque"),
            Link("OVHcloud – Noms de domaine", "https://www.ovhcloud.com/fr/domains/", "Réserver votre nom de domaine .fr"),
        ),
    ),
    Step(
        id = 6,
        emoji = "⚖️",
        title = "Respecter la loi française",
        subtitle = "CGV, mentions légales, RGPD, rétractation",
        intro = "Vendre à des consommateurs en France impose des obligations strictes. Un site non conforme " +
            "s'expose à des amendes. Les points essentiels : mentions légales, CGV, droit de rétractation de 14 jours, " +
            "politique de confidentialité (RGPD), affichage des prix TTC, informations de livraison claires.",
        tips = listOf(
            "Le vendeur reste responsable des produits vendus, même si le fournisseur est à l'étranger.",
            "Garantie légale de conformité : 2 ans sur les produits neufs. Vous devez gérer les retours et remboursements.",
            "Importation hors UE : informez-vous sur la TVA à l'importation et le guichet unique IOSS.",
            "Les avis clients doivent être authentiques : les faux avis sont interdits.",
        ),
        tasks = listOf(
            Task("6.1", "Rédiger les mentions légales (identité, SIRET, contact, hébergeur)"),
            Task("6.2", "Rédiger les CGV (conditions générales de vente)"),
            Task("6.3", "Ajouter la politique de retour et le droit de rétractation de 14 jours"),
            Task("6.4", "Mettre en place la politique de confidentialité et la bannière cookies (RGPD)"),
            Task("6.5", "Adhérer à un médiateur de la consommation (obligatoire) et l'indiquer sur le site"),
            Task("6.6", "Afficher prix TTC, délais et frais de livraison clairement"),
        ),
        links = listOf(
            Link("DGCCRF – Droits des consommateurs", "https://www.economie.gouv.fr/dgccrf", "Règles de la vente à distance"),
            Link("CNIL – RGPD pour les sites", "https://www.cnil.fr/fr/rgpd-par-ou-commencer", "Se mettre en conformité avec la protection des données"),
            Link("Médiation de la consommation", "https://www.economie.gouv.fr/mediation-conso", "Trouver un médiateur et comprendre l'obligation"),
            Link("Douane française", "https://www.douane.gouv.fr/", "Importations, droits de douane, produits interdits"),
            Link("Service-Public – Vente à distance", "https://entreprendre.service-public.fr/vosdroits/F31228", "Obligations du vendeur en ligne"),
        ),
    ),
    Step(
        id = 7,
        emoji = "💳",
        title = "Paiement et compte bancaire",
        subtitle = "Encaisser vos clients en sécurité",
        intro = "Ouvrez un compte bancaire dédié à votre activité (obligatoire en société, recommandé en micro-entreprise ; " +
            "obligatoire en micro-entreprise dès un certain chiffre d'affaires). Connectez ensuite une solution de paiement " +
            "à votre boutique : carte bancaire, PayPal, Apple Pay, paiement en plusieurs fois.",
        tips = listOf(
            "Proposez au moins carte bancaire + PayPal : ils couvrent la grande majorité des clients.",
            "Attention aux blocages : Stripe et PayPal peuvent geler des fonds si les délais de livraison sont trop longs.",
            "Séparez toujours l'argent de l'entreprise de votre compte personnel.",
        ),
        tasks = listOf(
            Task("7.1", "Ouvrir un compte bancaire professionnel"),
            Task("7.2", "Créer et vérifier mon compte Stripe (ou Shopify Payments)"),
            Task("7.3", "Créer et vérifier mon compte PayPal Business"),
            Task("7.4", "Tester un paiement réel puis le rembourser"),
        ),
        links = listOf(
            Link("Stripe", "https://stripe.com/fr", "Paiement par carte, Apple Pay, Google Pay"),
            Link("PayPal Business", "https://www.paypal.com/fr/business", "Paiement PayPal et en plusieurs fois"),
            Link("Qonto", "https://qonto.com/fr", "Compte pro 100 % en ligne"),
            Link("Shine", "https://www.shine.fr/", "Néobanque pour indépendants"),
            Link("Revolut Business", "https://www.revolut.com/fr-FR/business/", "Compte pro multi-devises"),
        ),
    ),
    Step(
        id = 8,
        emoji = "📣",
        title = "Marketing et publicité",
        subtitle = "Meta, TikTok, Google, e-mail",
        intro = "Sans trafic, pas de ventes. En dropshipping, la publicité payante est le principal levier : " +
            "TikTok et Instagram/Facebook pour les produits « coup de cœur », Google Shopping pour les produits " +
            "recherchés. Testez avec un petit budget, mesurez, puis augmentez ce qui marche.",
        tips = listOf(
            "Testez 3 à 5 vidéos ou visuels par produit avec 10 à 20 € par jour et par test.",
            "Installez le pixel/API de conversion avant de lancer la moindre publicité.",
            "Construisez une base e-mail dès le début : c'est votre canal le moins cher.",
            "Contenu organique (TikTok, Reels) : gratuit mais demande de la régularité.",
        ),
        tasks = listOf(
            Task("8.1", "Créer les comptes professionnels : Meta Business, TikTok Ads, Google Ads"),
            Task("8.2", "Installer les pixels de suivi sur la boutique"),
            Task("8.3", "Créer 3 vidéos / visuels publicitaires par produit"),
            Task("8.4", "Lancer les premières campagnes de test avec un budget limité"),
            Task("8.5", "Configurer Google Merchant Center pour Google Shopping"),
            Task("8.6", "Mettre en place l'e-mailing (bienvenue, panier abandonné)"),
        ),
        links = listOf(
            Link("Meta Business Suite", "https://business.facebook.com/", "Facebook et Instagram Ads"),
            Link("TikTok Ads", "https://ads.tiktok.com/", "Publicité sur TikTok"),
            Link("Google Ads", "https://ads.google.com/", "Search, Shopping, YouTube"),
            Link("Google Merchant Center", "https://merchants.google.com/", "Envoyer son catalogue à Google Shopping"),
            Link("Klaviyo", "https://www.klaviyo.com/", "E-mailing et automatisations"),
            Link("CapCut", "https://www.capcut.com/", "Montage vidéo simple pour pubs et TikTok"),
        ),
    ),
    Step(
        id = 9,
        emoji = "🚚",
        title = "Commandes et service client",
        subtitle = "Suivre les colis, rassurer, gérer les retours",
        intro = "Une fois les premières ventes arrivées, la gestion quotidienne commence : passer la commande " +
            "chez le fournisseur, envoyer le numéro de suivi au client, répondre vite, gérer les retours. " +
            "Un client bien traité laisse un avis positif et revient.",
        tips = listOf(
            "Répondez en moins de 24 h, idéalement en quelques heures.",
            "Prévenez proactivement en cas de retard : cela évite litiges et chargebacks.",
            "Automatisez : e-mails de confirmation et d'expédition, import des suivis.",
        ),
        tasks = listOf(
            Task("9.1", "Mettre en place un processus de traitement des commandes"),
            Task("9.2", "Configurer les e-mails automatiques (confirmation, expédition)"),
            Task("9.3", "Créer des modèles de réponses (retard, retour, remboursement)"),
            Task("9.4", "Mettre en place un outil de suivi de colis"),
            Task("9.5", "Collecter les avis clients après livraison"),
        ),
        links = listOf(
            Link("17TRACK", "https://www.17track.net/fr", "Suivi de colis multi-transporteurs"),
            Link("Parcel App", "https://parcelsapp.com/fr", "Suivre colis de toute provenance"),
            Link("Trustpilot", "https://fr.trustpilot.com/", "Collecter des avis clients vérifiés"),
            Link("Avis Vérifiés", "https://www.avis-verifies.com/", "Solution française d'avis certifiés"),
            Link("Gorgias", "https://www.gorgias.com/", "Service client e-commerce centralisé"),
        ),
    ),
    Step(
        id = 10,
        emoji = "🧾",
        title = "Comptabilité et impôts",
        subtitle = "Déclarer, payer, rester en règle",
        intro = "Même en micro-entreprise, vous devez déclarer votre chiffre d'affaires chaque mois ou chaque " +
            "trimestre à l'URSSAF, même s'il est nul, tenir un livre des recettes et conserver vos factures. " +
            "En société, un expert-comptable est fortement conseillé.",
        tips = listOf(
            "Mettez de côté 15 à 25 % de chaque vente pour les cotisations, impôts et imprévus.",
            "Gardez toutes les factures fournisseurs, publicités et logiciels.",
            "Pensez à la CFE (cotisation foncière des entreprises) : vous recevrez l'avis à partir de la 2e année.",
            "Ventes vers d'autres pays européens : renseignez-vous sur la TVA via le guichet unique OSS.",
        ),
        tasks = listOf(
            Task("10.1", "Ouvrir un livre des recettes (tableur ou logiciel)"),
            Task("10.2", "Programmer les déclarations URSSAF (mensuelles ou trimestrielles)"),
            Task("10.3", "Archiver factures et justificatifs (fournisseurs, pubs, logiciels)"),
            Task("10.4", "Provisionner une part du chiffre d'affaires pour les charges"),
            Task("10.5", "Rencontrer un expert-comptable si le volume ou la société l'exige"),
        ),
        links = listOf(
            Link("URSSAF – Déclarer son CA", "https://www.autoentrepreneur.urssaf.fr/", "Déclaration et paiement des cotisations"),
            Link("Impots.gouv – Espace pro", "https://www.impots.gouv.fr/professionnel", "TVA, CFE, impôt sur le revenu"),
            Link("Indy", "https://www.indy.fr/", "Comptabilité automatisée pour indépendants"),
            Link("Tiime", "https://www.tiime.fr/", "Comptabilité en ligne pour micro-entreprises"),
            Link("Ordre des experts-comptables", "https://www.experts-comptables.fr/", "Trouver un expert-comptable"),
        ),
    ),
    Step(
        id = 11,
        emoji = "📈",
        title = "Analyser et développer",
        subtitle = "Mesurer, optimiser, faire grandir",
        intro = "Pour progresser, suivez quelques indicateurs : taux de conversion, panier moyen, coût d'acquisition " +
            "(CPA), marge nette et retour sur dépenses publicitaires (ROAS). Doublez ce qui marche, arrêtez ce qui ne " +
            "marche pas, et construisez peu à peu une vraie marque plutôt qu'une boutique éphémère.",
        tips = listOf(
            "Taux de conversion moyen : 1 à 3 %. En dessous de 1 %, travaillez la page produit et la confiance.",
            "Augmentez le budget de 20 % maximum à la fois pour ne pas casser l'algorithme.",
            "Évoluez vers une marque : packaging, stock en France, contenu, communauté.",
        ),
        tasks = listOf(
            Task("11.1", "Installer Google Analytics et suivre les ventes"),
            Task("11.2", "Suivre chaque semaine : visiteurs, conversion, panier moyen, ROAS"),
            Task("11.3", "Améliorer les produits qui convertissent (upsell, packs)"),
            Task("11.4", "Tester de nouveaux produits et canaux chaque mois"),
            Task("11.5", "Envisager un stock en France ou une marque propre"),
        ),
        links = listOf(
            Link("Google Analytics", "https://analytics.google.com/", "Analyse du trafic et des conversions"),
            Link("Hotjar", "https://www.hotjar.com/", "Voir comment les visiteurs utilisent la boutique"),
            Link("Fédération du e-commerce (FEVAD)", "https://www.fevad.com/", "Études et chiffres du e-commerce français"),
            Link("Shopify Community", "https://community.shopify.com/", "Entraide entre marchands"),
        ),
    ),
)

val allTasksCount: Int get() = steps.sumOf { it.tasks.size }
