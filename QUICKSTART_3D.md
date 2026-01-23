# 🚀 Quick Start Guide - 3D UI

**AI Generated 3D Visualization for Rubik's Cube Solver**

---

## ⚡ 30-Second Setup

### Step 1: Compile
```bash
cd "/Users/evanlowry/Documents/VS Code/CubeSolver-Java-1"
javac Main3D.java
```

### Step 2: Run
```bash
java Main3D
```

### Step 3: Use
- Press **SPACE** to solve
- Watch messages appear on screen!

---

## 🎮 Essential Controls

| Key | Action |
|-----|--------|
| `SPACE` | 🎯 Solve the cube |
| `Q` | 🚪 Quit |
| `R` | 🔄 Reset to solved state |

### Cube Rotations
| Face | Clockwise | Counter-CW | 180° |
|------|-----------|------------|------|
| Up | `J` | `F` | `1` |
| Down | `S` | `L` | `2` |
| Right | `I` | `K` | `3` |
| Left | `D` | `E` | `4` |
| Front | `H` | `G` | `5` |
| Back | `W` | `O` | `6` |

---

## 📺 What You'll See

```
┌─────────────────────────────────────────────────────────────────┐
│                                                                 │
│                  ◇                    ┌──────────────────┐     │
│                 ◇ ◇                   │  MESSAGE LOG     │     │
│                ◇ W ◇                  ├──────────────────┤     │
│               ◇ W W ◇                 │ [INFO] Cube      │     │
│              ◇ W W W ◇                │   scrambled!     │     │
│             ◇─────────◇               │ [SOLVER] Start   │     │
│            ◇ O O O│G G G              │   solving...     │     │
│           ◇ O O O │G G G              │ [SUCCESS] Solved │     │
│          ◇ O O O  │G G G              │   in 2.3 sec     │     │
│           ◇───────┴─────◇             │                  │     │
│            ◇ Y Y Y Y ◇                └──────────────────┘     │
│             ◇ Y Y Y ◇                                          │
│              ◇ Y Y ◇                 ┌──────────────────┐     │
│               ◇ Y ◇                  │  CONTROLS        │     │
│                ◇ ◇                   ├──────────────────┤     │
│                 ◇                    │ SPACE - Solve    │     │
│                                      │ Q - Quit         │     │
│         Isometric 3D View            │ R - Reset        │     │
│                                      │ 1-6 - Double     │     │
│                                      │ Letters - Moves  │     │
│                                      └──────────────────┘     │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🎨 Visual Features

### 1. Isometric 3D Cube
- Three visible faces at once
- Standard color scheme:
  - **White** = Top
  - **Yellow** = Bottom
  - **Green** = Front
  - **Blue** = Back
  - **Red** = Right
  - **Orange** = Left

### 2. Live Message Feed
- All solver output appears on screen
- Color-coded by type:
  - 🔵 Blue = Info
  - 🟢 Green = Success
  - 🔴 Red = Error
  - 🟡 Yellow = Solver updates
- Auto-scrolls
- Keeps 20 recent messages

### 3. Controls Reference
- Always visible
- No need to remember keys
- Quick reference guide

---

## 🔥 Try These Commands

### Scramble the cube
In `Main3D.java`, line 22, change the scramble:
```java
cube.performMoves("R U R' U' R' F R2 U' R' U' R U R' F'");
```

### Change solve depth
In `Main3D.java`, line 17:
```java
public static int maxDepth = 15;  // Try deeper search
```

### Clear message log
Messages auto-clear when reset, but you can also:
```java
MessageLog.getInstance().clear();
```

---

## 🆚 Original UI vs 3D UI

### When to use Original UI (CubeUI)
- ✅ You prefer the flat cross layout
- ✅ You want terminal-based logging
- ✅ You need the smallest window size
- ✅ You're debugging with terminal output

### When to use 3D UI (CubeUI3D)
- ✅ You want better visualization
- ✅ You prefer on-screen messages
- ✅ You want to see 3 faces at once
- ✅ You like modern dark themes
- ✅ You're demonstrating to others

**Both UIs work with the same cube solver!**

---

## 🐛 Troubleshooting

### "Unable to locate Java Runtime"
**Solution:** Install Java JDK 11 or higher
```bash
# Check if Java is installed
java -version

# If not, install OpenJDK
# macOS: brew install openjdk
# Linux: sudo apt install openjdk-11-jdk
```

### "Could not find or load main class Main3D"
**Solution:** Make sure you compiled first
```bash
javac Main3D.java
```

### "java.lang.NoClassDefFoundError: Cube"
**Solution:** Compile all dependencies
```bash
javac *.java
java Main3D
```

### Window doesn't appear
**Solution:** Check if another instance is running
- Press `Q` to quit any running instances
- Try running again

---

## 📚 More Information

- **Full Documentation:** README_3D_UI.md
- **File Details:** AI_GENERATED_FILES_SUMMARY.md
- **Original Project:** README.md

---

## 💡 Tips & Tricks

1. **Watch the solver work:** Messages update in real-time
2. **Practice your speedsolving:** Use manual moves (keys)
3. **Compare solutions:** Run solver multiple times
4. **Learn the algorithm:** Watch the move sequence in messages
5. **Verify correctness:** Compare 3D view with flat view

---

## ✅ Success Checklist

- [ ] Compiled without errors
- [ ] Window opens and shows 3D cube
- [ ] Can perform manual moves
- [ ] Message log shows info messages
- [ ] Pressing SPACE starts solver
- [ ] Solver messages appear on screen
- [ ] Cube gets solved
- [ ] Solution appears in message log

---

## 🎓 Example Session

```bash
$ javac Main3D.java
$ java Main3D

[Window Opens]
- Cube appears in 3D isometric view
- Message log shows: "Cube scrambled and ready!"
- Press SPACE

[Solver Starts]
- Messages: "Starting to solve the cube..."
- Messages: "Phase 1 complete..."
- Messages: "Phase 2 complete..."
- Messages: "Time: 2 sec, 431 ms"
- Messages: "States explored: 14.2K"
- Messages: "Solution: F' U R2 D' R' U' F D R2 B2"

[Cube is Solved!]
- All faces show single colors
- Message log shows success
```

---

**Ready to go! Launch Main3D and start solving! 🎉**

*AI Generated Quick Start Guide - January 22, 2026*
