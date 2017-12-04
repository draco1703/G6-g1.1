package g6.shooters;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.List;
import java.util.Random;
import G6.maps.IntMap;

public class Seeker {

    private final Random rnd;
    private final IntMap shots;
    private final IntMap shipDist;
    private Position lastShot;

    public Seeker(IntMap shots, Random rnd) {
        this.rnd = rnd;
        this.shots = shots;
        this.shipDist = new IntMap(shots.getXSize(), shots.getYSize());
    }

    public Position getFireCoordinates(Fleet enemyShips) {
        generateShipDistribution(enemyShips);
        List<Position> bestCoordinates = shipDist.getHighest();
        lastShot = bestCoordinates.get(rnd.nextInt(bestCoordinates.size()));
        return lastShot;
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
                    for (int i = 0; i < s.size(); ++i) {
                        int val = shots.get(x + i, y);
                        if (val != 0) {
                            canPlace = false;
                            break;
                        }
                    }
                    if (canPlace) {
                        for (int i = 0; i < s.size(); ++i) {
                            shipDist.add(x + i, y, 1);
                        }
                    }
                }
            }
            //Vertical
            maxX = shipDist.getXSize() - 1;
            maxY = shipDist.getYSize() - s.size();
            for (int y = 0; y <= maxY; ++y) {
                for (int x = 0; x <= maxX; ++x) {
                    boolean canPlace = true;
                    for (int i = 0; i < s.size(); ++i) {
                        int val = shots.get(x, y + i);
                        if (val != 0) {
                            canPlace = false;
                            break;
                        }
                    }
                    if (canPlace) {
                        for (int i = 0; i < s.size(); ++i) {
                            shipDist.add(x, y + i, 1);
                        }
                    }
                }
            }
        }
    }
}
