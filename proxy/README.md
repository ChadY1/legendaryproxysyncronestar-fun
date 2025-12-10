# ProxyForge + Mohist 1.7.10 (RolePlay/Moddé/Plugins)

Ce dossier fournit un plan de configuration automatisable pour un réseau ProxyForge / Velocity au-dessus d'une instance Mohist 1.7.10 orientée roleplay. Les fichiers sont pensés pour être préremplis par les pipelines CI/CD et complétés par le script `autofix.sh` (ou `autofix.bat` sous Windows).

## Architecture proposée
- **Proxy Velocity/ProxyForge** : Proxy sécurisé recevant StarfunCore Velocity et redirigeant vers Mohist.
- **Backend Mohist 1.7.10** : Serveur hybride Spigot/Forge pour les mods et plugins listés ci-dessous.
- **Canal sécurisé** : Canal `starfun:core` utilisant la clé AES documentée dans le README racine.

## Mods requis (Mohist 1.7.10)
La liste complète est dans `mods.list`. Inclure les dépendances officielles de chaque mod (Forge 1.7.10, iChunUtil, CoFHCore, etc.) et vérifier les licences avant distribution.

- ArmaManiaMod1.7.10 (pack AltisFun)
- Decocraft
- MrCrayfish's Furniture Mod
- RoadStuff
- Dynamic Surroundings
- Gliby's Voice Chat
- Flan's Mod + Content Packs (Vehicles, Guns, toutes variantes)
- ArchitectureCraft
- Carpenter's Blocks
- TubeTransports 1.7.10 + add-ons

## Plugins recommandés
- Authentification/2FA au proxy (ex. Authlib-Injector côté client si nécessaire, ou plugin d'authentification Velocity).
- Limiteurs de vitesse de connexion et antiproxy ouvert.
- Journalisation centralisée et anti-crash.

## Sécurité et durcissement
- Génère une clé AES unique pour `starfun:core` et applique-la côté proxy et hubs (voir README racine).
- Active le `forwarding-secret` ProxyForge/Velocity et désactive les commandes sensibles pour les non-opérateurs.
- Utilise des ACL au niveau du système pour bloquer les ports non utilisés.

## Automatisation
1. Lance `./proxy/autofix.sh` pour créer l'arborescence standard (mods, plugins, maps, config) et injecter les templates de config. Sous Windows, `autofix.bat` réalise les mêmes étapes (Invoke-WebRequest) avec les mêmes variables d'environnement. Les archives `.zip`/`.tar.gz` sont décompressées automatiquement après téléchargement. Les binaires récupérés sont mis en cache dans `proxy/downloads/` (et copiés vers `mods/`, `plugins/`, `maps/`), ce qui évite de polluer le dépôt : les dossiers de sortie sont maintenant ignorés par Git par défaut.
2. Fournis les artefacts (mods/plugins/maps) via un miroir interne ou un stockage S3 ; le script consomme `MOD_MIRROR_BASE`, `PLUGIN_MIRROR_BASE` et `MAP_MIRROR_BASE` pour télécharger automatiquement les fichiers listés dans `mods.list`, `plugins.list` et `maps.list` si tu ajoutes les URLs. Plusieurs URLs publiques sont pré-renseignées (Skript, LuckPerms, Flan's packs, TooManyItems, SecurityCraft, etc.) pour faciliter les tests connectés. Les fichiers existants sont ré-utilisés et ré-extraits à chaque passage.
3. Le workflow GitHub Actions `.github/workflows/proxy-autostart.yml` simule ce bootstrap en CI (checkout, exécution d'`autofix.sh`, téléchargement et inventaire des artefacts prêts à déployer).
4. Vérifie les empreintes SHA-256 via la table `proxy/config/checksums.sha256` (à compléter dans ta chaîne CI) avant de déployer.
5. Déploie le JAR StarfunCore Velocity sur le proxy et vérifie la présence de la clé AES et du `forwarding-secret`.
6. Pour les GUIs roleplay (ATM, pompe, concessionnaire), ajoute les textures PNG dans `proxy/assets/gui` en suivant `proxy/assets/gui/README.md`, puis copie les scripts SQript du dossier `proxy/sqript/` dans `mods/SQript/scripts`.

## Comment utiliser ce dossier
- **CI/CD** : copie `proxy/` dans ton artifact et complète `config/security.md`, `config/checksums.sha256` et la clé AES.
- **Ops** : exécute `autofix.sh` après chaque mise à jour pour recréer les fichiers manquants et rafraîchir les templates.
- **Débogage** : surveille les logs ProxyForge/Velocity et Mohist pour t'assurer que le canal `starfun:core` est opérationnel et que les mods se chargent sans conflits.
