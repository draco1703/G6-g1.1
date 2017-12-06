package G6.maps;

import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.List;

public class FloatMap {

    private final float[][] map;
    private final int xSize;
    private final int ySize;

    public FloatMap(int xSize, int ySize) {
        map = new float[xSize][ySize];
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public void clear() {
        for (int x = 0; x < xSize; ++x) {
            for (int y = 0; y < ySize; ++y) {
                map[x][y] = 0.0f;
            }
        }
    }

    public void set(int x, int y, float f) {
        map[x][y] = f;
    }

    public void add(int x, int y, float f) {
        map[x][y] += f;
    }

    public float get(int x, int y) {
        return map[x][y];
    }

    public FloatMap getNormalized() {
        float max = 0.0f;
        for (int x = 0; x < xSize; ++x) {
            for (int y = 0; y < ySize; ++y) {
                if (map[x][y] > max) {
                    max = map[x][y];
                }
            }
        }
        FloatMap res = new FloatMap(xSize, ySize);
        if (max > 0.0f) {
            float invMax = 1.0f / max;
            for (int x = 0; x < xSize; ++x) {
                for (int y = 0; y < ySize; ++y) {
                    res.map[x][y] = invMax * map[x][y];
                }
            }
        }
        return res;
    }

    public void normalize() {
        float max = 0.0f;
        for (int x = 0; x < xSize; ++x) {
            for (int y = 0; y < ySize; ++y) {
                if (map[x][y] > max) {
                    max = map[x][y];
                }
            }
        }
        if (max > 0.0f) {
            float invMax = 1.0f / max;
            for (int x = 0; x < xSize; ++x) {
                for (int y = 0; y < ySize; ++y) {
                    map[x][y] *= invMax;
                }
            }
        }
    }

    public List<Position> getHighest() {
        float max = 0.0f;
        for (int x = 0; x < xSize; ++x) {
            for (int y = 0; y < ySize; ++y) {
                if (map[x][y] > max) {
                    max = map[x][y];
                }
            }
        }
        List<Position> res = new ArrayList<>();
        for (int x = 0; x < xSize; ++x) {
            for (int y = 0; y < ySize; ++y) {
                if (map[x][y] == max) {
                    res.add(new Position(x, y));
                }
            }
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int y = 0; y < ySize; ++y) {
            for (int x = 0; x < xSize; ++x) {
                res.append(map[x][y]);
                res.append(' ');
            }
            res.append('\n');
        }
        return res.toString();
    }
}
