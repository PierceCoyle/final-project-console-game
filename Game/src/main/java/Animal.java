import java.util.Random;
import java.util.List;

public class Animal extends Item {
    public int health;
    public boolean isAlive;
    private Random rn;

    public Animal(String name, List<String> type, String desc, String use, String act, int min_damage, int max_damage) {
        super(name, type, desc, use, act);
        isAlive = true;
        health = rn.nextInt(2)+1;
    }

    // uniformly distributed random number
    public void recieveAttack(int damage) {
        health -= damage;
        health = 0 ? System.out.printline("this slug has been squashed") : System.out.println("You hit the slug! it seems to be moving slower..."); return;

    }
}
