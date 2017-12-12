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
            BoardFields field = finish();
            if (field != null) {
                Position shot = new Position(field.getPos().x, field.getPos().y);
                temp = field;
                hitMap.remove(getBoardFieldByPosition(field.getPos().x, field.getPos().y));
                return shot;
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

    public BoardFields finish() {
        //check for up
        if (hitShot.HasUp() && getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y + 1) != null) {
            return getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y + 1);

            //check for down
        } else if (hitShot.HasDown() && getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y - 1) != null) {
            return getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y - 1);
            //check for right
        } else if (hitShot.HasRight() && getBoardFieldByPosition(hitShot.getPos().x + 1, hitShot.getPos().y) != null) {
            return getBoardFieldByPosition(hitShot.getPos().x + 1, hitShot.getPos().y);
            //check for right
        } else if (hitShot.HasLeft() && getBoardFieldByPosition(hitShot.getPos().x - 1, hitShot.getPos().y) != null) {
            return getBoardFieldByPosition(hitShot.getPos().x - 1, hitShot.getPos().y);
        } else {
            return null;
        }
    }
}
