/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g6.shooters;

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
    private Position pos;
    private BoardFields hitShot;
    private final List<BoardFields> hitMap = new ArrayList<>();
    private final List<BoardFields> tempMap = new ArrayList<>();
    private BoardFields temp;
    private BoardFields field;
    private boolean hitStreak;
    private BoardFields ifHit;
    private final BoardFields BFObject = new BoardFields();

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

    //skal returnere en position der repræsenterer hvor vi vil skyde
    public Position getFireCoordinates(Fleet enemyShips) {
        enemyFleetSize = sum(enemyShips);
        //checks if finishing ship
        if (finish) {
            if (2 <= tempMap.size()) {
                field = shipSink();
            } else {
                field = findShipDirection();
            }
            if (BFObject.getBoardFieldsByPosition(field.getPos().x, field.getPos().y, hitMap) != null) {
                temp = field;
                ifHit = field;
                hitMap.remove(BFObject.getBoardFieldsByPosition(field.getPos().x, field.getPos().y, hitMap));
                return temp.getPos();
            } else {
                finish = false;
                temp = hitMap.get(rnd.nextInt(hitMap.size()));
                ifHit = temp;
                hitMap.remove(temp);
                return temp.getPos();
            }
        }

        temp = hitMap.get(rnd.nextInt(hitMap.size()));
        ifHit = temp;
        hitMap.remove(temp);
        return temp.getPos();
    }

    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        int newSize = sum(enemyShips);
        //hvis skibet er sunket
        if (hit) {
            tempMap.add(ifHit);
            hitStreak = true;
            hitShot = temp;
            finish = true;
        } else {
            hitStreak = false;
        }
        if (newSize < enemyFleetSize) {
            enemyFleetSize = newSize;
            tempMap.clear();
            finish = false;
        }
    }

    public void newRound(int round) {
        hitMap.clear();
        fillHitMap();
        tempMap.clear();
        finish = false;
    }

    public void fillHitMap() {
        for (int x = 0; x < sizeX; ++x) {
            for (int y = 0; y < sizeY; ++y) {
                if (x == 0 && y == 0) {
                    hitMap.add(new BoardFields(true, false, true, false, pos = new Position(x, y)));
                } else if (x == sizeX - 1 && y == sizeY - 1) {
                    hitMap.add(new BoardFields(true, false, true, false, pos = new Position(x, y)));
                } else if (x == sizeX - 1 && y == 0) {
                    hitMap.add(new BoardFields(true, false, false, true, pos = new Position(x, y)));
                } else if (x == 0 && y == sizeY - 1) {
                    hitMap.add(new BoardFields(false, true, true, false, pos = new Position(x, y)));
                } else if (x == sizeX - 1) {
                    hitMap.add(new BoardFields(true, true, false, true, pos = new Position(x, y)));
                } else if (x == 0) {
                    hitMap.add(new BoardFields(true, true, true, false, pos = new Position(x, y)));
                } else if (y == sizeY - 1) {
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

    public BoardFields findShipDirection() {
        //vertical
        //check for up   
        if (hitShot.HasUp() && getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y + 1) != null) {
            return getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y + 1);
            //check for down
        } else if (hitShot.HasDown() && getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y - 1) != null) {
            return getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y - 1);
        }

        //horizontal
        //check for right
        if (hitShot.HasRight() && getBoardFieldByPosition(hitShot.getPos().x + 1, hitShot.getPos().y) != null) {
            return getBoardFieldByPosition(hitShot.getPos().x + 1, hitShot.getPos().y);
            //check for left
        } else if (hitShot.HasLeft() && getBoardFieldByPosition(hitShot.getPos().x - 1, hitShot.getPos().y) != null) {
            return getBoardFieldByPosition(hitShot.getPos().x - 1, hitShot.getPos().y);
        }
        return null;
    }

    public BoardFields shipSink() {
        if (BFObject.ShipVertical(tempMap)) {
            //Vertical
            if (hitStreak) {
                if (BFObject.lastShotVerticalUp(tempMap)) {
                    BoardFields tempField = BFObject.getHighestY(tempMap);
                    if (tempField.HasUp()) {
                        BoardFields temporary = new BoardFields(tempField.HasUp(), tempField.HasDown(),
                                tempField.HasRight(), tempField.HasLeft(), tempField.getPos());
                        temporary.setPosXY(temporary.getPos().x, temporary.getPos().y + 1);
                        return temporary;
                    } else {
                        tempMap.clear();
                        return null;
                    }

                } else {
                    BoardFields tempField = BFObject.getLowestY(tempMap);
                    if (tempField.HasDown()) {
                        BoardFields temporary = new BoardFields(tempField.HasUp(), tempField.HasDown(),
                                tempField.HasRight(), tempField.HasLeft(), tempField.getPos());
                        temporary.setPosXY(temporary.getPos().x, temporary.getPos().y - 1);
                        return temporary;
                    } else {
                        tempMap.clear();
                        return null;
                    }
                }

                //shoots down
            }
            if (BFObject.lastShotVerticalUp(tempMap)) {
                BoardFields tempField = BFObject.getLowestY(tempMap);
                if (tempField.HasDown()) {
                    BoardFields temporary = new BoardFields(tempField.HasUp(), tempField.HasDown(),
                            tempField.HasRight(), tempField.HasLeft(), tempField.getPos());
                    temporary.setPosXY(temporary.getPos().x, temporary.getPos().y - 1);
                    return temporary;
                } else {
                    tempMap.clear();
                    return null;
                }

                //shoots up
            } else if (BFObject.lastShotVerticalUp(tempMap) == false) {
                BoardFields tempField = BFObject.getHighestY(tempMap);
                if (tempField.HasUp()) {
                    BoardFields temporary = new BoardFields(tempField.HasUp(), tempField.HasDown(),
                            tempField.HasRight(), tempField.HasLeft(), tempField.getPos());
                    temporary.setPosXY(temporary.getPos().x, temporary.getPos().y + 1);
                    return temporary;
                } else {
                    tempMap.clear();
                    return null;
                }
            }

        } else {
            //Horizontal
            if (hitStreak) {
                if (BFObject.lastShotHorizentalRight(tempMap)) {
                    BoardFields tempField = BFObject.getHighestX(tempMap);
                    if (tempField.HasRight()) {
                        BoardFields temporary = new BoardFields(tempField.HasUp(), tempField.HasDown(),
                                tempField.HasRight(), tempField.HasLeft(), tempField.getPos());
                        temporary.setPosXY(temporary.getPos().x + 1, temporary.getPos().y);
                        return temporary;
                    } else {
                        tempMap.clear();
                        return null;
                    }
                } else {
                    BoardFields tempField = BFObject.getLowestX(tempMap);
                    if (tempField.HasLeft()) {
                        BoardFields temporary = new BoardFields(tempField.HasUp(), tempField.HasDown(),
                                tempField.HasRight(), tempField.HasLeft(), tempField.getPos());
                        temporary.setPosXY(temporary.getPos().x - 1, temporary.getPos().y);
                        return temporary;
                    } else {
                        tempMap.clear();
                        return null;
                    }
                }
            } else {
                if (BFObject.lastShotHorizentalRight(tempMap) == false) {
                    BoardFields tempField = BFObject.getHighestX(tempMap);
                    if (tempField.HasRight()) {
                        BoardFields temporary = new BoardFields(tempField.HasUp(), tempField.HasDown(),
                                tempField.HasRight(), tempField.HasLeft(), tempField.getPos());
                        temporary.setPosXY(temporary.getPos().x + 1, temporary.getPos().y);
                        return temporary;
                    } else {
                        tempMap.clear();
                        return null;
                    }

                } else if (BFObject.lastShotHorizentalRight(tempMap)) {
                    BoardFields tempField = BFObject.getLowestX(tempMap);
                    if (tempField.HasLeft()) {
                        BoardFields temporary = new BoardFields(tempField.HasUp(), tempField.HasDown(),
                                tempField.HasRight(), tempField.HasLeft(), tempField.getPos());
                        temporary.setPosXY(temporary.getPos().x - 1, temporary.getPos().y);
                        return temporary;
                    } else {
                        tempMap.clear();
                        return null;
                    }
                }
            }

        }

        return null;
    }
}
