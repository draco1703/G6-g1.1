/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G6.maps;

import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tobias Grundtvig
 */
public class IntMap
{
    private final int[][] map;
    private final int xSize;
    private final int ySize;

    public IntMap(int xSize, int ySize)
    {
        map = new int[xSize][ySize];
        this.xSize = xSize;
        this.ySize = ySize;
    }
    
    public int getXSize()
    {
        return xSize;
    }
    
    public int getYSize()
    {
        return ySize;
    }
    
    public void clear()
    {
        for(int x = 0; x < xSize; ++x)
        {
            for(int y = 0; y < ySize; ++y)
            {
                map[x][y] = 0;
            }
        }
    }
    
    public void set(int x, int y, int value)
    {
        map[x][y] = value;
    }
    
    public void add(int x, int y, int i)
    {
        map[x][y] += i;
    }
    
    public int get(int x, int y)
    {
        return map[x][y];
    }
    
    public List<Position> getHighest()
    {
        int max = Integer.MIN_VALUE;
        for (int x = 0; x < xSize; ++x)
        {
            for (int y = 0; y < ySize; ++y)
            {
                if (map[x][y] > max)
                {
                    max = map[x][y];
                }
            }
        }
        List<Position> res = new ArrayList<>();
        for (int x = 0; x < xSize; ++x)
        {
            for (int y = 0; y < ySize; ++y)
            {
                if (map[x][y] == max)
                {
                    res.add(new Position(x, y));
                }
            }
        }
        return res;
    }
    
    public List<Position> getNonZero()
    {
        List<Position> res = new ArrayList<>();
        for (int x = 0; x < xSize; ++x)
        {
            for (int y = 0; y < ySize; ++y)
            {
                if (map[x][y] > 0)
                {
                    res.add(new Position(x, y));
                }
            }
        }
        return res;
    }
    
    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        for(int y = 0; y < ySize; ++y)
        {
            for(int x = 0; x < xSize; ++x)
            {
                res.append(map[x][y]);
                res.append(' ');
            }
            res.append('\n');
        }
        return res.toString();
    }
    
}
