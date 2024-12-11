# Final Project: Console Game

## Due Tuesday December 10

Notes
Bugs:
   - Entering a non-integer in the menu.
   - Entering an incorrect room name sets state.room to null, causing it to try to initialize contents from null.
   - Can pick up items that aren't in the room.

Fixes:
   - Placed a try catch around nextInt.
   - I check if rtemp is null before setting state.room.
   - I check if the item is in the room first before allowing the player to pick it up.

## Requirements and Deliverables

1. Add at least two new rooms in `rooms.yaml`, connected to the existing rooms.
   - I have a total of 11 rooms.
2. Add an ending related to at least one of your new rooms. Endings are
implemented in `GameState.java`.
   - I have two possible endings depending on your happiness value when you enter the final room
3. Implement a locked door that can only be opened if a Key is in your inventory.
   - I have a locked door which can be opened by using the crowbar Key item when in the same room.
4. Finish implementing subclasses for Items (Animal, Weapon, etc) instead of reading all
items in as Items.
   - I load in all items as their specific class in loadYAML.
5. Add and implement actions for all the items. All actions should modify GameState.
   - Each item has at least one action.
   - Weapon.java: attack()
   - Plant.java: inspect()
   - Animal.java: eat(), recieveAttack()
   - Key.java: unlock()
   - Healing.java: heal()

6. Add a new subclass of items and at least three corresponding entries in `items.yaml`.
   - There is a new subclass of items titles "Defense". These items have actions which help you track and stay away from the game monster.
   - The tracker tells you how far away the monster is. 
   - The time stop stops the monster for one turn. 
   - The bullhorn returns the monster to the spawn.
7. Implement two new tests in `Game/src/test/java/GameTest.java`.
   - There are two new tests, one which tests the starting room's item, and one which tests the starting room's connected door.

## Extra Credit Opportunities

Up to 2.5% extra credit (toward your overall class grade) is available if you complete one of the following options:

1. Create a "game player" object that can play your game (hint: you will have to
modify the game interaction loop in `Game.java` since a virtual player cannot
give input on the command line). Implement a search algorithm for your player to
find and print all endings to the game.
   - I have elements of the graph search in my monster, however this does not find or print any endings.

2. Make a significant change to the storyline or behaviors of the game (example:
add enemies and battling, including the option to "die" if an enemy does too
much damage to you). Run your idea past Prof. Nilles to guarantee it will earn
extra credit.
   - My most significant change is the presence of a monster that chases you after you unlock the key door.
   - This monster introduces the option to die if it catches you. You also have the option to die by other means.
   - Additionally, I have a console in the server room. This allows you to delete the monster file and stop it from spawning.
   - Deleting any other files crashes the game. 
