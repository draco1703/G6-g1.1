/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g6.shooters;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.Random;
import G6.maps.IntMap;

/**
 *
 * @author Tobias Grundtvig
 */
public class Shooter
{

    private final IntMap shots;
    private Position lastShot;
    private boolean isHunting;
    private final Hunter hunter;
    private final Seeker seeker;

    public Shooter(int xSize, int ySize, Random rnd)
    {
        shots = new IntMap(xSize, ySize);
        hunter = new Hunter(shots, rnd);
        seeker = new Seeker(shots, rnd);
    }

    public void newRound(int round)
    {
        shots.clear();
        isHunting = false;
    }

    public Position getFireCoordinates(Fleet enemyShips)
    {
        //System.out.println(shots);
        if (isHunting)
        {
            lastShot = hunter.getFireCoordinates(enemyShips);
        } else
        {
            lastShot = seeker.getFireCoordinates(enemyShips);
        }
        return lastShot;
    }

    public void hitFeedBack(boolean hit, Fleet enemyShips)
    {
        if (isHunting)
        {
            isHunting = hunter.hitFeedback(hit, enemyShips);
        } else
        {
            if (hit)
            {
                isHunting = true;
                shots.set(lastShot.x, lastShot.y, 1);
                hunter.startHunt(lastShot, enemyShips);
            } else
            {
                shots.set(lastShot.x, lastShot.y, -1);
            }
        }
    }
}
