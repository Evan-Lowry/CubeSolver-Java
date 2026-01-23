# AI Generated 3D UI for Rubik's Cube Solver

**⚠️ ALL FILES IN THIS DOCUMENT ARE AI GENERATED ⚠️**

## Overview

This is an alternative UI for the Rubik's Cube Solver that provides:
- **Semi-flat 3D isometric view** of the cube
- **On-screen message log** instead of terminal output
- **Better visual feedback** during solving

## AI Generated Files

The following files were created by AI and are clearly marked:

1. **MessageLog.java** - Singleton class to capture and display messages
2. **Drawing3D.java** - Handles isometric 3D rendering of the cube
3. **CubeUI3D.java** - Main UI panel for 3D view
4. **KeyHandler3D.java** - Input handler for 3D UI
5. **Main3D.java** - Alternative main class to launch 3D UI
6. **README_3D_UI.md** - This documentation file

## How to Use

### Option 1: Run the 3D UI Launcher
```bash
javac Main3D.java
java Main3D
```

### Option 2: Modify Main.java
Replace the line in `Main.java`:
```java
gamePanel = new CubeUI();
```
with:
```java
gamePanel3D = new CubeUI3D();
```

## Features

### 3D Isometric View
- Shows the cube from an angled perspective
- Visible faces: Top, Front, Right, Left, Back, Bottom
- Uses proper depth ordering for realistic appearance
- Color-coded facelets matching standard Rubik's Cube colors:
  - White (top)
  - Yellow (bottom)
  - Green (front)
  - Blue (back)
  - Red (right)
  - Orange (left)

### Message Log Panel
- Displays up to 20 recent messages
- Color-coded message types:
  - **Blue** - Info messages
  - **Green** - Success messages
  - **Red** - Error messages
  - **Yellow** - Solver messages
- Auto-scrolls to show most recent messages

### Controls Panel
- On-screen display of all keyboard controls
- No need to remember keybindings

## Controls

Same as the original UI:

| Key | Action |
|-----|--------|
| SPACE | Solve the cube |
| Q | Quit |
| R | Reset cube |
| 1-6 | Double moves (U2, D2, R2, L2, F2, B2) |
| J | U (Up clockwise) |
| F | U' (Up counter-clockwise) |
| H | F (Front clockwise) |
| G | F' (Front counter-clockwise) |
| I | R (Right clockwise) |
| K | R' (Right counter-clockwise) |
| D | L (Left clockwise) |
| E | L' (Left counter-clockwise) |
| S | D (Down clockwise) |
| L | D' (Down counter-clockwise) |
| W | B (Back clockwise) |
| O | B' (Back counter-clockwise) |

## Technical Details

### Isometric Projection
- Uses 30-degree angle for isometric effect
- Converts 3D cube coordinates to 2D screen space
- Maintains proper face visibility and depth ordering

### Message System
- Messages automatically route to on-screen display
- No terminal output required
- Messages persist for easy review

### Performance
- Double-buffered rendering for smooth graphics
- Anti-aliasing enabled for clean visuals
- Efficient polygon drawing

## Original Code Preservation

**Important:** This is an ALTERNATIVE UI. The original files are completely preserved:
- `CubeUI.java` - Original UI (unchanged)
- `Drawing.java` - Original drawing code (unchanged)
- `Main.java` - Original main class (unchanged, with added solve3D method)
- `KeyHandler.java` - Original input handler (unchanged)

You can still use the original flat UI by running `Main.java` instead of `Main3D.java`.

## Notes

- All AI-generated code is clearly marked with header comments
- The 3D view is "semi-flat" - it's an isometric projection, not full 3D rotation
- Window size: 900x600 pixels (larger to accommodate message panel)
- Messages are stored in memory (limited to 20 most recent)

## Future Enhancements (Not Implemented)

Potential improvements that could be added:
- Cube rotation animation
- Animated solving sequence
- Customizable color schemes
- Export message log to file
- Zoom in/out functionality
- Different viewing angles

---

*Created: January 22, 2026*  
*Purpose: Alternative visualization for Rubik's Cube solver*  
*Status: AI Generated - Complete*
