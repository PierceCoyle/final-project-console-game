import java.util.*;

public class Game {

    static String name;
    static int choice;
    static String itemp;

    // helper function for printing
    private static void printSlow(String toPrint) {
        char[] chars = toPrint.toCharArray();
        for (int i=0; i < chars.length; i++) {
            System.out.print(chars[i]);
            try { Thread.sleep(25);} 
            catch (InterruptedException e) {Thread.currentThread().interrupt();}
        }
        System.out.println("");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {

        // only instantiate once
        Scanner myObj = new Scanner(System.in);

        System.out.println("What is your name?");
        name = myObj.nextLine();
        // init game state
        GameState state = new GameState(name);
        state.isBoarded = true;

        // beginning flavor text
        /**
        printSlow("Welcome, "+name+".");
        System.out.println("");
        printSlow("You've been studying in the library for hours and decide to take a break by walking around.");
        System.out.println("");
        printSlow("You go downstairs into the basement, find an archive room, and get distracted by an old book describing the first version of Java (\'The Java Tutorial\' by Mary Campione and Kathy Walrath, published in 1997).");
        System.out.println("");
        printSlow("After reading for a while, you look up and notice that the room looks... different. The lighting seems a little dimmer, the room smells of cigarettes, and you could have sworn the carpet was a different pattern when you first walked into this room.");
        */
        while (!state.finished) {
            System.out.println("");
            System.out.println("What do you want to do next?");
            System.out.println("[1]: Look around the room.");
            System.out.println("[2]: Move to a new room.");
            System.out.println("[3]: Pick up an object from the room.");
            System.out.println("[4]: Examine my inventory.");
            System.out.println("[5]: Use an object from my inventory.");

            //If in console room
            System.out.println("[6]:");

            choice = myObj.nextInt();
            myObj.nextLine(); // consume newline from above

            //maybe if choice = 6 and in console room, then call console menu
            switch (choice) {
                case 1:
                    printSlow("You can see the following items:");
                    for (Item c : state.room.contents) printSlow(c.name);
                    printSlow("You also notice that this room has doors:");
                    for (String c : state.room.doors.keySet()) printSlow(c);
                    break;
                case 2:
                    printSlow("Which door?");
                    String door = myObj.nextLine();

                    try {
                        String rtemp = state.room.doors.get(door);
                        if (rtemp == null) {
                            System.out.println("room is currently" + state.room.name); //temp maybe
                            throw new Exception("door does not exist");
                        }
                        if (door.equals("yellow") && state.isBoarded == true) {
                            printSlow("This door is boarded up...");
                            break;
                        }
                        state.room = state.rooms.get(rtemp);
                        printSlow("You step through the " + door + " door. You realize this room is the " + state.room.name + ".");
                    } catch (Exception e) {
                        printSlow("Unknown door.");
                    }
                    break;
                case 3:
                    printSlow("Which item?");
                    itemp = myObj.nextLine();
                    try {
                        Item item = state.items.get(itemp);
                        state.room.contents.remove(item);
                        state.rooms.put(state.room.name, state.room);
                        state.inventory.add(item);
                        printSlow("You pick up the " + item.name + ". " + item.desc + ".");
                    } catch (Exception e) {
                        printSlow("Unknown item.");
                    }
                    break;
                case 4:
                    printSlow("Your inventory:");
                    printSlow(state.inventory.toString());
                    break;
                case 5:
                    printSlow("Which item?");
                    itemp = myObj.nextLine();
                    try {
                        Item item = state.items.get(itemp);
                        if (state.inventory.contains(item)) {
                            item.use();
                            //maybe change logic to check item type here, if its a key then use the key logic.
                            if (itemp.equals("crowbar") && state.room.name.equals("Room 1")) { // Skip item usage in case of unlocking door
                                state.inventory.remove(item);
                                state.room.contents.add(item);
                                //state.rooms.put(state.room.name, state.room);
                                break;
                            }
                            printSlow(item.use);
                            if (item.action.equals("drop")) {
                                state.inventory.remove(item);
                                state.room.contents.add(item);
                                state.rooms.put(state.room.name, state.room);
                            }
                        }
                        else {
                            printSlow("Unknown item.");
                        }
                    } catch (Exception e) {
                        printSlow("Unknown item.");
                    }
                    break;
                default:
                    printSlow("Unidentified input, try again?");
            }

            String update = state.update();
            printSlow(update);
        }
        printSlow("You win!");
    }

    public static void gameConsole(GameState state, Scanner myObj) throws Exception {
        ArrayList<String> files = new ArrayList<>(7); //List of files 
        files.add("Game.java");
        files.add("GameState.java");
        files.add("Room.java");
        files.add("Item.java");
        files.add("Weapon.java");
        files.add("Animal.java");
        files.add("LoadYAML.java");
        files.add("Monster.java");

        boolean inConsole = true;
        while (inConsole) {
            System.out.println("You are in the console");
            System.out.println("What do you want to do next?");
            System.out.println("[1]: View files.");
            System.out.println("[2]: Delete a file.");
            System.out.println("[3]: Exit.");

            choice = myObj.nextInt();
            myObj.nextLine(); // consume newline from above

            switch (choice) {
                case 1:
                    for (String s : files) {
                        printSlow(s);
                    }
                    break;
                case 2:
                    String fileChoice = myObj.nextLine();
                    if (files.contains(fileChoice) && fileChoice.equals("Monster.java")) {
                        state.isAIDeleted = true;
                        files.remove(fileChoice);
                    } else if (files.contains(fileChoice)) {
                        Exception exception = new Exception("ERROR CANNOT FIND " + fileChoice);
                        throw exception;
                    }
                    break;
                case 3: 
                    inConsole = false;
                    break;
                default:
                    printSlow("Unidentified input, try again?");
            }
        }
    }

    public static void aiMove(GameState state) {
        if (state.isAIDeleted == true) {
            return;
        }
        //Perform graph search
        //Store array list with rooms between player and ai
        //Remove from array list each time player moves towards ai or ai gets a turn
        //Use Deque to remove from front/back depending
        //if arraylist is empty then player has been caught
        state.aiTracker.poll();
    }
}
/*
 * Need to add one item for each pre-implemented class
 * Need to add 3 items for a new sub class
 * Need to implement ai graph search and management
 * Need to implement death
 * Need to implement two new tests
 * A poor innocent slug, How could you!
 */