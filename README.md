# MusicMenu

Plugin Paper 1.20.6 pour lancer les musiques d'un resource pack via `/musicmenu`.

## Joueurs autorises
- Anguile09
- DustMan00

## Compilation GitHub
Le workflow `.github/workflows/build.yml` compile automatiquement le projet a chaque push.

Recuperer ensuite le JAR dans :
Actions -> dernier build -> Artifacts -> MusicMenu-1.1.0

## Installation
Placer `MusicMenu-1.1.0.jar` dans le dossier `/plugins/` du serveur puis redemarrer completement le serveur.

## Audio
Les sons sont lances avec `SoundCategory.MUSIC`. Ils suivent donc le curseur Minecraft :
Options -> Musique et sons -> Musique

Les Sound Events du resource pack doivent rester en minuscules :
`musique.volcan_boss`, `musique.greenwood_orage`, etc.
