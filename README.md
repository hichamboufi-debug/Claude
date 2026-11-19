# Boutique dropshipping clé en main

Site e-commerce statique (HTML/CSS/JS, sans dépendance) + paiement Stripe via une fonction serverless Netlify.
Inclus : accueil, catalogue (recherche/filtre/tri), fiches produit, panier (barre livraison offerte), paiement Stripe Checkout,
pages légales françaises (CGV, mentions, retours, livraison, confidentialité), formulaires contact/newsletter, thème clair/sombre, responsive.

## Mise en ligne en 10 minutes
1. **Personnalisez** `js/config.js` (nom, email, société, seuil de livraison offerte).
2. **Produits** : éditez `data/products.json` (voir ci-dessous). Ajoutez `"image": "img/mon-produit.jpg"` pour une vraie photo (sinon un visuel emoji est affiché).
3. **Hébergez** : poussez ce dépôt sur GitHub, puis sur [netlify.com](https://netlify.com) → *Add new site → Import from Git*. Aucun build requis.
4. **Paiement** : créez un compte [Stripe](https://stripe.com), copiez la clé secrète (`sk_test_…` pour tester) dans Netlify → *Site settings → Environment variables* → `STRIPE_SECRET_KEY`. Redéployez. Passez en `sk_live_…` quand vous êtes prêt.
5. **Domaine** : Netlify → *Domain management*.
> Test sans Stripe : `demoCheckout: true` dans `js/config.js`.
> Si vous changez frais/seuil de livraison ou pays, mettez aussi à jour la constante `SHOP` en haut de `netlify/functions/checkout.js` (les prix sont toujours recontrôlés côté serveur).

## Fonctionnement du dropshipping
1. Choisissez un fournisseur : **CJ Dropshipping** (gratuit, entrepôts UE/FR), **Spocket**, **Zendrop**, **AliExpress via DSers**. Privilégiez les entrepôts UE pour des délais de 2-5 jours.
2. Ajoutez le produit dans `products.json` avec **prix de vente ≥ 2,5-3× le coût** (livraison, frais Stripe ~1,5 % + 0,25 €, pub, retours).
3. À chaque paiement, Stripe vous envoie un email : passez la commande chez le fournisseur avec l'adresse du client (Dashboard Stripe → Paiements). Puis envoyez le numéro de suivi au client.
4. Pour automatiser : connectez un webhook Stripe `checkout.session.completed` à Make/Zapier → API CJ/Spocket → email de suivi.

## À faire avant d'ouvrir (France/UE)
- Remplacez les textes légaux (`cgv.html`, `mentions-legales.html`) par une version validée ; statut d'entrepreneur (micro-entreprise) + SIRET ; TVA si applicable.
- Indiquez des délais **réalistes** (pas « 2-3 jours » si le colis vient d'Asie) et vérifiez la conformité CE des produits.
- Remplacez les 8 produits d'exemple par vos produits réels et vos propres photos/avis (n'affichez pas de faux avis ni de faux stock : c'est interdit).
- Ajoutez un bandeau cookies si vous installez Meta Pixel / Google Analytics.

## Structure
`index.html` · `boutique.html` · `produit.html?id=` · `panier.html` · `succes.html` · pages légales · `css/style.css` · `js/app.js` · `js/config.js` · `data/products.json` · `netlify/functions/checkout.js`

Aperçu local : `python3 -m http.server 8000` (le paiement nécessite Netlify ou `netlify dev`).
