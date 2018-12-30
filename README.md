# Snake That!

Verantwortlich: [Niclas Muss](https://github.com/NiclasMuss) & [Jan-Philipp Töberg](https://github.com/Janfiderheld)

Projektbetreuer: M.Sc. Andreas Schmelter & Prof. Dr. rer. nat. Stefan Heiss


Start: 06.11.2018

Zwischenstand: 11.12.2018

Abgabe: 08.01.2019

Präsentation: 08.01., 15.01. oder 22.01.2019

## Projektbeschreibung:

Bei Snake That! handelt es sich um ein innovatives Android-Spiel, welches den Klassiker Snake neu auflegt und mit einer interessanten Steuerung per Touch kombiniert!
Der Spieler muss Punkte in Form von NFC-Tags & Bitcoins einsammeln um mit seiner Schlange zu wachsen und den Highscore zu knacken. Dabei muss unterschiedlichen Hindernissen ausgewichen werden.
Dabei wird die Schlange über eine "Schleuder"-Mechanik intuitiv bedient & gesteuert!

## Hilfreiche / wichtige Links & Quellen

Im Folgenden werden alle Links (und ähnliches) aufgelistet, welche uns bei der Erstellung der App geholfen haben.

### Text- / Quellcode-Quellen 

- [libGDX: Website](https://libgdx.badlogicgames.com/) (abgerufen am: 07.11.)
- [libGDX: Beispiel-Spiel (Tutorial)](https://github.com/libgdx/libgdx/wiki/A-simple-game) (abgerufen am: 13.11.)
- [Umsetzung (mit Erweiterung) des Beispiel-Spiels](https://drive.google.com/drive/folders/11WqeAeZgEVUvycyAEVo4V9yjTBE-AW-w) (abgerufen am: 13.11.)
- [Artikel über die am häufig vorhandenen Bildschirmauflösungen von Android-Smartphones](https://deviceatlas.com/blog/most-used-smartphone-screen-resolutions) (abgerufen am: 13.11.)
- [libGDX: Memory Management](https://github.com/libgdx/libgdx/wiki/Memory-management) (abgerufen am: 27.11.)
- [Hiero (Bitmap Font Packaging Tool)](https://github.com/libgdx/libgdx/wiki/Hiero) (abgerufen am: 04.12.)
- [Texte & Schriftarten in libGDX verwenden](https://www.gamefromscratch.com/post/2014/11/21/LibGDX-Video-Tutorial-Creating-and-Using-Fonts-and-Text.aspx) (abgerufen am: 04.12.)
- [Zusammenfassung der Apache 2.0 Lizenz](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)) (abgerufen am: 06.12.)
- [StackOverflow: Wechseln von Screens in LibGDX](https://stackoverflow.com/questions/25837013/switching-between-screens-libgdx) (abgerufen am: 11.12.)
- [libGDX: Erweiterung des Beispiel-Spiels um Menüs](https://github.com/libgdx/libgdx/wiki/Extending-the-simple-game) (abgerufen am: 11.12.)
- [StackOverflow: Datentyp eines generischen Objekts rausfinden](https://stackoverflow.com/questions/11798951/generic-type-of-local-variable-at-runtime) (abgerufen am: 16.12.)
- [libGDX: multi-linguale Strings](https://github.com/libgdx/libgdx/wiki/Internationalization-and-Localization) (abgerufen am: 18.12.)
- [libGDX: Umgang mit Tabellen](https://github.com/libgdx/libgdx/wiki/Table) (abgerufen am: 29.12.)
- [Texturen mit libGDX packen](http://www.programmingmoney.com/libgdx-texturepacker-tutorial/) (abgerufen am: 29.12.)

### Bild- & Audioquellen

- [Ziegelstein-Block](https://soundimage.org/txr-brick-cartoon/) (abgerufen am: 07.12.)
- [Pipes-Hintergrund](https://www.toptal.com/designers/subtlepatterns/pipes-pattern/) (abgerufen am: 18.12.)
- [Hintergrundmusik: Magical Getaway](http://soundimage.org/wp-content/uploads/2016/08/Magical-Getaway_Looping.mp3) (abgerufen am: 29.12.)
- [Geräusch beim Einsammeln von Punkten](http://soundimage.org/wp-content/uploads/2016/04/UI_Quirky1.mp3) (abgerufen am: 30.12.)
- [Explosion beim Berühren eines Fasses](http://soundimage.org/wp-content/uploads/2016/04/Explosion1.mp3) (abgerufen am: 30.12.)
- [Lebensverlust](http://soundimage.org/wp-content/uploads/2016/04/Laser-Ricochet3.mp3) (abgerufen am: 30.12.)
- [Leben dazu](http://soundimage.org/wp-content/uploads/2016/04/SynthChime6.mp3) (abgerufen am: 30.12.)
- Herz (gefüllt und ungefüllt): selbstgemacht
- Coins (Leben & Punkte): selbstgemacht
- Fass (explosiv): selbstgemacht

### Programmier-Konventionen

Die Einhaltung der folgenden Konventionen wird von der Java-Sprachsyntax nicht zwingend vorgeschrieben. Ihre Einhaltung erhöht die Lesbarkeit der Programmquellen allerdings ungemein. Nur durch die Beachtung von einheitlichen Programmierkonventionen kann umfangreicherer Quellcode mit vertretbaren Aufwand gereviewed, gepflegt und ergänzt werden. Die Einhaltung folgender Konventionen wird empfohlen (vgl. auch mit SUNs Empfehlungen Code Conventions for the Java Programming Language):
- Schreibweisen (s.a.Code Conventions for the Java Programming Language, Kap. 9):
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