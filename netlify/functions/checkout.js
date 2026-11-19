// Crée une session Stripe Checkout. Les prix sont relus côté serveur (data/products.json)
// pour qu'un client ne puisse pas les modifier depuis son navigateur.
const fs = require("fs");
const path = require("path");

const SHOP = { currency: "eur", freeFrom: 49, fee: 4.9, countries: ["FR", "BE", "CH", "LU", "DE", "ES", "IT"] };

function loadProducts() {
  const roots = [process.env.LAMBDA_TASK_ROOT, process.cwd(), path.join(__dirname, "../..")].filter(Boolean);
  for (const r of roots) {
    const f = path.join(r, "data/products.json");
    if (fs.existsSync(f)) return JSON.parse(fs.readFileSync(f, "utf8"));
  }
  throw new Error("products.json introuvable");
}

exports.handler = async (event) => {
  const json = (code, body) => ({ statusCode: code, headers: { "Content-Type": "application/json" }, body: JSON.stringify(body) });
  if (event.httpMethod !== "POST") return json(405, { error: "Méthode non autorisée" });
  if (!process.env.STRIPE_SECRET_KEY) return json(500, { error: "STRIPE_SECRET_KEY manquante" });

  try {
    const { items } = JSON.parse(event.body || "{}");
    const products = loadProducts();
    const p = new URLSearchParams();
    let subtotal = 0, i = 0;

    for (const it of items || []) {
      const prod = products.find(x => x.id === it.id);
      const qty = Math.min(Math.max(parseInt(it.qty, 10) || 0, 0), 20);
      if (!prod || !qty) continue;
      const cents = Math.round(prod.price * 100);
      subtotal += cents * qty;
      p.append(`line_items[${i}][quantity]`, qty);
      p.append(`line_items[${i}][price_data][currency]`, SHOP.currency);
      p.append(`line_items[${i}][price_data][unit_amount]`, cents);
      p.append(`line_items[${i}][price_data][product_data][name]`, prod.name);
      p.append(`line_items[${i}][price_data][product_data][metadata][product_id]`, prod.id);
      i++;
    }
    if (!i) return json(400, { error: "Panier vide" });

    const shipping = subtotal >= SHOP.freeFrom * 100 ? 0 : Math.round(SHOP.fee * 100);
    p.append("shipping_options[0][shipping_rate_data][type]", "fixed_amount");
    p.append("shipping_options[0][shipping_rate_data][display_name]", shipping ? "Livraison standard" : "Livraison offerte");
    p.append("shipping_options[0][shipping_rate_data][fixed_amount][amount]", shipping);
    p.append("shipping_options[0][shipping_rate_data][fixed_amount][currency]", SHOP.currency);
    SHOP.countries.forEach((c, n) => p.append(`shipping_address_collection[allowed_countries][${n}]`, c));
    p.append("mode", "payment");
    p.append("phone_number_collection[enabled]", "true"); // utile pour le transporteur
    p.append("locale", "fr");
    const origin = event.headers.origin || process.env.URL;
    p.append("success_url", `${origin}/succes.html`);
    p.append("cancel_url", `${origin}/panier.html`);

    const r = await fetch("https://api.stripe.com/v1/checkout/sessions", {
      method: "POST",
      headers: { Authorization: `Bearer ${process.env.STRIPE_SECRET_KEY}`, "Content-Type": "application/x-www-form-urlencoded" },
      body: p
    });
    const d = await r.json();
    if (!r.ok) return json(502, { error: d.error?.message || "Erreur Stripe" });
    return json(200, { url: d.url });
  } catch (e) {
    return json(500, { error: e.message });
  }
};
