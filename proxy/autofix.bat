@echo off
setlocal enabledelayedexpansion

set ROOT=%~dp0
set ROOT=%ROOT:~0,-1%
set CONFIG=%ROOT%\config
set MODS=%ROOT%\mods
set PLUGINS=%ROOT%\plugins
set MAPS=%ROOT%\maps
set TEMPLATES=%ROOT%\templates

if not exist "%CONFIG%" mkdir "%CONFIG%"
if not exist "%MODS%" mkdir "%MODS%"
if not exist "%PLUGINS%" mkdir "%PLUGINS%"
if not exist "%MAPS%" mkdir "%MAPS%"
if not exist "%ROOT%\logs" mkdir "%ROOT%\logs"

call :copy_if_absent "%TEMPLATES%\mohist-server.properties" "%CONFIG%\server.properties"
call :copy_if_absent "%ROOT%\mods.list" "%MODS%\manifest.txt"
call :copy_if_absent "%ROOT%\plugins.list" "%PLUGINS%\manifest.txt"
call :copy_if_absent "%ROOT%\maps.list" "%MAPS%\manifest.txt"

set MOD_MIRROR_BASE=%MOD_MIRROR_BASE%
set PLUGIN_MIRROR_BASE=%PLUGIN_MIRROR_BASE%
set MAP_MIRROR_BASE=%MAP_MIRROR_BASE%

call :download_from_list "%ROOT%\mods.list" "%MODS%" "%MOD_MIRROR_BASE%" .jar
call :download_from_list "%ROOT%\plugins.list" "%PLUGINS%" "%PLUGIN_MIRROR_BASE%" .jar
call :download_from_list "%ROOT%\maps.list" "%MAPS%" "%MAP_MIRROR_BASE%" ""

if not exist "%CONFIG%\checksums.sha256" (
  echo # Renseigner ici les SHA-256 des mods/plugins (une ligne: ^<sha256^>  ^<chemin^>)>"%CONFIG%\checksums.sha256"
)

if not exist "%CONFIG%\security.md" (
  echo # Rappel: complète ce fichier avec les secrets et politiques propres à ton environnement.>"%CONFIG%\security.md"
)

echo Autofix Windows terminé. Arborescence prête dans %ROOT%
goto :eof

:copy_if_absent
if exist %1 (
  if not exist %2 (
    copy %1 %2 >nul
  )
)
goto :eof

:download_from_list
set LIST=%1
set DEST=%2
set BASE=%3
set DEFAULT_EXT=%4
if not exist %LIST% goto :eof
for /f "usebackq tokens=1,2" %%A in (%LIST%) do (
  set NAME=%%A
  set URL=%%B
  if "!NAME!"=="" goto :continue
  if "!NAME:~0,1!"=="#" goto :continue
  set FILENAME=!NAME!
  if not "!DEFAULT_EXT!"=="" (
    echo !FILENAME!| findstr /R /C:"\." >nul || set FILENAME=!FILENAME!!DEFAULT_EXT!
  )
  set TARGET=%DEST%\!FILENAME!
  if exist !TARGET! goto :continue
  if "!URL!"=="" (
    if "!BASE!"=="" (
      echo Avertissement: aucune URL pour !FILENAME! et aucune base n'est definie
      goto :continue
    )
    set URL=!BASE!/!FILENAME!
  )
  echo Téléchargement de !FILENAME! depuis !URL!
  powershell -Command "try {Invoke-WebRequest -Uri '!URL!' -OutFile '!TARGET!' -UseBasicParsing} catch {Remove-Item -ErrorAction SilentlyContinue '!TARGET!'; Write-Host 'Avertissement: echec de telechargement pour !FILENAME!' }"
  :continue
)
goto :eof
