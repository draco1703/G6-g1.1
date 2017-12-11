package maps;

import battleship.interfaces.Position;
import java.util.List;

public class BoardFields {

    boolean hasUp;
    boolean hasDown;
    boolean hasRight;
    boolean hasLeft;
    Position pos;

    public BoardFields(boolean hasUp, boolean hasDown, boolean hasRight, boolean hasLeft, Position pos) {
        this.hasUp = hasUp;
        this.hasDown = hasDown;
        this.hasRight = hasRight;
        this.hasLeft = hasLeft;
        this.pos = pos;
    }

    public boolean HasUp() {
        return hasUp;
    }

    public void setHasUp(boolean hasUp) {
        this.hasUp = hasUp;
    }

    public boolean HasDown() {
        return hasDown;
    }

    public void setHasDown(boolean hasDown) {
        this.hasDown = hasDown;
    }

    public boolean HasRight() {
        return hasRight;
    }

    public void setHasRight(boolean hasRight) {
        this.hasRight = hasRight;
    }

    public boolean HasLeft() {
        return hasLeft;
    }

    public void setHasLeft(boolean hasLeft) {
        this.hasLeft = hasLeft;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public BoardFields getBoardFieldsByPosition(int x, int y, List<BoardFields> temp) {
        for (BoardFields o : temp) {
            if (o.getPos().x == x && o.getPos().y == y) {
                return o;
            }
        }
        return null;
    }

}
