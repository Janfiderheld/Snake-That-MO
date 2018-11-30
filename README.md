# Snake That!

Verantwortlich: [Niclas Muss](https://github.com/NiclasMuss) & [Jan-Philipp Töberg](https://github.com/Janfiderheld)

Projektbetreuer: M.Sc. Andreas Schmelter & Prof. Dr. rer. nat. Stefan Heiss


Start: 06.11.2018

Zwischenstand: 11.12.2018

Abgabe: 08.01.2019

Präsentation: 08.01., 15.01. oder 22.01.2019

## Projektbeschreibung:

Bei Snake That! handelt es sich um ein innovatives Android-Spiel, welches den Klassiker Snake neu auflegt und mit einer interessanten Steuerung per Touch kombiniert!
Der Spieler muss Punkte (in Form von NFC-Tags (Stand: 30.11.)) einsammeln um mit seiner Schlange zu wachsen und den Highscore zu knacken.
Dabei wird die Schlange über eine "Schleuder"-Mechanik intuitiv bedient & gesteuert!

### Programmier-Konventionen

Die Einhaltung der folgenden Konventionen wird von der Java-Sprachsyntax nicht zwingend vorgeschrieben. Ihre Einhaltung erhöht die Lesbarkeit der Programmquellen allerdings ungemein. Nur durch die Beachtung von einheitlichen Programmierkonventionen kann umfangreicherer Quellcode mit vertretbaren Aufwand gereviewed, gepflegt und ergänzt werden. Die Einhaltung folgender Konventionen wird empfohlen (vgl. auch mit SUNs Empfehlungen Code Conventions for the Java Programming Language):
- Schreibweisen (s.a.Code Conventions for the Java Programming Language, Kap. 9):
  - Klassen- und Interfacenamen beginnen mit einem Großbuchstaben und sollten ein Substantiv sein.
  - Methodennamen beginnen mit einem Kleinbuchstaben und sollten ein Verb sein.
  - Variablen beginnen mit einem Kleinbuchstaben.
  - Konstanten (final deklarierte Variablen) werden nur aus Großbuchstaben und dem Underscore-Zeichen '_' zusammengesetzt.
  - Paketnamen (Packages) beginnen mit einem Kleinbuchstaben.
- Der Zweck von Klassen, Methoden und Variablen, die nicht nur lokal in einer Methode genutzt werden, ist durch eine entsprechend sinnvolle Namensgebung deutlich zu machen. Sind ausführlichere Erklärungen zu Zweck und Nutzung nötig, so sind diese in einer (Javadoc-)Kommentierung anzugeben.
- Anweisungen (Statements):
  - Nur eine Anweisung je Zeile.
  - Zeilen innerhalb eines Anweisungsblocks (zwischen den geschweiften Klammern {...}) einrücken (2 oder 4 Leerzeichen).
  - Für Verzweigungsbedingungen (if(..), else if(..), else) und Schleifenanfänge (for(..;..;..), while(..), do) ist jeweils eine eigene Zeile zu nutzen. (Auch eine einzelne return-Anweisung nach einer if-Abfrage sollte (eingerückt) auf eine eigene Zeile geschrieben werden.)
- Exceptions sollten in aller Regel nicht mit einer leerencatch-Anweisungen weggefangen werden (Empfehlung:printStackTrace() verwenden).
- Zeilenlänge: Maximal 80 Zeichen

### Hilfreiche Links / Quellen

- [libGDX: Website](https://libgdx.badlogicgames.com/)
- [libGDX: Beispiel-Spiel / Tutorial](https://github.com/libgdx/libgdx/wiki/A-simple-game)
- [libGDX: Memory Management](https://github.com/libgdx/libgdx/wiki/Memory-management)
- [Artikel über die am häufig vorhandenen Bildschirmauflösungen von Android-Smartphones](https://deviceatlas.com/blog/most-used-smartphone-screen-resolutions)

### aktuelle Bildquellen (Stand: 30.11.)

- [Waluigi-Block](http://www.softicons.com/game-icons/super-mario-blocks-icons-by-dannysp/waluigi-block-icon)
- [NFC-Symbol](https://nfc-reader.en.softonic.com/android)
- [Bitcoin](https://addons.opera.com/de/extensions/details/bitcoin-monero-miner/)