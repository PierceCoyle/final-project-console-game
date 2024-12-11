import java.util.*;

// Tracks global game state
// Useful for implementing state-based behavior (ex: see something new on second visit to room)

public class GameState {
    HashMap<Room, Boolean> visited = new HashMap<Room, Boolean>();
    String name;
    int health;
    int happiness;
    boolean finished;
    boolean isBoarded;
    boolean isAIDeleted;
    boolean isAIStopped;
    boolean isCaught;
    Room aiRoom;
    Room room;
    List<Item> inventory = new ArrayList<Item>();
    Deque<Room> aiTracker = new LinkedList<Room>();
    Map<String, Room> rooms; // global list of rooms
    Map<String, Item> items; // global list of known items

    // update state and check for winning condition
    public String update() {
        if (isCaught == true) {
            String finaltext =  """
                                    You Died!
                                """;
            finished = true;
            return finaltext;
        }
        if (health <= 0) {
            String finaltext =  """
                                    You Died!
                                """;
            finished = true;
            return finaltext;
        }
        if (room == null) {
            return "";
        } 
        if (room.name.equals("Rooftop")) {
            finished = true;
            String finaltext = "";
            if (happiness <= 3) {
                finaltext =  """
                    The heavy rooftop door slams shut behind you, echoing all around.
                    You inhale the cold, stale air as you look out at the horizon.
                    All around, you see a thick line of trees with only the unknown beyond them.
                    Whatever this journey has come to, you just want to go home.

                    You win?
                """;
            } else {
                finaltext =  """
                    The heavy rooftop door slams shut behind you, echoing all around.
                    You inhale the cold, fresh air as you look out at the horizon.
                    Through a thin line of trees, you see your house, lit up by the warm lights of the neighborhood.
                    You know your journey has come to an end, and it's time to go home.

                    You win!
                """;
            }

            return finaltext;
        } else 
        return "";
    }

    public GameState(String name) {
        this.name = name;
        health = 10;
        happiness = 0;
        finished = false;
        isAIDeleted = false;
        LoadYAML yl = new LoadYAML();
        rooms = yl.rooms;
        items = yl.items;
        room = rooms.get("Outside");
        visited.put(room, true);
        //itemTest();
    }

    public void unlock(Key key) {

        if (rooms.get(room.doors.get(key.door)).locked) {
            rooms.get(room.doors.get(key.door)).unlock();
            key.unlock();
            isBoarded = false;
            
            if (isAIDeleted == false) {
                initializeAI();
            }
            inventory.remove(key);
            
            Game.printSlow("You use the " + key.name + " to unlock the " + key.door + " door.");
        }
    }   

    public void initializeAI() {
        if (isAIDeleted == true) {
            return;
        }

        aiRoom = rooms.get("Server Room");
        aiTracker = graphSearch();

    }

    public void aiMove() {
        if (isAIStopped) { //if timestop is used
            isAIStopped = false;
            return;
        }
        if (aiRoom.name.equals(room.name)) {
            Game.printSlow("The Monster has caught the player in the " + room.name);
            isCaught = true;
            return;
        }

        aiTracker = graphSearch();
        aiTracker.pollFirst(); 
        aiRoom = aiTracker.pollFirst(); //Moves the monster one room

        if (aiRoom == room) {//Capture logic
            Game.printSlow("The Monster has caught the player in the " + room.name);
            isCaught = true;
        } else {
            Game.printSlow("You hear the growling getting closer...");
        }
    }

    public Deque<Room> graphSearch() {
        Room start = aiRoom;  
        Room end = room;
    
        Map<Room, Room> path = new HashMap<>();
        Queue<Room> queue = new LinkedList<>();
        Set<Room> visited = new HashSet<>();
    
        queue.add(start);
        visited.add(start);
    
        boolean foundPlayer = false;
    
        while (!queue.isEmpty() && foundPlayer == false) { //BFS
            Room curr = queue.poll();
    
            for (String door : curr.doors.keySet()) {
                Room neighbor = rooms.get(curr.doors.get(door));
    
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    path.put(neighbor, curr);
                    queue.add(neighbor);
    
                    if (neighbor == end) {
                        foundPlayer = true;
                        break;
                    }
                }
            }
        }
    
        Deque<Room> pathQ = new LinkedList<>();
        Room curr = end;
    
        while (curr != null && path.containsKey(curr)) { //path reconstruction
            pathQ.addFirst(curr);
            curr = path.get(curr);
        }
    
        if (pathQ.peekFirst() != start) {
            pathQ.addFirst(start);
        }

        return pathQ;
    }
    
    

    public void itemTest() {
        inventory.add(items.get("hammer"));
        inventory.add(items.get("glowing slug"));
        inventory.add(items.get("crowbar"));
        inventory.add(items.get("plant"));
        inventory.add(items.get("bandage"));
        inventory.add(items.get("poster"));
        inventory.add(items.get("book"));
        inventory.add(items.get("tracker"));
        inventory.add(items.get("time stop"));
        inventory.add(items.get("bullhorn"));
    }


}
