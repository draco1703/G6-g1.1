package g6.shooters;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import maps.IntMap;

public class Hunter {

    private final Random rnd;
    private final IntMap shots;
    private final IntMap shipDist;
    private final List<Position> hits;
    private Position lastShot;
    private int numHits;
    private int startFleetSum;

    public Hunter(IntMap shots, Random rnd) {
        this.rnd = rnd;
        this.shots = shots;
        this.shipDist = new IntMap(shots.getXSize(), shots.getYSize());
        this.hits = new ArrayList<>();
    }

    public void startHunt(Position pos, Fleet fleet) {
        numHits = 1;
        startFleetSum = fleetSum(fleet);
        hits.clear();
        hits.add(pos);
    }

    public Position getFireCoordinates(Fleet enemyShips) {
        generateShipDistribution(enemyShips);
        List<Position> bestCoordinates = shipDist.getHighest();
        lastShot = bestCoordinates.get(rnd.nextInt(bestCoordinates.size()));
        return lastShot;
    }

    //Returns true if hunt is still on...
    public boolean hitFeedback(boolean hit, Fleet enemyShips) {
        shots.set(lastShot.x, lastShot.y, hit ? 1 : -1);
        if (hit) {
            ++numHits;
            hits.add(lastShot);
            int curFleetSum = fleetSum(enemyShips);
            if (curFleetSum < startFleetSum) {
                //Ship down...
                numHits -= startFleetSum - curFleetSum;
                if (numHits == 0) {
                    //All hits are accounted for
                    //Mark hits
                    for (Position p : hits) {
                        shots.set(p.x, p.y, -2);
                    }
                    return false;
                }
                startFleetSum = curFleetSum;
            }
        }
        return true;
    }

    private int fleetSum(Fleet fleet) {
        int res = 0;
        for (Ship s : fleet) {
            res += s.size();
        }
        return res;
    }

    private void generateShipDistribution(Fleet enemyShips) {
        shipDist.clear();
        for (Ship s : enemyShips) {
            //Horizontal
            int maxX = shipDist.getXSize() - s.size();
            int maxY = shipDist.getYSize() - 1;
            for (int y = 0; y <= maxY; ++y) {
                for (int x = 0; x <= maxX; ++x) {
                    boolean canPlace = true;
                    int hitCount = 0;
                    for (int i = 0; i < s.size(); ++i) {
                        int val = shots.get(x + i, y);
                        if (val < 0) {
                            canPlace = false;
                            break;
                        }
                        if (val > 0) {
                            ++hitCount;
                        }
                    }
                    if (canPlace) {
                        for (int i = 0; i < s.size(); ++i) {
                            int val = shots.get(x + i, y);
                            if (val == 0) {
                                shipDist.add(x + i, y, hitCount);
                            }
                        }
                    }
                }
            }
            //Horizontal
            maxX = shipDist.getXSize() - 1;
            maxY = shipDist.getYSize() - s.size();
            for (int y = 0; y <= maxY; ++y) {
                for (int x = 0; x <= maxX; ++x) {
                    boolean canPlace = true;
                    int hitCount = 0;
                    for (int i = 0; i < s.size(); ++i) {
                        int val = shots.get(x, y + i);
                        if (val < 0) {
                            canPlace = false;
                            break;
                        }
                        if (val > 0) {
                            ++hitCount;
                        }
                    }
                    if (canPlace) {
                        for (int i = 0; i < s.size(); ++i) {
                            int val = shots.get(x, y + i);
                            if (val == 0) {
                                shipDist.add(x, y + i, hitCount);
                            }
                        }
                    }
                }
            }
        }
    }
}
