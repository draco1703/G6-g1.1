package shooter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import maps.BoardFields;
public class Shooter {

    private int enemyFleetSize;

    private final int sizeX;
    private final int sizeY;
    private final Random rnd;
    private boolean finish;
    BoardFields hitShot;
    private Position pos;
    private final List<BoardFields> hitMap = new ArrayList<>();
    private final Finisher finisher = new Finisher();
    private BoardFields temp;

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
        //checks if finishing ship
        if (finish) {
            pos = finisher.finish(hitShot, hitMap);
            if (pos != null) {
                temp.getBoardFieldsByPosition(pos.x, pos.y, hitMap);
                System.out.println("shooting at " + pos.x + pos.y);
                return pos;
            } else {
                finish = false;
                temp = hitMap.get(rnd.nextInt(hitMap.size()));
                hitMap.remove(temp);
                System.out.println("shooting at " + temp.getPos().x + temp.getPos().y);
                return temp.getPos();
            }
        }

        temp = hitMap.get(rnd.nextInt(hitMap.size()));
        hitMap.remove(temp);
        System.out.println("shooting at " + temp.getPos().x + temp.getPos().y);
        return temp.getPos();
    }
//finder ud af om der mangler et skib

    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        int newSize = sum(enemyShips);
        //hvis skibet er sunket
        if (hit) {
            hitShot = temp;
            finish = true;
        }
        if (newSize < enemyFleetSize) {
            enemyFleetSize = newSize;
            finish = false;
        }
        //hvis du har ramt et skib uden det er blevet sunket
    }

    public void newRound(int round) {
        hitMap.clear();
        fillHitMap();
        finish = false;
    }

    public void fillHitMap() {
        // y = 10 && x 0
        // y = 10 && x = 10
        // y = 0 && x = 10 
        // y = 0 && x = 0

        for (int x = 0; x < sizeX; ++x) {
            for (int y = 0; y < sizeY; ++y) {
                if (x == 0 && y == 0) {
                    hitMap.add(new BoardFields(true, false, true, false, pos = new Position(x, y)));
                } else if (x == 9 && y == 9) {
                    hitMap.add(new BoardFields(true, false, true, false, pos = new Position(x, y)));
                } else if (x == 9 && y == 0) {
                    hitMap.add(new BoardFields(true, false, false, true, pos = new Position(x, y)));
                } else if (x == 0 && y == 9) {
                    hitMap.add(new BoardFields(false, true, true, false, pos = new Position(x, y)));
                } else if (x == 9) {
                    hitMap.add(new BoardFields(true, true, false, true, pos = new Position(x, y)));
                } else if (x == 0) {
                    hitMap.add(new BoardFields(true, true, true, false, pos = new Position(x, y)));
                } else if (y == 9) {
                    hitMap.add(new BoardFields(false, true, true, true, pos = new Position(x, y)));
                } else if (y == 0) {
                    hitMap.add(new BoardFields(true, false, true, true, pos = new Position(x, y)));
                } else {
                    hitMap.add(new BoardFields(true, true, true, true, pos = new Position(x, y)));
                }
            }
        }
    }

    BoardFields getBoardFieldByPosition(int x, int y) {
        for (BoardFields o : hitMap) {
            if (o.getPos().x == x && o.getPos().y == y) {
                return o;
            }
        }
        return null;
    }
}
