# CubeSolver-Java

A small Java Rubik's Cube toolkit and solver (research / hobby project).

## Contents
- `Cube.java` — cube model and move implementations (byte[54] sticker representation).
- `CubeSolver.java` — depth-limited search / IDDFS solver with simple pruning; phase-1 / phase-2 routines.
- `Main.java` — entry point and simple UI integration.
- `CubeUI.java` / `Drawing.java` — Swing UI to view and manipulate the cube; keyboard controls in `KeyHandler.java`.
- `Move.java`, `IntStack.java`, `Heuristic.java` — supporting classes and utilities.

## Build & run (command line)
1. Compile:

   ```bash
   javac -d out *.java
   ```

2. Run the GUI demo:

   ```bash
   java -cp out Main
   ```

3. Run with a larger heap (recommended for heavy searches):

   ```bash
   java -Xmx2g -cp out Main
   ```

## Using the GUI
- The application will open a window showing the cube.
- Keyboard controls are defined in `KeyHandler.java` (single-letter keys apply moves; space starts the solver).

## Notes on performance
- The solver is CPU- and memory-sensitive. For large searches prefer:
  - IDDFS (keep only current path in memory) rather than storing entire frontiers.
  - Compact keys for visited-state sets (packed longs or Zobrist 64-bit keys) instead of large arrays or String keys.
  - In-place move application + undoMove to avoid frequent allocation.
