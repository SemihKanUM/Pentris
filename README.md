# Tetris with Genetic Algorithm 🎮🧬

![Pentris](PENTRIS_free-file.png)

This project implements a **Tetris game** with both a **GUI** and a **logic engine**, extended with a **Genetic Algorithm (GA) bot** that plays infinitely by continuously improving its strategy.  

The system evolves gameplay strategies through **fitness-based selection** and **mutation/crossover techniques**, allowing the bot to optimize piece placement over time.  

---

## 🚀 Features
- **Playable Tetris game** with GUI.
- **Logic engine** for game rules, scoring, and piece management.
- **Genetic Algorithm bot**:
  - Uses fitness functions to evaluate board states.
  - Runs infinitely, learning optimal strategies for higher scores.
- Implements **HeapSort** for fast ranking of solutions.
- Modular codebase with clear separation between **UI**, **engine**, and **AI** logic.

---

## 📂 Project Structure
- `Main.java` – Entry point of the game.  
- `Board.java` – Game logic and state handling.  
- `BoardComponent.java` – GUI rendering of the board.  
- `UI.java` – Game user interface.  
- `Input.java` – Handles player input.  
- `Bot.java` – AI bot implementation.  
- `BotMethods.java` – Heuristic functions and evaluation logic.  
- `GeneticAlgorithm.java` – Core GA implementation (selection, crossover, mutation).  
- `HeapSort.java` – Utility for sorting chromosomes/fitness values.  

---

## 🛠️ Technologies Used
- **Java**  
- **Genetic Algorithms** (evolutionary optimization)  
- **Game Development** (logic + GUI)  
- **Sorting & Data Structures**  

---

## 📌 Related Topics
- Game AI  
- Evolutionary Computation  
- Heuristic Search  
- Artificial Intelligence in Games  
- Java GUI Programming  

---

## ▶️ Running the Project
1. Compile all `.java` files:  
   ```bash
   javac *.java

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
