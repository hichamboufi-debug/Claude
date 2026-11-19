(() => {
  const S = window.SHOP;
  const $ = (s, r = document) => r.querySelector(s);
  const fmt = n => new Intl.NumberFormat(S.locale, { style: "currency", currency: S.currency }).format(n);
  const esc = s => String(s).replace(/[&<>"']/g, c => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" }[c]));

  // ---------- Panier (localStorage) ----------
  const Cart = {
    get() { try { return JSON.parse(localStorage.getItem("cart") || "[]"); } catch { return []; } },
    set(c) { localStorage.setItem("cart", JSON.stringify(c)); updateBadge(); },
    add(id, qty = 1) {
      const c = this.get(); const l = c.find(x => x.id === id);
      l ? (l.qty += qty) : c.push({ id, qty }); this.set(c);
    },
    setQty(id, qty) {
      let c = this.get(); c = qty <= 0 ? c.filter(x => x.id !== id) : c.map(x => x.id === id ? { ...x, qty } : x); this.set(c);
    },
    clear() { this.set([]); },
    count() { return this.get().reduce((a, l) => a + l.qty, 0); }
  };
  function updateBadge() { document.querySelectorAll(".cart-count").forEach(e => { e.textContent = Cart.count(); e.hidden = !Cart.count(); }); }

  let PRODUCTS = [];
  const byId = id => PRODUCTS.find(p => p.id === id);
  const stars = r => "★".repeat(Math.round(r)) + "☆".repeat(5 - Math.round(r));

  function visual(p, big) {
    if (p.image) return `<img src="${esc(p.image)}" alt="${esc(p.name)}" loading="lazy">`;
    return `<div class="ph${big ? " big" : ""}" style="--c:${p.color}"><span>${p.emoji}</span></div>`;
  }

  function card(p) {
    const disc = p.oldPrice ? Math.round((1 - p.price / p.oldPrice) * 100) : 0;
    return `<article class="card">
      <a href="produit.html?id=${p.id}" class="card-img">${visual(p)}${p.badge ? `<span class="badge">${esc(p.badge)}</span>` : ""}</a>
      <div class="card-body">
        <div class="muted small">${esc(p.category)}</div>
        <h3><a href="produit.html?id=${p.id}">${esc(p.name)}</a></h3>
        <div class="rating small"><span class="stars">${stars(p.rating)}</span> ${p.rating} (${p.reviews})</div>
        <div class="price-row"><strong>${fmt(p.price)}</strong>${p.oldPrice ? `<s class="muted">${fmt(p.oldPrice)}</s><span class="off">-${disc}%</span>` : ""}</div>
        <button class="btn btn-block" data-add="${p.id}">Ajouter au panier</button>
      </div></article>`;
  }

  // ---------- Layout (header / footer) ----------
  function layout() {
    document.title = document.title ? `${document.title} — ${S.name}` : S.name;
    $("#site-header").innerHTML = `
      <div class="announce">🚚 Livraison offerte dès ${fmt(S.freeShippingFrom)} · ↩️ Retours sous 30 jours · 🔒 Paiement 100 % sécurisé</div>
      <div class="bar container">
        <a href="index.html" class="logo">${esc(S.name)}<i>.</i></a>
        <nav id="nav">
          <a href="index.html">Accueil</a><a href="boutique.html">Boutique</a>
          <a href="livraison.html">Livraison</a><a href="contact.html">Contact</a>
        </nav>
        <a href="panier.html" class="cart-link" aria-label="Panier">🛒<span class="cart-count" hidden>0</span></a>
        <button class="burger" aria-label="Menu" onclick="document.getElementById('nav').classList.toggle('open')">☰</button>
      </div>`;
    $("#site-footer").innerHTML = `
      <div class="container foot">
        <div><div class="logo">${esc(S.name)}<i>.</i></div><p class="muted">${esc(S.tagline)}</p></div>
        <div><h4>Aide</h4><a href="livraison.html">Livraison</a><a href="retours.html">Retours & remboursements</a><a href="contact.html">Contact</a></div>
        <div><h4>Légal</h4><a href="cgv.html">CGV</a><a href="mentions-legales.html">Mentions légales</a><a href="confidentialite.html">Confidentialité</a></div>
        <div><h4>Paiement</h4><p class="muted">💳 Visa · Mastercard · Apple Pay · Google Pay</p></div>
      </div>
      <div class="copy">© ${new Date().getFullYear()} ${esc(S.name)} — Tous droits réservés</div>`;
    document.querySelectorAll(".js-free").forEach(e => e.textContent = fmt(S.freeShippingFrom));
    document.querySelectorAll(".js-delay").forEach(e => e.textContent = S.deliveryDelay);
    document.querySelectorAll(".js-email").forEach(e => { e.textContent = S.email; e.href = "mailto:" + S.email; });
    document.querySelectorAll(".js-company").forEach(e => e.innerHTML = `${esc(S.company.legalName)}<br>${esc(S.company.address)}<br>SIRET : ${esc(S.company.siret)}`);
    document.querySelectorAll(".js-host").forEach(e => e.textContent = S.company.host);
    updateBadge();
  }

  function toast(msg) {
    const t = document.createElement("div"); t.className = "toast"; t.textContent = msg; document.body.appendChild(t);
    setTimeout(() => t.classList.add("show"), 10); setTimeout(() => { t.classList.remove("show"); setTimeout(() => t.remove(), 300); }, 2200);
  }

  document.addEventListener("click", e => {
    const b = e.target.closest("[data-add]"); if (!b) return;
    const qty = +(document.getElementById("qty")?.value || 1);
    Cart.add(b.dataset.add, b.dataset.qty ? +b.dataset.qty : (b.id === "add-main" ? qty : 1));
    toast("✅ Ajouté au panier");
  });

  // ---------- Pages ----------
  const pages = {
    home() {
      $("#featured").innerHTML = PRODUCTS.slice(0, 4).map(card).join("");
      const cats = [...new Set(PRODUCTS.map(p => p.category))];
      $("#cats").innerHTML = cats.map(c => `<a class="cat" href="boutique.html?cat=${encodeURIComponent(c)}"><span>${PRODUCTS.find(p => p.category === c).emoji}</span>${esc(c)}</a>`).join("");
    },
    shop() {
      const q = new URLSearchParams(location.search);
      const cats = ["Tous", ...new Set(PRODUCTS.map(p => p.category))];
      let cat = q.get("cat") || "Tous";
      $("#filters").innerHTML = cats.map(c => `<button class="chip${c === cat ? " on" : ""}" data-cat="${esc(c)}">${esc(c)}</button>`).join("");
      const render = () => {
        const term = $("#search").value.toLowerCase(), sort = $("#sort").value;
        let list = PRODUCTS.filter(p => (cat === "Tous" || p.category === cat) && (p.name + p.short).toLowerCase().includes(term));
        if (sort === "asc") list.sort((a, b) => a.price - b.price);
        if (sort === "desc") list.sort((a, b) => b.price - a.price);
        if (sort === "rating") list.sort((a, b) => b.rating - a.rating);
        $("#grid").innerHTML = list.length ? list.map(card).join("") : `<p class="muted">Aucun produit trouvé.</p>`;
      };
      $("#filters").addEventListener("click", e => { const b = e.target.closest("[data-cat]"); if (!b) return; cat = b.dataset.cat; document.querySelectorAll(".chip").forEach(x => x.classList.toggle("on", x === b)); render(); });
      $("#search").addEventListener("input", render); $("#sort").addEventListener("change", render); render();
    },
    product() {
      const p = byId(new URLSearchParams(location.search).get("id"));
      if (!p) { $("#product").innerHTML = `<p>Produit introuvable. <a href="boutique.html">Retour à la boutique</a></p>`; return; }
      document.title = p.name + " — " + S.name;
      const disc = p.oldPrice ? Math.round((1 - p.price / p.oldPrice) * 100) : 0;
      $("#product").innerHTML = `
        <div class="p-img">${visual(p, true)}</div>
        <div class="p-info">
          <div class="muted small"><a href="boutique.html">Boutique</a> / ${esc(p.category)}</div>
          <h1>${esc(p.name)}</h1>
          <div class="rating"><span class="stars">${stars(p.rating)}</span> ${p.rating} · ${p.reviews} avis</div>
          <div class="price-row big"><strong>${fmt(p.price)}</strong>${p.oldPrice ? `<s class="muted">${fmt(p.oldPrice)}</s><span class="off">-${disc}%</span>` : ""}</div>
          <p>${esc(p.description)}</p>
          <ul class="ticks">${p.features.map(f => `<li>${esc(f)}</li>`).join("")}</ul>
          <div class="buy"><input id="qty" type="number" min="1" max="10" value="1" aria-label="Quantité"><button id="add-main" class="btn" data-add="${p.id}">Ajouter au panier</button></div>
          <div class="stock">🔥 Stock limité — expédié sous 48 h</div>
          <div class="trust-mini"><span>🚚 Livraison ${esc(S.deliveryDelay)}</span><span>↩️ Retours 30 jours</span><span>🔒 Paiement sécurisé</span></div>
        </div>`;
      const alt = PRODUCTS.filter(x => x.id !== p.id && x.category === p.category).concat(PRODUCTS.filter(x => x.id !== p.id && x.category !== p.category)).slice(0, 4);
      $("#related").innerHTML = alt.map(card).join("");
    },
    cart() {
      const render = () => {
        const lines = Cart.get().map(l => ({ ...l, p: byId(l.id) })).filter(l => l.p);
        const root = $("#cart");
        if (!lines.length) { root.innerHTML = `<div class="empty"><p>Votre panier est vide.</p><a class="btn" href="boutique.html">Découvrir la boutique</a></div>`; return; }
        const sub = lines.reduce((a, l) => a + l.p.price * l.qty, 0);
        const ship = sub >= S.freeShippingFrom ? 0 : S.shippingFee;
        const left = S.freeShippingFrom - sub;
        root.innerHTML = `
          <div class="cart-lines">
            ${left > 0 ? `<div class="ship-bar"><div style="width:${Math.min(100, sub / S.freeShippingFrom * 100)}%"></div><span>Plus que ${fmt(left)} pour la livraison offerte !</span></div>` : `<div class="ship-bar full"><div style="width:100%"></div><span>🎉 Livraison offerte</span></div>`}
            ${lines.map(l => `<div class="line"><a href="produit.html?id=${l.p.id}" class="thumb">${visual(l.p)}</a>
              <div class="grow"><a href="produit.html?id=${l.p.id}"><strong>${esc(l.p.name)}</strong></a><div class="muted small">${fmt(l.p.price)}</div>
              <button class="link" data-rm="${l.p.id}">Supprimer</button></div>
              <div class="qty"><button data-dec="${l.p.id}">−</button><span>${l.qty}</span><button data-inc="${l.p.id}">+</button></div>
              <strong class="line-total">${fmt(l.p.price * l.qty)}</strong></div>`).join("")}
          </div>
          <aside class="summary"><h3>Récapitulatif</h3>
            <div class="sum"><span>Sous-total</span><span>${fmt(sub)}</span></div>
            <div class="sum"><span>Livraison</span><span>${ship ? fmt(ship) : "Offerte"}</span></div>
            <div class="sum total"><span>Total</span><span>${fmt(sub + ship)}</span></div>
            <button id="pay" class="btn btn-block">Payer ${fmt(sub + ship)}</button>
            <p id="pay-err" class="err" hidden></p>
            <p class="muted small">🔒 Paiement sécurisé par Stripe. Taxes incluses.</p></aside>`;
        root.onclick = e => {
          const t = e.target, g = a => t.dataset[a], line = id => Cart.get().find(x => x.id === id);
          if (g("inc")) Cart.setQty(g("inc"), line(g("inc")).qty + 1);
          else if (g("dec")) Cart.setQty(g("dec"), line(g("dec")).qty - 1);
          else if (g("rm")) Cart.setQty(g("rm"), 0);
          else if (t.id === "pay") return checkout(t);
          else return;
          render();
        };
      };
      render();
    },
    success() { Cart.clear(); }
  };

  async function checkout(btn) {
    const err = $("#pay-err"); err.hidden = true; btn.disabled = true; btn.textContent = "Redirection…";
    if (S.demoCheckout) { location.href = "succes.html"; return; }
    try {
      const r = await fetch(S.checkoutEndpoint, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify({ items: Cart.get() }) });
      const d = await r.json();
      if (!r.ok || !d.url) throw new Error(d.error || "Paiement indisponible");
      location.href = d.url;
    } catch (e) {
      err.textContent = "Le paiement n'est pas encore configuré (" + e.message + "). Consultez le README ou activez demoCheckout dans js/config.js.";
      err.hidden = false; btn.disabled = false; btn.textContent = "Payer";
    }
  }

  // ---------- Init ----------
  document.addEventListener("DOMContentLoaded", async () => {
    layout();
    const page = document.body.dataset.page;
    if (pages[page]) {
      try { PRODUCTS = await (await fetch("data/products.json")).json(); } catch { PRODUCTS = []; }
      pages[page]();
    }
    const nl = $("#newsletter"); if (nl) nl.addEventListener("submit", e => { e.preventDefault(); toast("Merci, vous êtes inscrit ! 🎉"); nl.reset(); });
  });
})();
