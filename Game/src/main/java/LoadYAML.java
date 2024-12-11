//package Game.src.main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import org.yaml.snakeyaml.Yaml;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class LoadYAML {

    String fname;
    HashMap<String, Object> data;
    HashMap<String, Room> rooms = new HashMap<>();
    HashMap<String, Item> items = new HashMap<>();

    // load room data from yaml file
    // could do this more cleverly with packaged class definitions
    // Something like this:
    //InputStream stream = new FileInputStream(fname);
    //Room room = (new Yaml(new Constructor(Room.class))).load(stream);
    public HashMap<String,Room> loadRooms() {
        data = load("rooms.yaml");
        for (String name : data.keySet()) {
            List<Item> contents = new ArrayList<>();
            Map<String, Object> inRoom = (HashMap) data.get(name);
            List<String> contemps = (ArrayList) inRoom.get("contents");
            for (String it : contemps) contents.add(items.get(it));
            Map<String, String> doors = (HashMap) inRoom.get("doors");
            boolean locked = (boolean) inRoom.get("locked");
            rooms.put(name, new Room(name, contents, doors, locked));
        }
        return rooms;
    }

    public HashMap<String,Item> loadItems() {
        data = load("items.yaml");
        for (String name : data.keySet()) {
            Map<String, Object> properties = (HashMap) data.get(name);
            String desc = (String) properties.get("description");
            Map<String, Object> use = (HashMap) properties.get("use");
            String usetext = (String) use.get("text");
            String useaction = (String) use.get("action");
            List<String> types = (ArrayList) properties.get("type");
            String type = types.get(0);
            switch (type) {
                case "Weapon":
                    int minW = (int) properties.get("min-damage");
                    int maxW = (int) properties.get("max-damage");
                    items.put(name , new Weapon(name, type, desc, usetext, useaction, minW, maxW));
                    break;
                case "Animal":
                    int health = (int) properties.get("health");
                    int food = (int) properties.get("food");
                    items.put(name , new Animal(name, type, desc, usetext, useaction, health, food));
                    break;
                case "Key":
                    String door = (String) properties.get("door");
                    items.put(name , new Key(name, type, desc, usetext, useaction, door));
                    break;
                case "Healing":
                    int amount = (int) properties.get("amount");
                    items.put(name , new Healing(name, type, desc, usetext, useaction, amount));
                    break;
                case "Plant":
                    items.put(name , new Plant(name, type, desc, usetext, useaction));
                    break;
                case "Item":
                    items.put(name , new Item(name, type, desc, usetext, useaction));
                    break;
                case "Defense":
                    items.put(name , new Defense(name, type, desc, usetext, useaction));
                    break;
            }
        }
        return items;
    }

    public HashMap<String, Object> load(String fname) {
            Yaml yaml = new Yaml();
            File file = new File("./config/"+fname);
            try {
                FileInputStream inputStream = new FileInputStream(file);
                data = yaml.load(inputStream);
            } catch (FileNotFoundException e) {System.out.println("Couldn't find file");}
        return data;
    }

    public LoadYAML() {
        items = loadItems();
        rooms = loadRooms();
    }
}
