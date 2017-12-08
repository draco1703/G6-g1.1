package shooter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import maps.BooleanMap;

public class Shooter {

    private int enemyFleetSize;

    private int sizeX;
    private final int sizeY;
    private final Random rnd;
    private Position shot;
    private List<Position> hitMap = new ArrayList<>();

    public Shooter(int sizeX, int sizeY, Random rnd) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.rnd = rnd;
    }

    private int sum(Fleet fleet) {
        int res = 0;
        for (Ship s : fleet) {
            res += s.size();
        }
        return res;
    }

    //skal returnerer en position der repræsenterer hvor vi vil skyde
    public Position getFireCoordinates(Fleet enemyShips) {
        enemyFleetSize = sum(enemyShips);
        shot = hitMap.get(rnd.nextInt(hitMap.size()));
        hitMap.remove(shot);
        return shot;
    }
//finder ud af om der mangler et skib

    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        int newSize = sum(enemyShips);
        if (newSize < enemyFleetSize) {
            System.out.println("Ship sunk: " + (enemyFleetSize - newSize));
            enemyFleetSize = newSize;

            //hit skal bruges til at finde ud af om der bliver ramt et skib
        }
    }

    public void newRound(int round) {
        hitMap.clear();
        fillHitMap();
    }

    public void fillHitMap() {
        for (int x = 0; x < sizeX; ++x) {
            for (int y = 0; y < sizeY; ++y) {
                hitMap.add(shot = new Position(x, y));
            }
        }
    }

}
