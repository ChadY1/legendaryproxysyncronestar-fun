# GUI textures StarLife RP

Placez ici les PNG fournis (extraits du ticket) pour que les GUIs SQript les utilisent. Les fichiers attendus sont nommés
exactement comme suit :

- **ATM** : `atm_home.png`, `atm_withdraw.png`, `atm_deposit.png`, `atm_log.png`, `atm_transfer.png`, `atm_close.png`,
  `atm_denied.png`, `atm_success.png`, `atm_hacking.png`, `atm_hacked.png`, `note_10.png`, `note_20.png`, `note_50.png`,
  `note_100.png`, `note_500.png`, `note_1000.png`.
- **Carburant** : `fuel_pump.png`.
- **Concessionnaire** : `vehicle_grid.png`, `vehicle_insurance.png` (assurance optionnelle), `starlife_brand.png`.
- **Identité / RP** : `passport_menu.png`, `police_terminal.png`, `medic_tablet.png`, `military_ops.png`.

Si les PNG sont hébergés en amont, définissez `GUI_ASSET_BASE` (par défaut
`https://raw.githubusercontent.com/ChadY1/legendaryproxysyncronestar-fun/main/proxy/assets/gui`) et lancez `./autofix.sh`
pour télécharger automatiquement les fichiers listés dans `proxy/assets/gui.list`.

Les noms doivent correspondre aux références du script `proxy/sqript/gui_star_life.sq`.
