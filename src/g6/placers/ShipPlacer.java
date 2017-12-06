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

        //Shuffle the ship list
        List<Ship> ships = new ArrayList<>(fleet.getNumberOfShips());
        for (Ship s : fleet) {
            ships.add(s);
        }
        Collections.shuffle(ships);
        for (Ship s : ships) {
            List<ShipConf> confs = getConfigurations(s);
            ShipConf conf = selectConf(confs);
            if (conf != null) {
                placeShip(conf, board);
            }
        }
    }

    public void incoming(Position pos) {
        heatMap.add(pos.x, pos.y, shotValue--);
        shotMap.set(pos.x, pos.y, shotMap.get(pos.x, pos.y) + 1);
    }

    private ShipConf selectConf(List<ShipConf> confs) {
        int count = confs.size();
        if (confs.isEmpty()) {
            return null;
        }
        if (useHeatMap) {
            Collections.sort(confs);
            int bestValue = confs.get(0).getValue();
            count = 1;
            for (int i = 1; i < confs.size(); ++i) {
                ShipConf c = confs.get(i);
                if (c.getValue() > bestValue) {
                    break;
                }
                ++count;
            }
        }
        return confs.get(rnd.nextInt(count));
    }

    public IntMap getShotMap() {
        return shotMap;
    }

    private void placeShip(ShipConf conf, Board board) {
        int x;
        int y;
        int size = conf.getShip().size();
        board.placeShip(conf.getPosition(), conf.getShip(), conf.getVertical());
        if (conf.getVertical()) {
            x = conf.getPosition().x;
            if (!adjacentShips) {
                //Bottom
                y = conf.getPosition().y - 1;
                markShipPoint(x, y);
                //Top
                y = conf.getPosition().y + size;
                markShipPoint(x, y);
            }
            //Ship+sides
            y = conf.getPosition().y;
            for (int i = 0; i < size; ++i) {
                if (!adjacentShips) {
                    markShipPoint(x - 1, y + i);
                }
                markShipPoint(x, y + i);
                if (!adjacentShips) {
                    markShipPoint(x + 1, y + i);
                }
            }
        } else {
            y = conf.getPosition().y;
            if (!adjacentShips) {
                //Left
                x = conf.getPosition().x - 1;
                markShipPoint(x, y);
                //Right
                x = conf.getPosition().x + size;
                markShipPoint(x, y);
            }

            //Ship+sides
            x = conf.getPosition().x;
            for (int i = 0; i < size; ++i) {
                if (!adjacentShips) {
                    markShipPoint(x + i, y - 1);
                }
                markShipPoint(x + i, y);
                if (!adjacentShips) {
                    markShipPoint(x + i, y + 1);
                }
            }
        }
    }

    private void markShipPoint(int x, int y) {
        if (x >= 0 && x < xSize && y >= 0 && y < ySize) {
            shipMap.mark(x, y);
        }
    }

    private List<ShipConf> getConfigurations(Ship ship) {
        int size = ship.size();
        List<ShipConf> res = new ArrayList<>();
        
        
        //Horizontal
        for (int y = 0; y < ySize; ++y) {
            for (int x = 0; x <= xSize - size; ++x) {
                boolean validPos = true;
                int value = 0;
                for (int i = 0; i < size; ++i) {
                    //shipMap.getPos returns false if
                    //it is a possible placement for the ship
                    if (shipMap.getPos(x + i, y)) {
                        validPos = false;
                        break;
                    }
                    value += heatMap.get(x + i, y);
                }
                if (validPos) {
                    res.add(new ShipConf(ship, positions[x][y], false, value));
                }
            }
        }

        //Vertical
        for (int y = 0; y <= ySize - size; ++y) {
            for (int x = 0; x < xSize; ++x) {
                boolean validPos = true;
                int value = 0;
                for (int i = 0; i < size; ++i) {
                    if (shipMap.getPos(x, y + i)) {
                        validPos = false;
                        break;
                    }
                    value += heatMap.get(x, y + i);
                }
                if (validPos) {
                    res.add(new ShipConf(ship, positions[x][y], true, value));
                }
            }
        }
        return res;
    }
}
