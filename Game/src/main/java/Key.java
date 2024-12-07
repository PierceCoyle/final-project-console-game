import java.util.Random;
import java.util.List;

public class Key extends Item {
    boolean hasBeenUsed;


    public Key(String name, List<String> types, String desc, String use, String act, int min_damage, int max_damage) {
        super(name, types, desc, use, act);

        hasBeenUsed = false;
    }

    // uniformly distributed random number
    public void unlock() {
        hasBeenUsed = true;
    }

}
