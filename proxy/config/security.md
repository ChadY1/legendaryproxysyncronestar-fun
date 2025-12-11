# Lignes directrices de sécurité ProxyForge / Velocity / Mohist

- Stocke la clé AES `starfun:core` dans `plugins/starfuncore/starfuncore.properties` et ne la versionne jamais.
- Active le forwarding moderne (Velocity) avec `forwarding-secret` unique et non partagé.
- Limite les commandes `/server`, `/alert` aux opérateurs et bloque la console distante.
- Active la compression TLS/SSL uniquement sur le load balancer, pas au sein du proxy.
- Sur Mohist, désactive les commandes de debug Forge en production et applique `online-mode=true` côté proxy.
- Surveille les logs pour détecter les tentatives de Flood/Handshake ; configure un rate-limit au niveau ProxyForge.
