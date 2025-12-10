#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CONFIG="$ROOT/config"
MODS="$ROOT/mods"
PLUGINS="$ROOT/plugins"
MAPS="$ROOT/maps"
TEMPLATES="$ROOT/templates"

download_from_list() {
  local list_file="$1"
  local destination_dir="$2"
  local base_url="$3"
  local default_ext="$4"

  [ -f "$list_file" ] || return 0

  while IFS= read -r entry; do
    [[ -z "$entry" || "$entry" =~ ^# ]] && continue

    local name url
    name="$(echo "$entry" | awk '{print $1}')"
    url="$(echo "$entry" | awk 'NF>1{print $2}')"

    local filename="$name"
    if [[ -n "$default_ext" && "$filename" != *.* ]]; then
      filename="${filename}${default_ext}"
    fi

    local target="$destination_dir/$filename"
    if [ -f "$target" ]; then
      continue
    fi

    if [ -z "$url" ]; then
      if [ -z "$base_url" ]; then
        echo "Avertissement: aucune URL fournie pour $filename et aucune base n'est définie, téléchargement ignoré"
        continue
      fi
      url="${base_url%/}/$filename"
    fi

    echo "Téléchargement de $filename depuis $url"
    if ! curl -fsSL "$url" -o "$target"; then
      echo "Avertissement: impossible de télécharger $filename"
      rm -f "$target"
    fi
  done < "$list_file"
}

mkdir -p "$CONFIG" "$MODS" "$PLUGINS" "$MAPS" "$ROOT/logs"

# Copie le template server.properties s'il n'existe pas déjà
if [ -f "$TEMPLATES/mohist-server.properties" ]; then
  target="$CONFIG/server.properties"
  if [ ! -f "$target" ]; then
    cp "$TEMPLATES/mohist-server.properties" "$target"
  fi
fi

# Prépare des listes de téléchargement locales
if [ -f "$ROOT/mods.list" ]; then
  cp "$ROOT/mods.list" "$MODS/manifest.txt"
fi

if [ -f "$ROOT/plugins.list" ]; then
  cp "$ROOT/plugins.list" "$PLUGINS/manifest.txt"
fi

if [ -f "$ROOT/maps.list" ]; then
  cp "$ROOT/maps.list" "$MAPS/manifest.txt"
fi

# Téléchargements optionnels depuis des miroirs internes (si des URLs sont fournies)
download_from_list "$ROOT/mods.list" "$MODS" "${MOD_MIRROR_BASE:-}" ".jar"
download_from_list "$ROOT/plugins.list" "$PLUGINS" "${PLUGIN_MIRROR_BASE:-}" ".jar"
download_from_list "$ROOT/maps.list" "$MAPS" "${MAP_MIRROR_BASE:-}" ""

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
