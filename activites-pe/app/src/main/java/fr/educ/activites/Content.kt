package fr.educ.activites

import androidx.annotation.DrawableRes

enum class Category(
    val label: String,
    val emoji: String,
    @DrawableRes val photo: Int,
    val tagline: String,
) {
    EMOTIONS("Émotions", "💬", R.drawable.photo_emotions, "Mettre des mots sur ce qu'on ressent"),
    CALME("Apaisement", "🌿", R.drawable.photo_calme, "Respirer, se poser, retrouver le calme"),
    CREATIF("Créatif", "🎨", R.drawable.photo_creatif, "Créer, imaginer, s'exprimer"),
    COOPERATION("Coopération", "🤝", R.drawable.photo_cooperation, "Réussir ensemble"),
    SPORT("Mouvement", "⚽", R.drawable.photo_sport, "Bouger et se dépenser"),
    CUISINE("Cuisine", "🍳", R.drawable.photo_cuisine, "Préparer et partager un repas"),
    AUTONOMIE("Autonomie", "🔑", R.drawable.photo_autonomie, "Apprendre la vie quotidienne"),
    CONFIANCE("Estime de soi", "⭐", R.drawable.photo_confiance, "Grandir et croire en soi"),
    JEUX("Jeux", "🎲", R.drawable.photo_jeux, "Jouer, rire, se mesurer sans écraser"),
}

data class Activity(
    val id: Int,
    val title: String,
    val category: Category,
    val ageMin: Int,
    val ageMax: Int,
    val minutes: Int,
    val group: String,
    val goal: String,
    val materials: List<String>,
    val steps: List<String>,
    val vigilance: String,
) {
    val ages: String get() = "$ageMin–$ageMax ans"
}

data class AgeGroup(val label: String, val min: Int, val max: Int)

val ageGroups = listOf(
    AgeGroup("3-6 ans", 3, 6),
    AgeGroup("7-11 ans", 7, 11),
    AgeGroup("12-17 ans", 12, 17),
)

val principles: List<Pair<String, String>> = listOf(
    "Toujours proposer, jamais imposer" to
        "L'enfant peut refuser, observer ou arrêter à tout moment. Participer est un choix : c'est déjà une façon de lui rendre du pouvoir sur ce qui lui arrive.",
    "Prévisibilité et sécurité" to
        "Annoncez le déroulé, la durée et la fin de l'activité. Utilisez des rituels de début et de fin. L'imprévu peut réveiller de l'inquiétude chez un enfant qui a vécu des ruptures.",
    "Prudence avec les sujets sensibles" to
        "Évitez de demander de dessiner « sa famille », de raconter son histoire ou de parler de ses parents sans préparation. Attention aussi à la fête des mères/pères, aux anniversaires et aux vacances.",
    "Pas de compétition humiliante" to
        "Privilégiez la coopération, les jeux sans élimination et le plaisir partagé. Valorisez l'effort et la progression plutôt que le résultat.",
    "Confidentialité" to
        "Ce qu'un enfant produit ou confie reste dans le cadre. Ne l'exposez pas devant les autres sans son accord. Les révélations préoccupantes se transmettent selon les procédures de votre structure.",
    "Adapter à l'enfant, pas seulement à l'âge" to
        "Le développement et le vécu comptent plus que l'âge officiel. Gardez toujours une version plus simple et une version plus complexe en réserve.",
    "Prendre un temps pour terminer" to
        "Terminez par un court échange ou un geste de clôture : ce qui a plu, ce qu'on range ensemble. Restez disponible après l'activité.",
)

const val DISCLAIMER =
    "Ces idées sont des supports d'animation. Elles ne remplacent ni le projet pédagogique de votre structure, " +
        "ni l'avis de l'équipe pluridisciplinaire (psychologue, chef de service). Adaptez-les à chaque enfant."

val activities: List<Activity> = listOf(
    Activity(
        1, "La météo des émotions", Category.EMOTIONS, 4, 10, 15, "1 à 8 enfants",
        "Mettre des mots ou des images sur son état du moment, sans jugement.",
        listOf("Feuilles", "Crayons ou feutres", "Pictogrammes météo (soleil, nuage, pluie, orage)"),
        listOf(
            "Présentez les symboles météo : soleil, nuages, pluie, orage, arc-en-ciel.",
            "Chacun choisit ou dessine la météo qui ressemble à son état d'aujourd'hui.",
            "Ceux qui le souhaitent expliquent. On n'insiste jamais.",
            "Terminez en demandant ce qui pourrait aider la météo à changer (une collation, un câlin, du calme…).",
        ),
        "Accueillez toutes les météos de la même façon : l'orage n'est pas « mauvais ». Ne cherchez pas à interpréter les dessins.",
    ),
    Activity(
        2, "Le thermomètre de la colère", Category.EMOTIONS, 6, 12, 25, "1 à 6 enfants",
        "Repérer les signes de la colère et trouver ses propres stratégies pour redescendre.",
        listOf("Carton ou grande feuille", "Feutres rouge, orange, jaune, vert", "Ciseaux"),
        listOf(
            "Dessinez ensemble un grand thermomètre de bas en haut : calme, énervé, très en colère, explosion.",
            "Demandez : que sent ton corps à chaque niveau ? (cœur qui bat, poings serrés, chaleur…)",
            "Pour chaque niveau, cherchez ce qui aide : respirer, marcher, boire de l'eau, demander un câlin, aller dans un coin calme.",
            "Affichez le thermomètre dans la chambre ou dans la salle commune.",
        ),
        "Faites l'activité à froid, jamais pendant une crise. Ne la présentez pas comme une punition.",
    ),
    Activity(
        3, "La boîte à ressources apaisante", Category.CALME, 6, 17, 40, "1 à 8 enfants",
        "Créer un objet personnel qui aide à se calmer dans les moments difficiles.",
        listOf("Boîte en carton ou boîte à chaussures", "Peinture, collages, autocollants", "Objets doux ou sensoriels (balle antistress, tissu, photo choisie)", "Papier pour écrire des phrases"),
        listOf(
            "Chacun décore sa boîte comme il veut.",
            "Proposez de la remplir : un objet doux, une balle antistress, un coloriage, une musique, un mot encourageant.",
            "L'enfant décide de ce qu'il met et de qui a le droit de l'ouvrir.",
            "Convenez avec lui du moment où il peut aller la chercher.",
        ),
        "La boîte est personnelle et ne doit pas être confisquée. Évitez les parfums forts et les petits objets dangereux pour les plus jeunes.",
    ),
    Activity(
        4, "La respiration du ballon", Category.CALME, 4, 12, 10, "Groupe ou individuel",
        "Apprendre une technique simple pour se calmer par la respiration.",
        listOf("Un coussin ou un ballon (facultatif)"),
        listOf(
            "Allongez-vous ou asseyez-vous confortablement, une main sur le ventre.",
            "Imaginez un ballon dans le ventre : inspirez par le nez, le ballon gonfle.",
            "Soufflez doucement par la bouche, le ballon se dégonfle. Répétez 5 fois.",
            "Variante : posez un coussin sur le ventre et regardez-le monter et descendre.",
        ),
        "Certains enfants n'aiment pas fermer les yeux : laissez-les regarder autour. Ne forcez pas le silence.",
    ),
    Activity(
        5, "Le jeu des cinq sens (5-4-3-2-1)", Category.CALME, 6, 17, 10, "Groupe ou individuel",
        "Se recentrer sur le moment présent quand l'angoisse monte.",
        listOf("Aucun matériel"),
        listOf(
            "Demandez de nommer 5 choses que l'on voit.",
            "Puis 4 choses que l'on peut toucher, 3 que l'on entend, 2 que l'on sent, 1 que l'on goûte.",
            "Faites-le à voix haute ensemble, lentement.",
            "Proposez de le refaire seul quand l'enfant en a besoin.",
        ),
        "Pratiquez-le plusieurs fois au calme pour qu'il devienne un réflexe utilisable dans les moments difficiles.",
    ),
    Activity(
        6, "Mimes des émotions", Category.EMOTIONS, 4, 9, 20, "3 à 10 enfants",
        "Reconnaître les émotions sur le visage et dans le corps.",
        listOf("Cartes émotions (dessinées ou imprimées)"),
        listOf(
            "Chaque enfant tire une carte (joie, peur, colère, tristesse, surprise, dégoût).",
            "Il mime l'émotion sans parler, les autres devinent.",
            "On discute : dans quelle situation on peut ressentir cela ? Qu'est-ce qui nous aide ?",
            "Faites tourner les rôles, y compris pour l'adulte.",
        ),
        "Proposez d'abord des émotions agréables. Un enfant peut passer son tour. Ne mettez personne sous le regard du groupe contre son gré.",
    ),
    Activity(
        7, "La lettre jamais envoyée", Category.EMOTIONS, 12, 17, 30, "Individuel, en petit groupe",
        "Exprimer par écrit ce qu'on n'arrive pas à dire, sans pression.",
        listOf("Papier et stylo", "Enveloppes"),
        listOf(
            "Expliquez : on écrit une lettre à quelqu'un ou à quelque chose, que l'on n'enverra jamais.",
            "Chacun s'installe où il veut, au calme, pendant 15 minutes.",
            "À la fin, il garde sa lettre, la ferme ou la déchire. Personne ne la lit.",
            "Proposez un moment d'échange libre, uniquement pour ceux qui le souhaitent.",
        ),
        "Ne la proposez qu'à des adolescents prêts, avec un adulte disponible ensuite. Elle peut faire remonter des émotions fortes. N'exigez pas de lire.",
    ),
    Activity(
        8, "Improvisation théâtrale", Category.EMOTIONS, 10, 17, 45, "4 à 12 jeunes",
        "Exprimer ses émotions par le jeu et gagner en confiance.",
        listOf("Cartes de situations (au restaurant, dans le bus, une dispute…)", "Accessoires simples"),
        listOf(
            "Échauffez-vous avec des jeux d'expression corporelle (marcher comme un robot, un géant, un vieillard).",
            "Tirez une carte situation. Deux ou trois jeunes l'improvisent pendant 2 minutes.",
            "Le public applaudit, sans critique.",
            "Terminez par un tour de parole : ce que j'ai aimé, ce que j'ai ressenti en jouant.",
        ),
        "Pas de situations touchant à la famille ou à la violence vécue. Le rôle masqué aide les plus timides à s'exprimer.",
    ),
    Activity(
        9, "La fresque collective", Category.CREATIF, 5, 17, 60, "4 à 15 enfants",
        "Créer ensemble une œuvre qui valorise chacun.",
        listOf("Grand rouleau de papier ou drap blanc", "Peinture, gouache, pinceaux", "Tabliers ou vieux vêtements"),
        listOf(
            "Choisissez ensemble un thème positif : la forêt, l'espace, la ville idéale, la mer.",
            "Chaque enfant a sa zone et sa liberté, puis on relie les dessins.",
            "Ajoutez la signature ou l'empreinte de chacun.",
            "Affichez la fresque dans un lieu de vie et organisez un petit vernissage.",
        ),
        "Pas de comparaison entre les dessins. Valorisez chaque contribution et ne retouchez pas leur travail.",
    ),
    Activity(
        10, "Pâte à modeler maison", Category.CREATIF, 3, 8, 40, "2 à 8 enfants",
        "Se détendre par le toucher et la création.",
        listOf("2 tasses de farine", "1 tasse de sel", "2 cuillères d'huile", "Eau tiède, colorant alimentaire"),
        listOf(
            "Les enfants mesurent et mélangent eux-mêmes les ingrédients.",
            "Ajoutez de l'eau tiède peu à peu, pétrissez, puis colorez.",
            "Modelez librement : animaux, personnages, gâteaux.",
            "Rangez dans une boîte hermétique pour la prochaine fois.",
        ),
        "Vérifiez les allergies (gluten). Les très jeunes peuvent porter la pâte à la bouche : restez attentif.",
    ),
    Activity(
        11, "Mon blason", Category.CONFIANCE, 7, 14, 40, "1 à 8 enfants",
        "Se découvrir et se présenter avec ses forces, ses goûts et ses rêves.",
        listOf("Feuille avec un blason à 4 cases", "Crayons, magazines à découper"),
        listOf(
            "Présentez les 4 cases : ce que j'aime, ce que je sais bien faire, ce qui me rend fier, ce que je rêve de faire.",
            "Chacun remplit ses cases par un dessin, un collage ou des mots.",
            "Ceux qui le veulent présentent leur blason.",
            "Affichez-le dans leur chambre ou rangez-le dans leur classeur.",
        ),
        "N'imposez aucune case liée à la famille ou au passé. Aidez celui qui dit « je ne sais rien faire » en soulignant ses qualités observées.",
    ),
    Activity(
        12, "Le carnet des réussites", Category.CONFIANCE, 8, 17, 15, "Individuel, répété chaque semaine",
        "Prendre conscience de ses progrès et de ses fiertés.",
        listOf("Un petit carnet par enfant", "Stylos, autocollants"),
        listOf(
            "Chaque semaine, l'enfant note ou dessine une chose réussie : même petite.",
            "L'éducateur ajoute une observation positive.",
            "Relisez ensemble de temps en temps.",
            "Le carnet appartient à l'enfant, il le garde où il veut.",
        ),
        "Ne le liez ni aux récompenses ni aux sanctions. Il doit rester un espace de fierté.",
    ),
    Activity(
        13, "Le cercle des compliments", Category.CONFIANCE, 7, 17, 20, "4 à 10 enfants",
        "Recevoir et donner des mots positifs.",
        listOf("Un objet à se passer (balle, peluche)"),
        listOf(
            "Placez-vous en cercle. Celui qui reçoit l'objet reçoit un compliment des autres.",
            "Les compliments portent sur un comportement, un talent ou une qualité (« tu m'as aidé à ranger »).",
            "Chacun passe à son tour, y compris l'adulte.",
            "L'enfant remercie simplement.",
        ),
        "Certains enfants ont du mal à recevoir un compliment : laissez-les passer s'ils le veulent. Veillez à ce que tous en reçoivent.",
    ),
    Activity(
        14, "La chasse au trésor", Category.COOPERATION, 4, 12, 60, "4 à 15 enfants",
        "Coopérer, s'entraider et se repérer dans l'espace.",
        listOf("Indices écrits ou dessinés", "Un petit trésor (gâteaux, jeux collectifs)", "Cartes ou plans simples"),
        listOf(
            "Préparez 6 à 8 indices cachés dans le lieu ou le jardin.",
            "Formez des équipes mélangées avec un adulte.",
            "Chaque indice mène au suivant ; les équipes s'entraident si besoin.",
            "Le trésor est partagé entre tous.",
        ),
        "Pas de classement des équipes : tous gagnent ensemble. Adaptez la difficulté des indices aux plus jeunes.",
    ),
    Activity(
        15, "Le drap magique", Category.COOPERATION, 3, 8, 30, "5 à 12 enfants",
        "Jouer ensemble et partager un moment de plaisir collectif.",
        listOf("Un grand drap ou un parachute", "Balles légères ou ballons"),
        listOf(
            "Tenez le drap ensemble. Faites des vagues douces, puis fortes.",
            "Mettez des balles dessus et faites-les sauter sans les faire tomber.",
            "Les enfants passent dessous quand l'adulte annonce un prénom.",
            "Terminez par un câlin collectif ou une chanson.",
        ),
        "Certains enfants n'aiment pas être sous un drap : laissez-les rester sur le côté. Gardez des règles claires sur les contacts physiques.",
    ),
    Activity(
        16, "Le défi de la tour", Category.COOPERATION, 6, 17, 30, "Équipes de 3 à 5",
        "Planifier, communiquer et réussir ensemble.",
        listOf("Spaghettis crus", "Ruban adhésif", "Guimauves", "Ficelle"),
        listOf(
            "Chaque équipe reçoit le même matériel.",
            "Défi : construire la tour la plus haute qui tient debout, en 20 minutes.",
            "Les équipes se parlent et se répartissent les rôles.",
            "Chacun explique sa stratégie. Mangez les guimauves ensemble à la fin.",
        ),
        "Si la compétition crée des tensions, faites une seule équipe qui construit une grande tour.",
    ),
    Activity(
        17, "Parcours de motricité", Category.SPORT, 3, 7, 30, "3 à 10 enfants",
        "Dépenser son énergie, se repérer dans l'espace et gagner en confiance.",
        listOf("Coussins, cerceaux, chaises, cordes", "Tapis"),
        listOf(
            "Aménagez un parcours : ramper, sauter, marcher en équilibre, passer sous un obstacle.",
            "Faites-le une première fois avec chaque enfant.",
            "Ils le refont à leur rythme, sans chronomètre.",
            "Ajoutez de la musique et des consignes rigolotes (comme un animal).",
        ),
        "Sécurisez le parcours et le sol. Ne comparez pas les performances. Laissez chacun choisir les étapes.",
    ),
    Activity(
        18, "Jeux de ballon sans élimination", Category.SPORT, 6, 17, 45, "6 à 20",
        "Se défouler et coopérer sans exclure personne.",
        listOf("Ballons", "Plots ou cônes"),
        listOf(
            "Choisissez des jeux où personne n'est éliminé : passe à dix, balle au capitaine modifiée, ballon voyageur.",
            "Mélangez les équipes à chaque manche.",
            "Comptez les points pour les deux équipes ensemble.",
            "Terminez par des étirements et un temps d'eau.",
        ),
        "Veillez à ce que les plus timides ou les moins sportifs participent. Les gros contacts physiques peuvent déclencher des tensions : annoncez les règles.",
    ),
    Activity(
        19, "La balade photographique", Category.SPORT, 8, 17, 60, "2 à 8 jeunes",
        "Observer son environnement, s'exprimer et se sentir fier de ses créations.",
        listOf("Téléphone, appareil photo ou tablette", "Liste de défis (une couleur, un détail, une ombre…)"),
        listOf(
            "Donnez 5 défis photo : quelque chose de beau, d'insolite, de petit, de rouge…",
            "Chacun part seul ou en duo avec un adulte à proximité.",
            "De retour, chacun choisit sa photo préférée.",
            "Faites une mini-exposition sur un mur ou un écran.",
        ),
        "Pas de photos des personnes sans leur accord. Respectez le droit à l'image, particulièrement important en protection de l'enfance.",
    ),
    Activity(
        20, "Le mini-potager", Category.SPORT, 4, 17, 60, "2 à 10 enfants",
        "Prendre soin d'un être vivant, apprendre la patience et la responsabilité.",
        listOf("Pots ou jardinière", "Terre", "Graines faciles (radis, tournesol, persil)", "Arrosoir"),
        listOf(
            "Chacun remplit son pot de terre et plante ses graines.",
            "Décorez les pots avec son prénom.",
            "Créez un planning d'arrosage partagé.",
            "Récoltez et goûtez ensemble.",
        ),
        "Évitez les plantes toxiques. Un enfant qui voit mourir sa plante peut mal le vivre : prévoyez un plan B.",
    ),
    Activity(
        21, "L'atelier crêpes", Category.CUISINE, 4, 17, 60, "4 à 10 enfants",
        "Cuisiner ensemble, partager un goûter et se sentir fier de ce qu'on a préparé.",
        listOf("Farine, œufs, lait, sucre", "Saladier, fouet, poêle", "Garnitures"),
        listOf(
            "Les enfants pèsent et mélangent la pâte, avec de l'aide.",
            "Reposez la pâte pendant que l'on prépare la table.",
            "Les plus grands cuisent, les plus petits garnissent.",
            "Mangez ensemble autour d'une grande table.",
        ),
        "Vérifiez les allergies et les régimes. Surveillez la cuisson. La nourriture a parfois une histoire difficile : ne forcez jamais à goûter.",
    ),
    Activity(
        22, "Pizza maison", Category.CUISINE, 6, 17, 90, "4 à 12",
        "Planifier un repas, coopérer et partager un plaisir simple.",
        listOf("Pâte à pizza", "Sauce tomate, fromage", "Garnitures au choix"),
        listOf(
            "Choisissez ensemble les garnitures et faites une liste de courses.",
            "Préparez la pâte et étalez-la.",
            "Chacun compose sa pizza individuelle.",
            "Cuisez, puis mangez ensemble.",
        ),
        "Respectez les interdits alimentaires et les allergies. Laissez le choix, c'est important pour l'estime de soi.",
    ),
    Activity(
        23, "Top menu de la semaine", Category.AUTONOMIE, 10, 17, 90, "3 à 8 jeunes",
        "Apprendre à composer un repas équilibré, établir un budget et faire les courses.",
        listOf("Prospectus de supermarché", "Budget fictif ou réel", "Feuille et stylo"),
        listOf(
            "Fixez un budget par personne pour 3 repas.",
            "Les jeunes choisissent des recettes dans des prospectus ou des livres.",
            "Ils écrivent la liste de courses et comparent les prix.",
            "Si possible, faites les courses réellement et cuisinez un des repas.",
        ),
        "Les jeunes en difficulté budgétaire peuvent être gênés : restez sur un exercice, pas sur la situation personnelle.",
    ),
    Activity(
        24, "Mon argent de poche", Category.AUTONOMIE, 11, 17, 45, "3 à 10 jeunes",
        "S'initier à la gestion d'un budget.",
        listOf("Tableau de budget simple", "Faux billets ou feuille", "Étiquettes de prix"),
        listOf(
            "Donnez un montant mensuel fictif.",
            "Les jeunes répartissent : besoins, envies, épargne.",
            "Tirez une carte « imprévu » (cassé un téléphone, une invitation) et ajustez.",
            "Discutez des choix et des astuces pour épargner.",
        ),
        "Évitez les comparaisons entre les jeunes. Adaptez à ceux qui sont proches de la majorité (contrat jeune majeur, premier compte).",
    ),
    Activity(
        25, "Je gère mon linge", Category.AUTONOMIE, 8, 17, 40, "2 à 8 jeunes",
        "Acquérir les gestes de la vie quotidienne : trier, laver, étendre, plier.",
        listOf("Linge propre à trier", "Étiquettes d'entretien", "Panier, étendoir"),
        listOf(
            "Apprenez à lire les étiquettes d'entretien.",
            "Triez par couleur et température.",
            "Lancez une machine et étendez le linge.",
            "Défi : plier un tee-shirt en 10 secondes avec la bonne technique.",
        ),
        "Valorisez les compétences. Pour les plus âgés, rattachez-les au projet d'autonomie.",
    ),
    Activity(
        26, "Simulation d'entretien", Category.AUTONOMIE, 15, 17, 45, "2 à 6 jeunes",
        "Se préparer à un entretien de stage ou d'embauche.",
        listOf("Annonce fictive", "Liste de questions types", "Grille d'observation positive"),
        listOf(
            "Choisissez une annonce qui intéresse le jeune.",
            "Jouez l'entretien : l'éducateur ou un pair joue le recruteur.",
            "Donnez des retours positifs en premier, puis une piste d'amélioration.",
            "Rejouez la scène avec les améliorations.",
        ),
        "Préparez-le à l'avance pour les questions sur le parcours : une réponse courte et choisie, sans mentir ni se justifier.",
    ),
    Activity(
        27, "Yoga des animaux", Category.CALME, 4, 10, 20, "3 à 10 enfants",
        "Se détendre par le mouvement et la respiration.",
        listOf("Tapis ou couvertures", "Musique douce"),
        listOf(
            "Imitez des animaux : le chat, le chien, le papillon, la grenouille, la tortue.",
            "Associez chaque posture à une respiration.",
            "Finissez par un temps de repos allongé.",
            "Racontez une petite histoire pendant la relaxation.",
        ),
        "N'obligez personne à fermer les yeux ni à être touché. Évitez les postures qui exposent le corps de façon gênante.",
    ),
    Activity(
        28, "Mandalas à colorier", Category.CALME, 5, 17, 30, "Individuel ou petit groupe",
        "Se poser, se concentrer et se détendre en coloriant.",
        listOf("Mandalas imprimés", "Crayons de couleur, feutres", "Musique douce"),
        listOf(
            "Installez un coin calme avec de la musique.",
            "Chacun choisit son mandala et ses couleurs.",
            "On colorie en silence ou en discutant doucement.",
            "Exposez les mandalas ou laissez-les emporter.",
        ),
        "Proposez-le aux moments de tension ou de fatigue. Ne notez pas les coloriages.",
    ),
    Activity(
        29, "La boîte à soucis", Category.CALME, 3, 8, 15, "Individuel",
        "Déposer ses inquiétudes avant le coucher.",
        listOf("Une boîte avec une fente", "Papiers et crayons"),
        listOf(
            "Le soir, l'enfant dessine ou dit un souci à l'adulte, qui l'écrit.",
            "Il le glisse dans la boîte.",
            "L'adulte ouvre la boîte le lendemain avec lui pour trouver une solution si possible.",
            "Terminez par un rituel apaisant : une histoire ou une chanson.",
        ),
        "Respectez le secret de la boîte. Si les soucis révèlent un danger, suivez la procédure de votre structure.",
    ),
    Activity(
        30, "Le jeu de Kim sensoriel", Category.JEUX, 4, 9, 15, "3 à 10 enfants",
        "Développer l'attention et la mémoire en s'amusant.",
        listOf("Un plateau", "8 à 10 petits objets", "Un tissu"),
        listOf(
            "Montrez les objets pendant 30 secondes.",
            "Couvrez le plateau avec le tissu et retirez un objet.",
            "Les enfants devinent lequel a disparu.",
            "Variante : reconnaître les objets uniquement en les touchant.",
        ),
        "Pas de classement : on cherche ensemble. Évitez les objets qui peuvent effrayer.",
    ),
    Activity(
        31, "Qui suis-je ?", Category.JEUX, 6, 14, 20, "4 à 12 enfants",
        "Rire ensemble et travailler le langage et la déduction.",
        listOf("Post-it ou bandeaux", "Cartes personnages, animaux ou métiers"),
        listOf(
            "Chacun a un personnage collé sur le front sans le voir.",
            "Il pose des questions auxquelles on répond par oui ou non.",
            "Il découvre qui il est.",
            "Faites tourner jusqu'à ce que tous aient trouvé.",
        ),
        "Choisissez des personnages positifs et neutres. Évitez les moqueries.",
    ),
    Activity(
        32, "Loup-garou", Category.JEUX, 10, 17, 45, "8 à 20 jeunes",
        "Se divertir en groupe et développer l'écoute et l'argumentation.",
        listOf("Cartes de rôles (ou jeu du commerce)", "Un meneur de jeu"),
        listOf(
            "Distribuez les rôles en secret.",
            "La nuit, les loups choisissent une victime ; le jour, le village débat et vote.",
            "Le meneur raconte l'histoire pour entretenir l'ambiance.",
            "Jouez plusieurs manches courtes.",
        ),
        "Il y a un risque d'exclusion ou de moqueries : annoncez que « ce n'est qu'un jeu » et prévoyez que les éliminés restent actifs (commentateurs).",
    ),
    Activity(
        33, "Blind test et karaoké", Category.JEUX, 8, 17, 45, "5 à 20",
        "Partager du plaisir autour de la musique.",
        listOf("Enceinte, téléphone", "Paroles de chansons"),
        listOf(
            "Faites deviner des chansons par équipes mixtes.",
            "Alternez avec un moment karaoké où chacun chante s'il le souhaite.",
            "Chacun propose un titre favori pour la fin.",
            "Faites une playlist du groupe.",
        ),
        "Vérifiez les paroles et les clips des chansons proposées. La musique peut réveiller des souvenirs : restez attentif aux réactions.",
    ),
)

data class ProLink(val title: String, val desc: String, val url: String) {
    val domain: String
        get() = url.removePrefix("https://").removePrefix("http://").removePrefix("www.").substringBefore("/")
}

data class ProGroup(val title: String, val emoji: String, val links: List<ProLink>)

val proGroups: List<ProGroup> = listOf(
    ProGroup(
        "Aide et signalement", "🆘",
        listOf(
            ProLink("119 – Allô Enfance en danger", "Numéro national gratuit et confidentiel, 24 h/24, pour signaler ou demander conseil.", "https://allo119.gouv.fr/"),
            ProLink("Défenseur des droits", "Droits de l'enfant, saisine en cas de non-respect des droits.", "https://www.defenseurdesdroits.fr/"),
            ProLink("Ministère des Solidarités", "Politiques de protection de l'enfance et textes de référence.", "https://solidarites.gouv.fr/"),
            ProLink("Service-Public.fr", "Fiches pratiques officielles sur la protection de l'enfance.", "https://www.service-public.fr/"),
        ),
    ),
    ProGroup(
        "Réseaux et associations", "🤝",
        listOf(
            ProLink("CNAPE", "Fédération nationale des associations de protection de l'enfant.", "https://www.cnape.fr/"),
            ProLink("Apprentis d'Auteuil", "Fondation d'accueil, d'éducation et d'insertion des jeunes.", "https://www.apprentis-auteuil.org/"),
            ProLink("Enfance et Partage", "Prévention et lutte contre les maltraitances faites aux enfants.", "https://www.enfance-et-partage.org/"),
            ProLink("UNICEF France", "Droits de l'enfant, ressources et outils pédagogiques.", "https://www.unicef.fr/"),
        ),
    ),
    ProGroup(
        "Pédagogie et activités", "🎯",
        listOf(
            ProLink("CEMÉA", "Éducation nouvelle, formation et pratiques d'animation.", "https://www.cemea.asso.fr/"),
            ProLink("Les Francas", "Mouvement d'éducation populaire : activités et projets éducatifs.", "https://www.francas.asso.fr/"),
            ProLink("Les Petits Débrouillards", "Activités scientifiques et ludiques pour les enfants et jeunes.", "https://www.lespetitsdebrouillards.org/"),
            ProLink("EEDF", "Éclaireuses et éclaireurs de France : plein air et vie collective.", "https://www.eedf.fr/"),
            ProLink("UFOLEP", "Activités physiques et sportives pour tous, hors compétition.", "https://www.ufolep.org/"),
        ),
    ),
    ProGroup(
        "Références professionnelles", "📚",
        listOf(
            ProLink("Haute Autorité de santé", "Recommandations de bonnes pratiques sociales et médico-sociales.", "https://www.has-sante.fr/"),
            ProLink("Jeunes.gouv.fr", "Politiques de jeunesse, éducation populaire et engagement.", "https://www.jeunes.gouv.fr/"),
        ),
    ),
)
