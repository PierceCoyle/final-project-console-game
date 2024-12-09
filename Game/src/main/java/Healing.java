import java.util.Random;
import java.util.List;

public class Healing extends Item {
    int min;
    int max;
    private Random rn;

    public Healing(String name, String type, String desc, String use, String act) {
        super(name, type, desc, use, act);
        rn = new Random();
    }

    // uniformly distributed random number
    public int heal() {
        int var = min + rn.nextInt((max-min) + 1);
        return var;
    }

}
