// ====== CONFIGURATION DE LA BOUTIQUE — modifiez ces valeurs ======
window.SHOP = {
  name: "Nordique",                       // nom de votre boutique
  tagline: "Des trouvailles malignes pour la maison, le bien-être et le quotidien",
  email: "contact@votre-boutique.fr",
  currency: "EUR",
  locale: "fr-FR",
  freeShippingFrom: 49,                   // livraison offerte dès X €
  shippingFee: 4.9,                       // sinon frais fixes
  deliveryDelay: "7 à 12 jours ouvrés",
  shippingCountries: ["FR", "BE", "CH", "LU", "DE", "ES", "IT"],
  // Fonction serverless qui crée la session de paiement Stripe (voir README)
  checkoutEndpoint: "/.netlify/functions/checkout",
  // Raccourci : mettez true pour simuler le paiement (démo, sans Stripe)
  demoCheckout: false,
  company: {                              // utilisé dans les pages légales
    legalName: "Votre Société SAS",
    address: "1 rue Exemple, 75000 Paris",
    siret: "000 000 000 00000",
    host: "Netlify, Inc. — 512 2nd Street, San Francisco, CA 94107, USA"
  }
};
