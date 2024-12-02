# Final Project: Console Game

## Due Tuesday December 10

## Getting Started

How to build and run a gradle project on the command line for Linux and Mac:

```sh
./gradlew build
./gradlew -q run
```

and on Windows:

```sh
./gradlew.bat build
./gradlew.bat -q run
```

You may also use the
![VS Code extension, Grade for
Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-gradle),
however the game will likely be easier to interact with in the terminal.

## Directory Structure

Game code is located in `Game/src/main/java/`.

Tests are located in `Game/src/test/java/`.

YAML configuration files are located in `Game/config/`.

You shouldn't have to touch any of the Gradle files.

## Requirements and Deliverables

1. Add at least two new rooms in `rooms.yaml`, connected to the existing rooms.
2. Add an ending related to at least one of your new rooms. Endings are
implemented in `GameState.java`.
3. Implement a locked door that can only be opened if a Key is in your inventory.
4. Finish implementing subclasses for Items (Animal, Weapon, etc) instead of reading all
items in as Items.
   - Add and implement actions for all the items. All actions should modify GameState.
5. Add a new subclass of items and at least three corresponding entries in `items.yaml`.
6. Implement two new tests in `Game/src/test/java/GameTest.java`.

## Extra Credit Opportunities

Up to 2.5% extra credit (toward your overall class grade) is available if you complete one of the following options:

- Create a "game player" object that can play your game (hint: you will have to
modify the game interaction loop in `Game.java` since a virtual player cannot
give input on the command line). Implement a search algorithm for your player to
find and print all endings to the game.
- Make a significant change to the storyline or behaviors of the game (example:
add enemies and battling, including the option to "die" if an enemy does too
much damage to you). Run your idea past Prof. Nilles to guarantee it will earn
extra credit.
