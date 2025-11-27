# StarfunCore Velocity

Plugin Velocity fournissant le noyau StarfunCore (messagerie sécurisée entre le proxy Velocity et les hubs Spigot/Folia) via le canal plugin messaging `starfun:core`.

## Configuration

1. Génère une clé AES partagée (128/192/256 bits) en Base64, par exemple :
   ```bash
   openssl rand -base64 32
   ```
2. Renseigne cette clé dans `plugins/starfuncore/starfuncore.properties` (créé automatiquement au premier démarrage) avec la propriété `encryptionKey`.
   ```properties
   encryptionKey=VOTRE_CLE_BASE64_ICI
   ```
3. Déploie le JAR sur ton proxy Velocity. Le canal est enregistré automatiquement et les messages sont chiffrés avec la clé fournie.

## Build

Le projet utilise Gradle + Shadow pour produire un JAR prêt à l'emploi. Utilise une installation locale de Gradle 8+ ou ajoute ton wrapper avant le build.

```bash
gradle build
```

Le JAR final est généré dans `build/libs/StarfunCoreVelocity-1.0.0.jar`.
