# Slash

### **Android Launcher** -> The activity where everything is launched.

## Packages and Classes Structure (from core project)

### Package **slash**
Package for screens and anything related to them.
* **Slash** -> Class where the default screen is launched.
* **Resizer** -> Abstract class to handle the camera's dimensions in each of the Screens.
* **MenuScreen** -> Default screen when opening the app.
* **GameScreen** -> Screen where the game is played.
* **GameOverScreen** -> Screen shown if the game ends.
* **EasterEggScreen** -> Screen containing the hidden animation.


### Package **gameworld**
This is the package where the general part of the game logic is handled and handled.
* **GameRenderer** -> Handles the rendering.
* **GameWorld** -> Handles the physics simulation and game logic.


### Package **gameobjects**
These are the objects we actually see during our game.
* **Ball** -> Ball that wanders around.
* **GameArea** -> "Box" with balls and slasher inside.
* **Slasher** -> The yellow ball that cuts the GameArea to create a new one.
* **Redirecter** -> Purple box that appears when a certain score is reached. It changes nearby ball's movement direction.


### Package **slashhelpers**
This package contains the helpers.
* **InputHandler** -> For being able to receive inputs (used only on GameScreen)
* **Function** -> For using functions (y=m*x+b).
* **Utilities** -> Containts several miscellaneous methods.
