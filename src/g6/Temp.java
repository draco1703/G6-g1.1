package g6;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.Random;
import g6.placers.ShipPlacer;
import g6.shooters.Shooter;

public class Temp implements BattleshipsPlayer {

    private final Random rnd;
    private Shooter shooter;
    private ShipPlacer placer;
    private int round;

    public Temp() {
        rnd = new Random();
        shooter = null;
        placer = null;
    }

    @Override
        public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        shooter = new Shooter(sizeX, sizeY, rnd);
        placer = new ShipPlacer(sizeX, sizeY, rnd);
    }

    @Override
    public void startRound(int round) {
        this.round = round;
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        shooter.newRound(round);
        placer.placeShips(fleet, board);
    }

    @Override
    public void incoming(Position pos) {
        placer.incoming(pos);
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        return shooter.getFireCoordinates(enemyShips);
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        shooter.hitFeedBack(hit, enemyShips);
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
//  empy
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
//  empty
    }
}
