public class Animal extends Item {
    public int health;
    public boolean isAlive;
    public int food;

    public Animal(String name, String type, String desc, String use, String act, int health, int food) {
        super(name, type, desc, use, act);
        isAlive = true;
        this.health = health;
        this.food = food;
    }

    public int recieveAttack(int damage, GameState state) {
        health -= damage;
        if (health <= 0) {
            Game.printSlow("This " + this.name + " has been squashed...");
            state.happiness--;
            state.room.contents.add(spawnSlug());
            Game.printSlow("A new slug crawls out from a test tube!");
            return 1;
        } else {
            Game.printSlow("You hit the " + this.name + "! It seems to be moving slower...");
            return 0;
        }
    }

    public int eat(GameState state) {
        state.health += food;
        if (state.health <= 0) {
            Game.printSlow("You feel your strength fading as the toxic " + this.name + " takes its toll...");
            state.finished = true;
            return food;
        }
        return food;
    }

    public static Animal spawnSlug() {
        return new Animal(
            "glowing slug", 
            "Animal", 
            "A bright purple slug, oozing a thick liquid", 
            "You place the slug gently on the floor. It croaks contentedly.", 
            "attack", 
            2, 
            -2); 
    }
}
