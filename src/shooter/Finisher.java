package shooter;

import battleship.interfaces.Position;
import java.util.List;
import java.util.Random;
import maps.BoardFields;
import maps.BooleanMap;

public class Finisher {

    Random rnd = new Random();

    public Position finish(BoardFields hitShot, List<BoardFields> hitMap, Position shot, BooleanMap boolMap) {
        //hitShot is a position object of the coordinates of the last hit
        BoardFields BoardField = getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y, hitMap);
        if (BoardField.HasUp() && BoardField.HasDown()) {
            int random = rnd.nextInt(1);
            //shoots up
            if (random == 1) {
                shot = getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y + 1, hitMap).getPos();
                setAdjecentBoardFieldsFalse(hitShot, hitMap);
                hitMap.remove(shot);
                boolMap.mark(shot.x, shot.y);
                return BoardField.getPos();
            } else {
                shot = getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y - 1, hitMap).getPos();
                setAdjecentBoardFieldsFalse(hitShot, hitMap);
                hitMap.remove(shot);
                boolMap.mark(shot.x, shot.y);
                return BoardField.getPos();
            }
        } else if (BoardField.HasLeft() && BoardField.HasRight()) {
            int random = rnd.nextInt(1);
            //shoots right
            if (random == 1) {
                shot = getBoardFieldByPosition(hitShot.getPos().x + 1, hitShot.getPos().y, hitMap).getPos();
                setAdjecentBoardFieldsFalse(hitShot, hitMap);
                hitMap.remove(shot);
                boolMap.mark(shot.x, shot.y);
                return BoardField.getPos();
            } else {
                shot = getBoardFieldByPosition(hitShot.getPos().x - 1, hitShot.getPos().y, hitMap).getPos();
                setAdjecentBoardFieldsFalse(hitShot, hitMap);
                hitMap.remove(shot);
                boolMap.mark(shot.x, shot.y);
                return BoardField.getPos();
            }
        } else if (BoardField.HasUp()) {
            shot = getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y + 1, hitMap).getPos();
            setAdjecentBoardFieldsFalse(hitShot, hitMap);
            hitMap.remove(shot);
            boolMap.mark(shot.x, shot.y);
            return BoardField.getPos();

        } else if (BoardField.HasDown()) {
            shot = getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y - 1, hitMap).getPos();
            setAdjecentBoardFieldsFalse(hitShot, hitMap);
            hitMap.remove(shot);
            boolMap.mark(shot.x, shot.y);
            return BoardField.getPos();

        } else if (BoardField.HasRight()) {
            shot = getBoardFieldByPosition(hitShot.getPos().x + 1, hitShot.getPos().y, hitMap).getPos();
            setAdjecentBoardFieldsFalse(hitShot, hitMap);
            hitMap.remove(shot);
            boolMap.mark(shot.x, shot.y);
            return BoardField.getPos();

        } else if (BoardField.HasLeft()) {
            shot = getBoardFieldByPosition(hitShot.getPos().x - 1, hitShot.getPos().y, hitMap).getPos();
            setAdjecentBoardFieldsFalse(hitShot, hitMap);
            hitMap.remove(shot);
            boolMap.mark(shot.x, shot.y);
            return BoardField.getPos();

        } else {
            return null;
        }
    }

    void setAdjecentBoardFieldsFalse(BoardFields temp, List<BoardFields> hitMap) {
        if (temp.HasUp()) {
            temp.getBoardFieldsByPosition(temp.getPos().x, temp.getPos().y + 1, hitMap).setHasUp(false);
        }
        if (temp.HasDown()) {
            temp.getBoardFieldsByPosition(temp.getPos().x, temp.getPos().y - 1, hitMap).setHasDown(false);
        }
        if (temp.HasLeft()) {
            temp.getBoardFieldsByPosition(temp.getPos().x - 1, temp.getPos().y + 1, hitMap).setHasLeft(false);
        }
        if (temp.HasRight()) {
            temp.getBoardFieldsByPosition(temp.getPos().x + 1, temp.getPos().y + 1, hitMap).setHasRight(false);

        }
    }

    BoardFields getBoardFieldByPosition(int x, int y, List<BoardFields> hitMap) {
        for (BoardFields o : hitMap) {
            if (o.getPos().x == x && o.getPos().y == y) {
                return o;
            }
        }
        return null;
    }
}
