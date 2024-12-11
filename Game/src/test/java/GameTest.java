import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void testYAML() {
        LoadYAML yl = new LoadYAML();
        Room room1 = yl.rooms.get("Outside");
        assertEquals(room1.name, "Outside");
    }

    @Test
    public void testItems() {
        LoadYAML yl = new LoadYAML();
        Room room1 = yl.rooms.get("Outside");
        assertTrue(room1.contents.contains(yl.items.get("hammer")));
    }

    @Test
    public void testDoors() {
        LoadYAML yl = new LoadYAML();
        Room room1 = yl.rooms.get("Outside");
        assertEquals(room1.doors.get("green"), "Entrance");
    }

}
