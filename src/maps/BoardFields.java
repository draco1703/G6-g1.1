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

    public BoardFields() {
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

    public void setPosXY(int x, int y) {
        pos = new Position(x, y);
    }

    public BoardFields getBoardFieldsByPosition(int x, int y, List<BoardFields> temp) {
        for (BoardFields o : temp) {
            if (o.getPos().x == x && o.getPos().y == y) {
                return o;
            }
        }
        return null;
    }

    public BoardFields getHighestY(List<BoardFields> field) {
        BoardFields finalPosition = null;
        for (BoardFields x : field) {
            if (finalPosition == null || x.getPos().y > finalPosition.getPos().y) {
                finalPosition = x;
            }
        }
        return finalPosition;
    }

    public BoardFields getHighestX(List<BoardFields> field) {
        BoardFields finalPosition = null;
        for (BoardFields x : field) {
            if (finalPosition == null || x.getPos().x > finalPosition.getPos().x) {
                finalPosition = x;
            }
        }
        return finalPosition;

    }

    public BoardFields getLowestX(List<BoardFields> field) {
        BoardFields finalPosition = null;
        for (BoardFields x : field) {
            if (finalPosition == null || x.getPos().x < finalPosition.getPos().x) {
                finalPosition = x;
            }
        }
        return finalPosition;
    }

    public BoardFields getLowestY(List<BoardFields> field) {
        BoardFields finalPosition = null;
        for (BoardFields x : field) {
            if (finalPosition == null || x.getPos().y < finalPosition.getPos().y) {
                finalPosition = x;
            }
        }
        return finalPosition;
    }

    public boolean ShipVertical(List<BoardFields> field) {
        return field.get(0).getPos().y != field.get(1).getPos().y;
    }

    public boolean lastShotHorizentalRight(List<BoardFields> field) {
        if (field.size() < 2) {
            return false;
        } else {
            return field.get(field.size() - 2).getPos().x < field.get(field.size() - 1).getPos().x;
        }
    }

    public boolean lastShotVerticalUp(List<BoardFields> field) {
        if (field.size() < 2) {
            return false;
        } else {
            return field.get(field.size() - 2).getPos().y < field.get(field.size() - 1).getPos().y;
        }
    }
}
