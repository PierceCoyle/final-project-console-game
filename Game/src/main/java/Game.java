import java.util.*;

public class Game {

    static String name;
    static int choice;
    static String itemp;

    // helper function for printing
    public static void printSlow(String toPrint) {
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
        boolean isMonstersTurn = false;

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
            isMonstersTurn = false;
            System.out.println("");
            System.out.println("What do you want to do next?");
            System.out.println("[1]: Look around the room.");
            System.out.println("[2]: Move to a new room.");
            System.out.println("[3]: Pick up an object from the room.");
            System.out.println("[4]: Examine my inventory.");
            System.out.println("[5]: Use an object from my inventory.");

            if (state.room.name.equals("Server Room")) {
                System.out.println("[6]: Enter the console.");
            }
            try {
            choice = myObj.nextInt();
            } catch (Exception e) {
                printSlow("Unknown input, try again");
                myObj.nextLine();
                continue;
            }
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
                            throw new Exception("door does not exist");
                        }
                        if (state.rooms.get(rtemp).locked == true) {
                            printSlow("This door is boarded up...");
                            break;
                        }
                        isMonstersTurn = true;
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
                        if (itemp.equals("book")) {
                            String message = """
                                                You glance at the book. It's titled \"A Proof of the Riemann Hypothesis.\"
                                                Math isn't really your thing, so you leave it behind.
                                            """;
                            printSlow(message);
                            break;
                        }
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
                            printSlow(item.use);
                            switch (item.action) {
                                case "drop":
                                    state.inventory.remove(item);
                                    state.room.contents.add(item);
                                    state.rooms.put(state.room.name, state.room);
                                    break;
                                case "unlock":
                                    Key key = (Key) item;
                                    if (state.room.doors.containsKey(key.door)) {
                                        state.unlock(key);
                                        if (state.isAIDeleted == false) {
                                            printSlow("Your noise seems to have awoken something... \nYou hear a growling in the distance.");
                                        }
                                    } else {
                                        printSlow("Theres nothing to use this item on...");
                                    }
                                    break;
                                case "heal":
                                    Healing healing = (Healing) item;
                                    state.health += healing.heal();
                                    break;
                                case "plant":
                                    Plant plant = (Plant) item;
                                    state.happiness += plant.increaseHappiness();
                                    break;
                                case "swing":
                                    Weapon weapon = (Weapon) item;  
                                    weapon.swing(state);
                                    break;
                                case "track":
                                    if (state.isAIDeleted == true || state.isBoarded == true) {
                                        printSlow("Nothing seems to have happened...");
                                        break;
                                    }
                                    Defense tracker = (Defense) item;  
                                    tracker.track(state);
                                    break;
                                case "stop":
                                    if (state.isAIDeleted == true || state.isBoarded == true) {
                                        printSlow("Nothing seems to have happened...");
                                        break;
                                    }
                                    Defense stop = (Defense) item; 
                                    stop.stop(state);
                                    break;
                                case "repel":
                                    if (state.isAIDeleted == true || state.isBoarded == true) {
                                        printSlow("Nothing seems to have happened...");
                                        break;
                                    }
                                    Defense repel = (Defense) item;  
                                    repel.repel(state);
                                    break;
                                case "eat":                
                                    Animal animal = (Animal) item; 
                                    animal.eat(state);
                                    break;
                            }
                        }
                        else {
                            printSlow("Unknown item.");
                        }
                    } catch (Exception e) {
                        printSlow("Unknown item.");
                    }
                    break;
                    case 6:
                        if (state.room.name.equals("Server Room")) {
                                gameConsole(state, myObj);
                        } else {
                            printSlow("Unidentified input, try again?");
                    }
                    break;
                default:
                    printSlow("Unidentified input, try again?");
            }

            if (state.isBoarded == false && state.isAIDeleted == false && isMonstersTurn == true) {
                    aiMove(state);
                
            }

            String update = state.update();
            printSlow(update);
        }
    }

    public static void gameConsole(GameState state, Scanner myObj) {
        List<String> files = new ArrayList<>(7); //List of "files"
        files.add("Game.java");
        files.add("GameState.java");
        files.add("Room.java");
        files.add("Item.java");
        files.add("Weapon.java");
        files.add("Animal.java");
        files.add("LoadYAML.java");
        files.add("Monster.java");
        int consoleChoice;
        boolean inConsole = true;
        while (inConsole) {
            printSlow("You are in the console.");
            System.out.println("What do you want to do next?");
            System.out.println("[1]: View files.");
            System.out.println("[2]: Delete a file.");
            System.out.println("[3]: Exit.");
            try {
                consoleChoice = myObj.nextInt();
                myObj.nextLine();
            } catch (Exception e) {
                myObj.nextLine();
                printSlow("Unidentified input, try again?");
                continue;
            }
            switch (consoleChoice) {
                case 1:
                printSlow("Files:");
                    for (String s : files) {
                        printSlow("- " + s);
                    }
                    break;
                case 2:
                    printSlow("Which file would you like to delete?");
                    String fileChoice = myObj.nextLine();
    
                    if (files.contains(fileChoice)) {
                        if (fileChoice.equals("Monster.java")) {
                            state.isAIDeleted = true;
                            files.remove(fileChoice);
                            printSlow("Monster.java successfully deleted.");
                        } else {
                            //Fake crash
                            //throw new RuntimeException("ERROR: Deleting " + fileChoice + " has caused a critical failure!");
                            files.remove(fileChoice);
                            printSlow("ERROR: Deleting " + fileChoice + " has caused a critical failure!");
                        }
                    } else {
                        printSlow("ERROR: File not found.");
                    }
                    break;
                case 3:
                    inConsole = false;
                    break;
                default:
                    printSlow("Invalid input. Try again.");
            }
        }
    }
    

    
    public static void aiMove(GameState state) {
        if (state.isAIStopped) {
            state.isAIStopped = false;
            return;
        }

        Room aiRoom = state.aiTracker.poll();
        if (aiRoom == state.room) {
            System.out.println("The Monster has caught the player in " + state.room.name);
            state.isCaught = true;
            return;
        }
        Room nextRoom = state.aiTracker.peekFirst();

        if (nextRoom == state.room) {
            System.out.println("The Monster has caught the player in " + nextRoom.name);
            state.isCaught = true;
        }

    }
}
