package g6.placers;

import battleship.interfaces.Position;
import battleship.interfaces.Ship;

public class ShipTools {

    private final Ship ship;
    private final Position pos;
    private boolean vertical;

    public ShipTools(Ship ship, Position pos, boolean vertical) {
        this.ship = ship;
        this.pos = pos;
        this.vertical = vertical;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public Position getShipPos() {
        return pos;
    }

    public Ship getShip() {
        return ship;
    }
}