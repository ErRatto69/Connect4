# 🔴 Connect 4 (Forza 4) - Java CLI

> Una versione avanzata e altamente personalizzabile del classico gioco da tavolo, eseguita interamente da terminale con animazioni fluide e un'Intelligenza Artificiale competitiva.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Console](https://img.shields.io/badge/Console-CLI-4EAA25?style=for-the-badge)

## 📖 Descrizione

Questo progetto è un'implementazione completa di **Forza 4** in Java. Non si limita alle regole base, ma offre un'esperienza utente arricchita da **animazioni ANSI**, **colori**, e un sistema di configurazione profondo. 

Il cuore del progetto è un'Intelligenza Artificiale basata sull'algoritmo **Minimax con Alpha-Beta Pruning**, capace di offrire diversi livelli di sfida, fino a diventare quasi imbattibile.

## ✨ Funzionalità Principali

### 🎮 Gameplay
* **Multiplayer Locale:** Gioca contro un amico sullo stesso PC.
* **Single Player vs CPU:** Sfida il Bot con 4 livelli di difficoltà.
* **Partite Multiple:** Imposta serie di partite (al meglio di 3, 5, ecc.).
* **Dimensioni Variabili:** Scegli tu la grandezza della griglia (es. standard 7x6 o personalizzata).

### ⚙️ Personalizzazione
* **Nomi e Simboli:** Ogni giocatore può scegliere il proprio username e il carattere ASCII da usare come pedina.
* **Colori:** Selezione del colore tramite codici ANSI (Rosso, Verde, Giallo, Blu, ecc.).
* **Validazione Input:** Sistema robusto che impedisce crash dovuti a input errati.

### 🎨 Grafica e UX
* **Animazioni:** Effetto "caduta" della pedina e testo stile "macchina da scrivere".
* **Interfaccia Colorata:** Utilizzo intensivo di sequenze di escape ANSI per una UI vibrante nel terminale.

## 🧠 L'Intelligenza Artificiale (Il Bot)

La logica del Bot (`BotAlgorithm.java`) è progettata per essere efficiente e scalabile:

1.  **Minimax Algorithm:** Esplora l'albero delle mosse future per scegliere quella ottimale.
2.  **Alpha-Beta Pruning:** Ottimizza la ricerca tagliando i rami dell'albero che non porteranno a soluzioni migliori, migliorando drasticamente le performance.
3.  **Ottimizzazione della Memoria:** La `GameBoard` (oggetti complessi) viene convertita in un **byte array piatto** (1D) prima di essere processata dall'algoritmo, riducendo l'overhead della memoria e velocizzando i calcoli.
4.  **Euristica Strategica:** Il bot preferisce il controllo della colonna centrale e riconosce minacce immediate.

**Livelli di Difficoltà:**
* **Easy:** Profondità di calcolo minima (spesso casuale).
* **Medium:** Profondità 3.
* **Hard:** Profondità 6.
* **Impossible:** Profondità 12 (richiede alta ottimizzazione).

## 🚀 Come Eseguire il Progetto

### Prerequisiti
* Java Development Kit (JDK) 8 o superiore.
* Un terminale che supporti i colori ANSI (CMD di Windows moderni, PowerShell, Bash, Terminale macOS).

### Compilazione
Naviga nella cartella radice del progetto ed esegui:

```bash
javac -d bin connect4/*.java utility/*.java bot/*.java Main.java

```

### Esecuzione

```bash
java -cp bin Main

```

## 📂 Struttura del Codice

* `connect4/`: Contiene la logica di gioco (`Game`, `Match`, `Board`, `Player`) e la gestione I/O (`InputManager`, `Menu`).
* `bot/`: Contiene il cervello dell'IA (`BotAlgorithm`).
* `utility/`: Classi di supporto per la grafica (`Animation`, `ConsoleColors`).

## 🛠 To-Do & Miglioramenti Futuri

* [ ] Refactoring della logica di vittoria diagonale.
* [ ] Aggiunta del supporto per il gioco online via Socket.
* [ ] Miglioramento dell'euristica per il livello "Impossible".

## 👥 Crediti

Progettato e Sviluppato da:

* **Valerio Ratti**
* **Edoardo Ratti**

---

*Questo progetto è stato creato a scopo educativo per esplorare algoritmi di ricerca e manipolazione della console in Java.*

### Consigli extra per il tuo repository:
1.  **Aggiungi uno screenshot:** Siccome è un gioco da console colorato, fai uno screenshot mentre il gioco è in esecuzione (magari con la griglia piena di pedine colorate) e caricalo nella cartella del progetto. Poi sostituisci la sezione "Demo" o aggiungilo dopo la descrizione.
2.  **File `.gitignore`:** Assicurati di avere un file `.gitignore` che escluda la cartella `bin/` o i file `.class`, per non sporcare il repository.
