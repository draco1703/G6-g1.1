package maps;

public class BooleanMap {

    private final boolean[][] map;
    private final int xSize;
    private final int ySize;

    public BooleanMap(int xSize, int ySize) {
        map = new boolean[xSize][ySize];
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
                map[x][y] = false;
            }
        }
    }

    public void mark(int x, int y) {
        map[x][y] = true;
    }

    public void unMark(int x, int y) {
        map[x][y] = false;
    }

    public boolean getPos(int x, int y) {
        return map[x][y];
    }

}
