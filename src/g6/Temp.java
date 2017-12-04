package g6;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

public class Temp implements PlayerFactory<BattleshipsPlayer> {

    public Temp() {
    }

    @Override
    public BattleshipsPlayer getNewInstance() {
        return new G6();
    }

    @Override
    public String getID() {
        return "G6";
    }

    @Override
    public String getName() {
        return "G6";
    }

    @Override
    public String[] getAuthors() {
        String[] res = {"Filip Filipovic, Nicolai Rosenvinge, Jonas Grønbek"};
                 
        return res;
    }
}