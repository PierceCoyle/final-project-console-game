public class Defense extends Item {

    public Defense(String name, String type, String desc, String usetext, String useaction) {
        super(name, type, desc, usetext, useaction);
    }

    public void track(GameState state) { //Rooms between monster and player
        if (state.isBoarded == false && state.isAIDeleted == false) {
            int distance = state.graphSearch().size() - 1;  
            Game.printSlow("It seems to tell you that the monster is " + distance + " room(s) away.");
        }
    }

    public void stop(GameState state) { //Stops monster for one turn
        state.isAIStopped = true;
        Game.printSlow("The growling seems to have stopped... for now.");

    }

    public void repel(GameState state) { //Returns monster to beginning
        state.initializeAI();
        Game.printSlow("The monster seems to have returned to where it started...");
    }
}
