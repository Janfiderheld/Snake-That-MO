# Snake That!

Verantwortlich: [Niclas Muss](https://github.com/NiclasMuss) & [Jan-Philipp Töberg](https://github.com/Janfiderheld)

Projektbetreuer: M.Sc. Andreas Schmelter & Prof. Dr. rer. nat. Stefan Heiss


Start: 06.11.2018

Zwischenstand: 11.12.2018

Abgabe: 08.01.2019

Präsentation: 08.01.2019

Release: 14.02.2019

Der offizielle Teil der Projektarbeit ist damit abgeschlossen!
Außerdem ist das Spiel jetzt offiziell im Google Play Store hochgeladen! Finden tut man es [hier](https://play.google.com/store/apps/details?id=com.muss_and_toeberg.snake_that)

## Projektbeschreibung:

Bei Snake That! handelt es sich um ein innovatives Android-Spiel, welches den Klassiker Snake neu auflegt und mit einer interessanten Schleuder-Steuerung per Touch kombiniert!
Der Spieler muss Punkte einsammeln um mit seiner Schlange zu wachsen und den Highscore zu knacken. Dabei muss unterschiedlichen Hindernissen ausgewichen werden.
Sollte man jedoch trotzdem Leben verlieren, so kann man sich diese selbstverständlich zurückholen. Außerdem kann per Portal abgekürzt werden.

## Hilfreiche / wichtige Links & Quellen

Im Folgenden werden alle Links (und ähnliches) aufgelistet, welche uns bei der Erstellung der App geholfen haben.

### Text- / Quellcode-Quellen 

- [libGDX: Website](https://libgdx.badlogicgames.com/) (abgerufen am: 07.11.)
- [libGDX: API Dokumentation](https://libgdx.badlogicgames.com/ci/nightlies/docs/api/) (abgerufen am: 07.11.)
- [libGDX: Beispiel-Spiel (Tutorial)](https://github.com/libgdx/libgdx/wiki/A-simple-game) (abgerufen am: 13.11.)
- [Umsetzung (mit Erweiterung) des Beispiel-Spiels](https://drive.google.com/drive/folders/11WqeAeZgEVUvycyAEVo4V9yjTBE-AW-w) (abgerufen am: 13.11.)
- [Artikel über die am häufig vorhandenen Bildschirmauflösungen von Android-Smartphones](https://deviceatlas.com/blog/most-used-smartphone-screen-resolutions) (abgerufen am: 13.11.)
- [Texte & Schriftarten in libGDX verwenden](https://www.gamefromscratch.com/post/2014/11/21/LibGDX-Video-Tutorial-Creating-and-Using-Fonts-and-Text.aspx) (abgerufen am: 04.12.)
- [Zusammenfassung der Apache 2.0 Lizenz](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)) (abgerufen am: 06.12.)
- [StackOverflow: Wechseln von Screens in LibGDX](https://stackoverflow.com/questions/25837013/switching-between-screens-libgdx) (abgerufen am: 11.12.)
- [libGDX: Erweiterung des Beispiel-Spiels um Menüs](https://github.com/libgdx/libgdx/wiki/Extending-the-simple-game) (abgerufen am: 11.12.)
- [libGDX: multi-linguale Strings](https://github.com/libgdx/libgdx/wiki/Internationalization-and-Localization) (abgerufen am: 18.12.)
- [libGDX: Umgang mit Tabellen](https://github.com/libgdx/libgdx/wiki/Table) (abgerufen am: 29.12.)
- [Texturen mit libGDX packen](http://www.programmingmoney.com/libgdx-texturepacker-tutorial/) (abgerufen am: 29.12.)
- [StackOverflow: Zeilenlänge in Android Studio setzen](https://stackoverflow.com/questions/30851617/how-to-set-max-line-length-in-android-studio-code-editor) (abgerufen am: 04.01.)
- [StackOverflow: Verwendung von @see in JavaDocs](https://stackoverflow.com/questions/5011291/usage-of-see-in-javadoc) (abgerufen am: 06.01.)
- [StackOverflow: Nutzung von Toasts mit libGDX](https://stackoverflow.com/questions/29142360/libgdx-toast-message) (abgerufen am: 09.02.)

### Bild- & Audioquellen

- [Ziegelstein-Block](https://soundimage.org/txr-brick-cartoon/) (abgerufen am: 07.12.)
- [Pipes-Hintergrund](https://www.toptal.com/designers/subtlepatterns/pipes-pattern/) (abgerufen am: 18.12.)
- [Hintergrundmusik: Magical Getaway](http://soundimage.org/wp-content/uploads/2016/08/Magical-Getaway_Looping.mp3) (abgerufen am: 29.12.)
- [Geräusch beim Einsammeln von Punkten](http://soundimage.org/wp-content/uploads/2016/04/UI_Quirky1.mp3) (abgerufen am: 30.12.)
- [Explosion beim Berühren eines Fasses](http://soundimage.org/wp-content/uploads/2016/04/Explosion1.mp3) (abgerufen am: 30.12.)
- [Lebensverlust](http://soundimage.org/wp-content/uploads/2016/04/Laser-Ricochet3.mp3) (abgerufen am: 30.12.)
- [Leben dazu](http://soundimage.org/wp-content/uploads/2016/04/SynthChime6.mp3) (abgerufen am: 30.12.)
- [Länderflaggen](https://www.free-country-flags.com/index.php) (abgerufen am: 01.01.)
- [Portal](https://opengameart.org/content/golgotha-effects-textures-acid-frontjpg) (abgerufen am: 04.01.)
- Herz (gefüllt und ungefüllt): selbstgemacht
- Coins (Leben & Punkte): selbstgemacht
- Fass (explosiv): selbstgemacht
- Weihnachtsmann-Mütze: selbstgemacht
- App-Icon: selbstgemacht

### Programmier-Konventionen

Die Einhaltung der folgenden Konventionen wird von der Java-Sprachsyntax nicht zwingend vorgeschrieben. Ihre Einhaltung erhöht die Lesbarkeit der Programmquellen allerdings ungemein. Nur durch die Beachtung von einheitlichen Programmierkonventionen kann umfangreicherer Quellcode mit vertretbaren Aufwand gereviewed, gepflegt und ergänzt werden. Die Einhaltung folgender Konventionen wird empfohlen (vgl. auch mit SUNs Empfehlungen Code Conventions for the Java Programming Language):
- Schreibweisen (s.a. Code Conventions for the Java Programming Language, Kap. 9):
  - Klassen- und Interfacenamen beginnen mit einem Großbuchstaben und sollten ein Substantiv sein
  - Methodennamen beginnen mit einem Kleinbuchstaben und sollten ein Verb sein
  - Variablen beginnen mit einem Kleinbuchstaben
  - Konstanten (final deklarierte Variablen) werden nur aus Großbuchstaben und dem Underscore-Zeichen '_' zusammengesetzt
  - Paketnamen (Packages) beginnen mit einem Kleinbuchstaben
- Der Zweck von Klassen, Methoden und Variablen, die nicht nur lokal in einer Methode genutzt werden, ist durch eine entsprechend sinnvolle Namensgebung deutlich zu machen. Sind ausführlichere Erklärungen zu Zweck und Nutzung nötig, so sind diese in einer (Javadoc-)Kommentierung anzugeben
- Anweisungen (Statements):
  - Nur eine Anweisung je Zeile
  - Zeilen innerhalb eines Anweisungsblocks (zwischen den geschweiften Klammern {...}) einrücken (2 oder 4 Leerzeichen)
  - Für Verzweigungsbedingungen (if(..), else if(..), else) und Schleifenanfänge (for(..;..;..), while(..), do) ist jeweils eine eigene Zeile zu nutzen. (Auch eine einzelne return-Anweisung nach einer if-Abfrage sollte (eingerückt) auf eine eigene Zeile geschrieben werden.)
- Exceptions sollten in aller Regel nicht mit einer leeren catch-Anweisungen weggefangen werden (Empfehlung: printStackTrace() verwenden)
- Zeilenlänge: Maximal 80 Zeichen