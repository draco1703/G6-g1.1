/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g6.placers;

import battleship.interfaces.Position;
import battleship.interfaces.Ship;


public class ShipConf implements Comparable<ShipConf>
{
    private final Ship ship;
    private final Position pos;
    private final boolean vertical;
    private final int value;

    public ShipConf(Ship ship, Position pos, boolean vertical, int value)
    {
        this.ship = ship;
        this.pos = pos;
        this.vertical = vertical;
        this.value = value;
    }
    
    public Ship getShip()
    {
        return ship;
    }
    
    public Position getPosition()
    {
        return pos;
    }
    
    public boolean getVertical()
    {
        return vertical;
    }
    
    public int getValue()
    {
        return value;
    }
    

    @Override
    public int compareTo(ShipConf o)
    {
        return this.value - o.value;
    }    
}
