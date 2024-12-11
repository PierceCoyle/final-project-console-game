import java.util.Random;

public class Weapon extends Item {
    int min;
    int max;
    private Random rn;

    public Weapon(String name, String type, String desc, String use, String act, int min_damage, int max_damage) {
        super(name, type, desc, use, act);
        min = min_damage;
        max = max_damage;
        rn = new Random();
    }

    public void swing(GameState state) {
        int damage = min + rn.nextInt((max - min) + 1);

        boolean hit = false;
        for (Item item : state.room.contents) { //search room for animals to attack
            if (item.type.equals(ItemType.toType("Animal"))) {
                Animal animal = (Animal) item;
                hit = true;
                int result = animal.recieveAttack(damage, state); //returns 1 if animal dies
                if (result == 1) {
                    state.room.contents.remove(item);
                break;
                }
            }
        }
        if (hit == false) {
            Game.printSlow("It slams into the ground.");
        }
    }
}