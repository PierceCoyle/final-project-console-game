import java.util.Random;
import java.util.List;

public class Healing extends Item {
    private int amount;

    public Healing(String name, String type, String desc, String use, String act, int amount) {
        super(name, type, desc, use, act);
        this.amount = amount;
    }

    public int heal() {
        return amount;
    }

}
