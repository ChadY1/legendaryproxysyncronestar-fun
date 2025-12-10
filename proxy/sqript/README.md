# SQript 1.7.10

Notes d'installation rapides :
- Installer le mod SQript 1.7.10 côté serveur et côté client (copier le JAR dans le dossier `mods`).
- Placer les scripts `.sq` de ce dossier dans `mods/SQript/scripts` (ou le chemin attendu par la distribution).
- Redémarrer le serveur pour charger les scripts ou utiliser la commande `/sqript reload` si disponible.

Scripts fournis :
- `atm_hacking.sq` : ajoute un ATM verrouillé par une clé rare et une interface de piratage.
- `atm_banking.sq` : gestion des comptes (dépôt/retrait/soldes) avec vérification des droits et coffre de sécurité.
- `identity_vehicles.sq` : clés de véhicule, passeports et permis via commandes et objets.
- `military_ops.sq` : crochets pour gameplay militaire (alerts, balises, radio de squad).
- `police_ops.sq` : interactions policières (menottes, sirènes, alertes).
- `medic_ops.sq` : trousse médicale, défibrillateur, stabilisation de blessés.
- `gui_star_life.sq` : GUIs StarLife RP (ATM complet, virements, pompe à essence, assurance véhicule, terminaux police/médic/militaire, passeport) avec les textures PNG décrites dans `../assets/gui/README.md`.
- `starliferp_core.sq` : reprise du bundle iRP (chat RP/HRP/radio, menottes, fouille, PV électroniques) avec toutes les commandes et préfixes renommés en `/slr...` pour StarLifeRP.
- `starliferp_jobs.sq` : métiers simplifiés façon iRP (bucheron/mineur) avec atelier `/slratelier` et kit `/slrjobkit` limitant les erreurs sur 1.7.10.

Documentation officielle : https://sqript.github.io/ (miroir communautaire)
