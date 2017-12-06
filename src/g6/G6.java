package g6;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

public class G6 implements PlayerFactory<BattleshipsPlayer> {

    public G6() {
    }

    @Override
    public BattleshipsPlayer getNewInstance() {
        return new Temp();
    }

    @Override
    public String getID() {
        return "G6ny";
    }

    @Override
    public String getName() {
        return "Den nye";
    }

    @Override
    public String[] getAuthors() {
        String[] res = {"Filip Filipovic, Nicolai Rosenvinge, Jonas Grønbek"};
        return res;
    }
}
