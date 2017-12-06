package g6.placers;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import G6.maps.BooleanMap;
import G6.maps.IntMap;

public class ShipPlacer {

    private boolean adjacentShips = true;
    private boolean useHeatMap = false;
    private final Random rnd;
    private int shotValue;
    private final int xSize;
    private final int ySize;
    private final IntMap heatMap;
    private IntMap shotMap;
    private final BooleanMap shipMap;
    private final Position[][] positions;

    public ShipPlacer(int xSize, int ySize, Random rnd) {
        this.rnd = rnd;
        this.xSize = xSize;
        this.ySize = ySize;
        heatMap = new IntMap(xSize, ySize);
        shotMap = new IntMap(xSize, ySize);
        shipMap = new BooleanMap(xSize, ySize);
        positions = new Position[xSize][ySize];
        for (int x = 0; x < xSize; ++x) {
            for (int y = 0; y < ySize; ++y) {
                positions[x][y] = new Position(x, y);
            }
        }
    }

    public void placeShips(Fleet fleet, Board board) {
        shotValue = xSize * ySize;
        shipMap.clear();
        shipPlacer(fleet, board);
    }

    public void incoming(Position pos) {
        heatMap.add(pos.x, pos.y, shotValue--);
        shotMap.set(pos.x, pos.y, shotMap.get(pos.x, pos.y) + 1);
    }

    public IntMap getShotMap() {
        return shotMap;
    }

    public void shipPlacer(Fleet fleet, Board board) {
        
        int sizeX = board.sizeX();
        int sizeY = board.sizeY();
        for(int i = 0; i < fleet.getNumberOfShips(); ++i)
        {
            Ship s = fleet.getShip(i);
            boolean vertical = rnd.nextBoolean();
            Position pos;
            if(vertical)
            {
                int x = rnd.nextInt(sizeX);
                int y = rnd.nextInt(sizeY-(s.size()-1));
                pos = new Position(x, y);
            }
            else
            {
                int x = rnd.nextInt(sizeX-(s.size()-1));
                int y = rnd.nextInt(sizeY);
                pos = new Position(x, y);
            }
            board.placeShip(pos, s, vertical);
        }
    }
}
