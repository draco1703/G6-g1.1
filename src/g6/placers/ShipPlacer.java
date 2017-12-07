package g6.placers;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import maps.IntMap;
import maps.BooleanMap;

public class ShipPlacer {

    private final boolean adjacentShips = true;
    private final boolean useHeatMap = false;
    private final Random rnd;
    private int shotValue;
    private final int xSize;
    private final int ySize;
    private final IntMap heatMap;
    private final IntMap shotMap;
    private final BooleanMap shipPlaces;
    private final BooleanMap shipMap;
    private final Position[][] positions;
    private final ArrayList<Ship> plainShip = new ArrayList<>();
    private final ArrayList<ShipTools> ships = new ArrayList<>();

    public ShipPlacer(int xSize, int ySize, Random rnd) {
        this.rnd = rnd;
        this.xSize = xSize;
        this.ySize = ySize;
        heatMap = new IntMap(xSize, ySize);
        shotMap = new IntMap(xSize, ySize);
        shipMap = new BooleanMap(xSize, ySize);
        shipPlaces = new BooleanMap(xSize, ySize);
        positions = new Position[xSize][ySize];
        for (int x = 0; x < xSize; ++x) {
            for (int y = 0; y < ySize; ++y) {
                positions[x][y] = new Position(x, y);
            }
        }
    }

    public void placeShips(Fleet fleet, Board board) {
        clearEverything();
        for (int i = 0; i < fleet.getNumberOfShips(); i++) {
            plainShip.add(fleet.getShip(i));
        }
        sortHighestFirst(plainShip);
        for (Ship ship : plainShip) {
            shipPlacer(ship, board);
        }
        for (ShipTools ship : ships) {
            board.placeShip(ship.getShipPos(), ship.getShip(), ship.isVertical());
        }

    }
    // ships.add(new ShipTools(ship, positions[?][?], angle));
    //board.placeshipt(ships, boolean,lol);

    public void incoming(Position pos) {
        heatMap.add(pos.x, pos.y, shotValue--);
        //will be used for placing ships later
        shotMap.set(pos.x, pos.y, shotMap.get(pos.x, pos.y) + 1);
    }

    public IntMap getShotMap() {
        return shotMap;
    }

    public void shipPlacer(Ship ship, Board board) {
        int count = 0;
        int sizeX = board.sizeX();
        int sizeY = board.sizeY();
        Position pos = null;
        boolean shipPlaced = false;

        //vertical
        do {
            count++;
            boolean angle = rnd.nextBoolean();
            if (angle && sizeY >= ship.size()) {

                int x = rnd.nextInt(sizeX);
                int y = rnd.nextInt(sizeY - (ship.size() - 1));
                if (checkIfOtherShips(ship, x, y, angle) == true) {
                    pos = new Position(x, y);
                    markShipPlaces(ship, x, y, angle);
                    ships.add(new ShipTools(ship, positions[x][y], angle));
                    shipPlaced = true;
                }
                //horizontal
            } else {
                angle = false;
                int x = rnd.nextInt(sizeX - (ship.size() - 1));
                int y = rnd.nextInt(sizeY);
                if (checkIfOtherShips(ship, x, y, angle) == true) {
                    pos = new Position(x, y);
                    markShipPlaces(ship, x, y, angle);
                    ships.add(new ShipTools(ship, positions[x][y], angle));
                    shipPlaced = true;
                }
            }
        } while (shipPlaced == false && count < 100);
    }

    boolean checkIfOtherShips(Ship ship, int x, int y, boolean angle) {
        if (angle) {
            for (int j = 0; j < ship.size(); j++) {
                if (shipPlaces.getPos(x, y + j) == true) {
                    return false;
                }
            }
        } else {
            for (int j = 0; j < ship.size(); j++) {
                if (shipPlaces.getPos(x + j, y) == true) {
                    return false;
                }
            }
        }
        return true;
    }

    private void markShipPlaces(Ship ship, int x, int y, boolean angle) {
        if (angle == true) {
            for (int i = 0; i < ship.size(); i++) {
                shipPlaces.mark(x, y + i);
            }
        } else {
            for (int j = 0; j < ship.size(); j++) {
                shipPlaces.mark(x + j, y);
            }
        }
    }

    void sortHighestFirst(ArrayList<Ship> ship) {

        Collections.sort(ship, (Ship a, Ship b) -> {

            if (a.size() < b.size()) {
                return 1;
            } else if (a.size() == b.size()) {
                return 0;
            } else {
                return -1;
            }
        });
    }

    void clearEverything() {
        shipMap.clear();
        heatMap.clear();
        shipPlaces.clear();
        shipMap.clear();
        plainShip.clear();
        ships.clear();
    }
}
