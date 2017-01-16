# bomberblind
A bomberman alike game.

## Commands
- Use the arrow keys to move the bomberman.
- Use key 'B' to put bombs.
- Use key 'ESC' to quit.

## On master:
* Randomly create the map.
  * The number/proportion of elements is fully settable (.properties).
* Bomberman and map Scrolling.
  * The bomberman is centered to the screen and the map is scrolled until a border is reached.
  * When a border is reached, scrolling of the map is stopped and the bomberman starts moving.
* Bombs and flames handling.
  * Bomberman dies when hitting by a flame.
  * Bomb does explose when hiting by a flame.
* Randomly create enemies.
  * The number/proportion of enemies is fully settable (.properties).
* Walking enemies handling.
  * A walking enemy dies when hitting by a flame.
  * A walking enemy changes of direction when reaching an obstacle or another enemy.
* Breaking enemies handling.
  * Same as walking enemy, but breaks obstacles (and then, changes of direction) when reaching mutables.
* Fullscreen handling.
  * Fullscreen mode is available for the following screen format:
    * 4/3 (1024*768, 16bits, 60hz)
    * 16/9 (1280*720, 16bits, 60hz)
    * 16/10 (1280*800, 16bits, 60hz)
  * If fullscreen is not available, set the window mode with a resoltion of 1024*768.