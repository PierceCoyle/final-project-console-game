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
        if (room.name.equals("Final Room 11")) {
            finished = true;
            String finaltext = "";
            if (happiness < 5) {
                finaltext =  """
                                You Win sad!
                            """;
            } else {
                finaltext =  """
                                You Win happy!
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
        room = rooms.get("Server Room");
        visited.put(room, true);
        itemTest();
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
        Room aiRoom = rooms.get("Server Room");
        Room playerRoom = room;

        Map<Room, Room> from = new HashMap<>();
        Queue<Room> queue = new LinkedList<>();
        Set<Room> visited = new HashSet<>();

        queue.add(aiRoom);
        visited.add(aiRoom);

        while (!queue.isEmpty()) {
            Room curr = queue.poll();

            for (String door : curr.doors.keySet()) {
                Room neighbor = rooms.get(curr.doors.get(door));

                if (neighbor != null && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    from.put(neighbor, curr);
                    queue.add(neighbor);

                    if (neighbor == playerRoom) {
                        break;
                    }
                }
            }
        }

        Deque<Room> path = new LinkedList<>();
        Room step = playerRoom;

        while (step != null && from.containsKey(step)) {
            path.addFirst(step);
            step = from.get(step);
        }

        if (!path.isEmpty() && path.peekFirst() != aiRoom) {
            path.addFirst(aiRoom);
        }

        aiTracker = path;
    }

    public void itemTest() {
        inventory.add(items.get("hammer"));
        inventory.add(items.get("glowing slug"));
        inventory.add(items.get("crowbar"));
        inventory.add(items.get("plant"));
        inventory.add(items.get("healing"));
        inventory.add(items.get("poster"));
        inventory.add(items.get("book"));
        inventory.add(items.get("tracker"));
        inventory.add(items.get("time stop"));
        inventory.add(items.get("bullhorn"));
    }
}
