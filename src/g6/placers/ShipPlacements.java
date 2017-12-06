package g6.placers;

import battleship.interfaces.Position;

public class ShipPlacements {

    private final ShipPlacements ship;
    private final Position pos;
    private int value;
    private boolean vertical;

    public ShipPlacements(ShipPlacements ship, Position pos, boolean vertical, int value) {
        this.ship = ship;
        this.pos = pos;
        this.vertical = vertical;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public Position getShipPos() {
        return ship.pos;
    }

    public ShipPlacements getShip() {
        return ship;
    }
}
