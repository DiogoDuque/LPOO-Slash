# Slash

## Packages and Classes Structure

### Package **slash**
Package used for generic stuff.
* **Slash** -> Activity where it all starts.
* **GameScreen** -> Class accessed only by *Slash*. Handles typical Activity stuff.


### Package **gameworld**
This is the package where the main part of the game logic is handled.
* **GameRenderer** -> Handles the rendering.
* **GameWorld** -> Handles the physics simulation.


### Package **gameobjects**
These are the objects we actually see during our game.
* **Ball** -> ball that wanders around.
* **GameArea** -> box with all balls.


### Package **slashhelpers**
This package contains the helpers.
* **AssetLoader** -> for loading possible assets, such as textures and sprites.
* **InputHandler** -> for being able to receive inputs.
* **Function** -> for using functions (y=m*x+b).
