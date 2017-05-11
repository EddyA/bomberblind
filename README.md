# bomberblind
A Bomberman-like game.


## Commands

- Use the arrow keys to move the bomber.
- Reach the exit an press (Q) to complete the stage.
- Press (B) to put bombs.
- Press (ENTER) to start the stage.
- Press (ESC) to quit the game.

## Credits
- Game by **Eddy ALBERT** (eddy.albert@gmail.com).
- Map sprites from *The Legend of Zelda: A link to the Past (Nintendo)*, ripped & updated by Eddy ALBERT.
- Bomber sprites from *Saturn Bomberman (Hudson Soft)*, ripped by Ragey on www.spriters-resource.com.
- Enemies sprites from *The Legend of Zelda: A link to the Past (Nintendo)*, ripped & updated by Eddy ALBERT.
- Ascii sprites from *Metal Slug Mobile 3 (SNK/Playmore)*, ripped by Maneko on www.spritedatabase.net.

## In a nutshell
* Map.
  * The map is randomly created.
  * The map size & the number/proportion of map elements is fully settable (.properties).
  * **Finding path algorithm**:
    * A finding path algorithm (A*) is applied to assert that there does exist a path between the entrance and the exit.
    * The map is regenerated (with a maximum number of 20 tries) until a viable map is generated.
    * If no viable map has been generated, the program does exit throwing an exception. 
  
* Bomber.
  * The bomber dies when hitting by a flame or an enemy.
  * When dying, all the collected bonus are randomly dropped on the map.
    
* Bonus.
    * The bonus are randomly placed.
    * The number of bonus is fully settable (.properties).
    * There are 4 type of bonus:
        * **bomb** (increase the number of bombs the bomber is able to put simultaneously).
        * **flame** (increase the size of the bomb's blast the bomber is dropping).
        * **heart** (increase the number of remaining lifes of the bomber).
        * **roller** (increase the speed of the bomber).
        
* Enemies.
    * The enemies are randomly placed.
    * The number of enemies is fully settable (.properties).
    * There are 2 types of enemy:
        * **Walking enemies**.
            * A walking enemy dies when hitting by a flame.
            * A walking enemy changes of direction when reaching an obstacle or another enemy.
        * **Breaking enemies**.
            * Same as walking enemy, but able to break mutable obstacles.
            * When breaking a mutable obstacle, the enemy changes of direction.

* Scene.
    * There are 3 types of map elements:
        * **pathway** (can be crossed by characters).
        * **mutable obstacle** (cannot be crossed by characters, can be knocked through by a bomb and become a pathway)
        * **immutable obstacle** (cannot be crossed by characters, cannot by knocked through)
    * **Flying nomad**.
        * A flying nomad is a scenary element (it does not interact with the other sprites) crossing the map (e.g. birds).
        * Flying nomads are dynamically & randomly created during the game.
        * The creation frequency is fully settable (.properties).

* GUI.
    * **Scrolling**.
        * The bomber is centered to the screen and the map is scrolled until a border is reached.
        * When a border is reached, scrolling of the map is stopped and the bomber starts moving.

    * **Fullscreen**.
      * Fullscreen mode is available for the following screen format:
          * 4/3 (1024*768, 16bits, 60hz)
          * 16/9 (1280*720, 16bits, 60hz)
          * 16/10 (1280*800, 16bits, 60hz)
      * If fullscreen is not available, set the window mode with a resoltion of 1024*768.