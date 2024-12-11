import java.util.Random;
import java.util.List;

public class Plant extends Item {
    private Random rn;

    public Plant(String name, String type, String desc, String use, String act) {
        super(name, type, desc, use, act);
        rn = new Random();
    }

    public int increaseHappiness() {
        int var = rn.nextInt(2) + 1;
        return var;
    }
}
