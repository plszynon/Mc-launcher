# MC Launcher

Apka na Androida z przyciskami do zdalnego startowania serwera Minecraft
(Java + Bedrock + tunel playit.gg), działającego w Termuxie.

**Jak to działa:** apka NIE zawiera serwera w środku. Wysyła komendę
do zainstalowanego Termuxa przez oficjalne API `RUN_COMMAND`, a Termux
odpala prawdziwe skrypty. To bezpieczne i lekkie podejście.

## Krok 1 — zbuduj APK na GitHubie

1. Utwórz nowe repozytorium na GitHubie (np. `mc-launcher`).
2. Wrzuć do niego całą zawartość tego folderu (`git push` albo
   przeciągnij pliki w interfejsie GitHuba).
3. Wejdź w zakładkę **Actions** w repo — workflow `Build APK`
   uruchomi się automatycznie po pushu.
4. Po ok. 2–3 minutach, w zakładce zakończonego joba, w sekcji
   **Artifacts** pobierzesz plik `mc-launcher-apk.zip`. W środku
   jest `app-debug.apk`.
5. Przenieś ten plik na telefon i zainstaluj (włącz "instalacja
   z nieznanych źródeł" jeśli Android o to zapyta).

## Krok 2 — przygotuj Termux (zrób to raz)

W Termuxie:

```bash
mkdir -p ~/.termux
echo "allow-external-apps=true" >> ~/.termux/termux.properties
termux-reload-settings
```

To pozwala zewnętrznym apkom (naszej) wysyłać komendy do Termuxa.
Zainstaluj też z F-Droid apkę **Termux:API** (wymagana przez
`termux-wake-lock` w skryptach).

## Krok 3 — wgraj skrypty startowe do Termuxa

Skopiuj 4 pliki z folderu `scripts/` (`start-all.sh`, `start-java.sh`,
`start-bedrock.sh`, `stop-all.sh`) do katalogu domowego Termuxa
(`~`, czyli `/data/data/com.termux/files/home/`), np. przez
`termux-setup-storage` + skopiowanie z pamięci telefonu, albo wklejając
ich zawartość ręcznie przez `nano start-all.sh`.

Nadaj im prawa wykonywania:

```bash
chmod +x ~/start-all.sh ~/start-java.sh ~/start-bedrock.sh ~/stop-all.sh
```

Upewnij się, że masz już postawione serwery w `~/mc-java` (Paper) i
`~/mc-bedrock` (PocketMine-MP) oraz plik `~/playit` (tunel) — zgodnie
z wcześniejszą instrukcją.

## Krok 4 — użycie

Otwórz apkę MC Launcher → naciśnij "Start Java + Bedrock + Tunel".
Android poprosi raz o zgodę na uruchamianie komend w Termuxie —
zaakceptuj. Status pojawi się w apce, pełne logi zobaczysz w plikach
`~/java.log`, `~/bedrock.log`, `~/playit.log` w Termuxie.
