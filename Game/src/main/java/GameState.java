import java.util.*;

// Tracks global game state
// Useful for implementing state-based behavior (ex: see something new on second visit to room)

public class GameState {
    HashMap<Room, Boolean> visited = new HashMap<Room, Boolean>();
    String name;
    boolean finished;
    boolean isBoarded;
    boolean isAIDeleted;
    Room room;
    List<Item> inventory = new ArrayList<Item>();
    Deque<Room> aiTracker = new LinkedList<Room>();
    Map<String, Room> rooms; // global list of rooms
    Map<String, Item> items; // global list of known items

    // update state and check for winning condition
    public String update() {
        if (aiTracker.size() == 0) {
            String finaltext =  """
                                    You Died!
                                """;
            return finaltext;
        } else if (room == null) {
            return "";
        } else if (room.name.equals("Final Room 9")) {
            finished = true;
            String finaltext =  """
                                    You Win!
                                """;
            return finaltext;
        } else if (room.name.equals("Room 1") && room.contents.contains(items.get("crowbar")) & isBoarded == true) {
            isBoarded = false;
            inventory.remove(items.get("crowbar"));
            return "You use the crowbar to pry off the boards";
        } else 
        return "";
    }

    public GameState(String name) {
        this.name = name;
        finished = false;
        LoadYAML yl = new LoadYAML();
        rooms = yl.rooms;
        items = yl.items;
        room = rooms.get("Starting Room");
        visited.put(room, true);
        inventory.add(items.get("crowbar")); // temp
    }
}
