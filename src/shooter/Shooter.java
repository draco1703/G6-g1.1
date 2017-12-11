package shooter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import maps.BoardFields;
import maps.BooleanMap;

public class Shooter {

    private int enemyFleetSize;

    private final int sizeX;
    private int sizeY;
    private final Random rnd;
    private Position shot;
    private boolean finish;
    BoardFields hitShot;
    private Position pos;
    private final List<BoardFields> hitMap = new ArrayList<>();
    private Finisher finisher = new Finisher();
    private BooleanMap boolMap;

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
            pos = finisher.finish(hitShot, hitMap, shot, boolMap);
            if (pos != null) {
                return pos;
            } else {
                finish = false;
            }
        }

        BoardFields temp = hitMap.get(rnd.nextInt(hitMap.size()));
        setAdjecentBoardFieldsFalse(temp);
        shot = temp.getPos();
        hitMap.remove(temp);
        boolMap.mark(shot.x, shot.y);
        return temp.getPos();
    }
//finder ud af om der mangler et skib

    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        int newSize = sum(enemyShips);
        //hvis skibet er sunket
        if (hit) {
            hitShot = getBoardFieldByPosition(shot.x, shot.y);
            finish = true;
        }
        if (newSize < enemyFleetSize) {
            enemyFleetSize = newSize;
            finish = false;
        }
        //hvis du har ramt et skib uden det er blevet sunket
    }

    public void newRound(int round) {
        boolMap = new BooleanMap(sizeX, sizeX);
        boolMap.clear();
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
                } else if (x == 10 && y == 10) {
                    hitMap.add(new BoardFields(true, false, true, false, pos = new Position(x, y)));
                } else if (x == 10 && y == 0) {
                    hitMap.add(new BoardFields(true, false, false, true, pos = new Position(x, y)));
                } else if (x == 0 && y == 10) {
                    hitMap.add(new BoardFields(false, true, true, false, pos = new Position(x, y)));
                } else if (x == 10) {
                    hitMap.add(new BoardFields(true, true, false, true, pos = new Position(x, y)));
                } else if (x == 0) {
                    hitMap.add(new BoardFields(true, true, true, false, pos = new Position(x, y)));
                } else if (y == 10) {
                    hitMap.add(new BoardFields(false, true, true, true, pos = new Position(x, y)));
                } else if (y == 0) {
                    hitMap.add(new BoardFields(true, false, true, true, pos = new Position(x, y)));
                } else {
                    hitMap.add(new BoardFields(true, true, true, true, pos = new Position(x, y)));
                }
            }
        }
    }

    void setAdjecentBoardFieldsFalse(BoardFields temp) {
        if (temp.HasUp()) {
            temp.getBoardFieldsByPosition(temp.getPos().x, temp.getPos().y + 1, hitMap).setHasUp(false);
        }
        if (temp.HasDown()) {
            temp.getBoardFieldsByPosition(temp.getPos().x, temp.getPos().y - 1, hitMap).setHasDown(false);
        }
        if (temp.HasLeft()) {
            temp.getBoardFieldsByPosition(temp.getPos().x - 1, temp.getPos().y + 1, hitMap).setHasLeft(false);
        }
        if (temp.HasRight()) {
            temp.getBoardFieldsByPosition(temp.getPos().x + 1, temp.getPos().y + 1, hitMap).setHasRight(false);
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
