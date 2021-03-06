# Omegapoint Othello robottävling 

## Kom igång
Du måste ha Java8 installerat på din dator
(Om du vill bygga/köra med maven så skall du även installera det - https://maven.apache.org/download.cgi)

### Eclipse
1. Checka ut koden
```git clone https://github.com/Omegapoint/othello.git```
2. File -> New... -> Java Project
  * Project name: othello
  * Klicka ur "Use default location" och ange istället katalogen där du klonade ut koden
  * Finish
3. Högerklick på src/main/Othello.java -> Run as -> Java Application

### IntelliJ IDEA
1. Checka ut koden
```git clone https://github.com/Omegapoint/othello.git```
2. New -> Project from existing sources -> (välj pom.xml-filen där du klonade koden)
3. Next -> ... -> Next -> Finish
4. Högerklick på src/main/Othello.java -> Run Othello.main()

### Bygga/köra med maven
1. Checka ut koden
```git clone https://github.com/Omegapoint/othello.git```
2. Ställ dig (med cd) i katalogen dit du klonat koden (filen pom.xml finns i denna katalog)
3. Bygg och skapa jar-filen
```mvn clean install```
4. Du kan antingen köra applikationen från jar-filen
```java -jar target/othello-1.0-SNAPSHOT.jar```
5. ...eller köra det direkt från maven
```mvn exec:java -Dexec.mainClass=main.Othello```

## Bygga en robot

1. Skapa en klass i katalogen `src/player/agents` och se till att ärver klassen `Player` (ge den något roligt namn)

## Poängräkning
Seriesystem med hemma- och bortamatcher:
* Vinst 3p, Oavgjort 1p, förlust 0p
* Poängskillnad (vid lika poäng)
* Inbördesmöten (vid lika poängskillnad)
* Antal gjorda "mål" (Plus)
* Rundpingis
