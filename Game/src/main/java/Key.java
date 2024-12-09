import java.util.Random;
import java.util.List;

public class Key extends Item {
    boolean hasBeenUsed;
    String door;


    public Key(String name, String type, String desc, String use, String act, String door) {
        super(name, type, desc, use, act);
        this.door = door;
        hasBeenUsed = false;
    }

    public void unlock() {
        hasBeenUsed = true;
    }

}
