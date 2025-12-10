#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CONFIG="$ROOT/config"
MODS="$ROOT/mods"
PLUGINS="$ROOT/plugins"
TEMPLATES="$ROOT/templates"

mkdir -p "$CONFIG" "$MODS" "$PLUGINS" "$ROOT/logs"

# Copie le template server.properties s'il n'existe pas déjà
if [ -f "$TEMPLATES/mohist-server.properties" ]; then
  target="$CONFIG/server.properties"
  if [ ! -f "$target" ]; then
    cp "$TEMPLATES/mohist-server.properties" "$target"
  fi
fi

# Prépare une liste des mods à télécharger
if [ -f "$ROOT/mods.list" ]; then
  manifest="$MODS/manifest.txt"
  cp "$ROOT/mods.list" "$manifest"
fi

# Téléchargement optionnel depuis un miroir interne (si MOD_MIRROR_BASE est fourni)
if [ -n "${MOD_MIRROR_BASE:-}" ] && [ -f "$ROOT/mods.list" ]; then
  while IFS= read -r mod; do
    [[ -z "$mod" || "$mod" =~ ^# ]] && continue
    filename="${mod}.jar"
    destination="$MODS/$filename"
    if [ ! -f "$destination" ]; then
      echo "Téléchargement de $filename depuis $MOD_MIRROR_BASE"
      curl -fsSL "${MOD_MIRROR_BASE%/}/$filename" -o "$destination" || echo "Avertissement: impossible de télécharger $filename"
    fi
  done < "$ROOT/mods.list"
fi

# Génère un fichier de contrôles si absent
checksums="$CONFIG/checksums.sha256"
if [ ! -f "$checksums" ]; then
  echo "# Renseigner ici les SHA-256 des mods/plugins (une ligne: <sha256>  <chemin>)" > "$checksums"
fi

# Note d'exploitation
notice="$CONFIG/security.md"
if [ ! -f "$notice" ]; then
  cat > "$notice" <<'SECURITY'
# Rappel: complète ce fichier avec les secrets et politiques propres à ton environnement.
SECURITY
fi

echo "Autofix terminé. Arborescence prête dans $ROOT"
