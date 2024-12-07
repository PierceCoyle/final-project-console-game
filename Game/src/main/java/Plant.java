import java.util.Random;
import java.util.List;

public class Plant extends Item {
    int min;
    int max;
    private Random rn;

    public Plant(String name, List<String> types, String desc, String use, String act, int min_damage, int max_damage) {
        super(name, types, desc, use, act);
        min = min_damage;
        max = max_damage;
        rn = new Random();
    }

    public int inspect() {
        return 5;
    }

    // uniformly distributed random number
    public int attack() {
        int var = min + rn.nextInt((max-min) + 1);
        return var;
    }

}
