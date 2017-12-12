package shooter;

import battleship.interfaces.Position;
import java.util.List;
import java.util.Random;
import maps.BoardFields;

public class Finisher {

    Random rnd = new Random();

    public Position finish(BoardFields hitShot, List<BoardFields> hitMap) {
        //check for up
        if (hitShot.HasUp() && getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y + 1, hitMap) != null) {
            Position shot = new Position(hitShot.getPos().x, hitShot.getPos().y + 1);
            hitMap.remove(getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y + 1, hitMap));
            return shot;
            //check for down
        } else if (hitShot.HasDown() && getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y - 1, hitMap) != null) {
            Position shot = new Position(hitShot.getPos().x, hitShot.getPos().y - 1);
            hitMap.remove(getBoardFieldByPosition(hitShot.getPos().x, hitShot.getPos().y - 1, hitMap));
            return shot;
            //check for right
        } else if (hitShot.HasRight() && getBoardFieldByPosition(hitShot.getPos().x + 1, hitShot.getPos().y, hitMap) != null) {
            Position shot = new Position(hitShot.getPos().x + 1, hitShot.getPos().y);
            hitMap.remove(getBoardFieldByPosition(hitShot.getPos().x + 1, hitShot.getPos().y, hitMap));
            return shot;
            //check for right
        } else if (hitShot.HasLeft() && getBoardFieldByPosition(hitShot.getPos().x - 1, hitShot.getPos().y, hitMap) != null) {
            Position shot = new Position(hitShot.getPos().x - 1, hitShot.getPos().y);
            hitMap.remove(getBoardFieldByPosition(hitShot.getPos().x - 1, hitShot.getPos().y, hitMap));
            return shot;
        } else {
            return null;
        }
//        //hitShot is a position object of the coordinates of the last hit
//        if (hitShot.HasUp() && hitShot.HasDown()) {
//            int random = rnd.nextInt(1);
//            //shoots up
//            if (random == 1) {
//                hitShot.setHasUp(false);
//                Position shot = new Position(hitShot.getPos().x, hitShot.getPos().y + 1);
//                setAdjecentBoardFieldsFalse(getBoardFieldByPosition(shot.x, shot.y, hitMap), hitMap);
//                hitMap.remove(getBoardFieldByPosition(shot.x, shot.y, hitMap));
//                return shot;
//                //shoots down
//            } else {
//                hitShot.setHasDown(false);
//                Position shot = new Position(hitShot.getPos().x, hitShot.getPos().y - 1);
//                setAdjecentBoardFieldsFalse(getBoardFieldByPosition(shot.x, shot.y, hitMap), hitMap);
//                hitMap.remove(getBoardFieldByPosition(shot.x, shot.y, hitMap));
//                return shot;
//            }
//        } else if (hitShot.HasLeft() && hitShot.HasRight()) {
//            int random = rnd.nextInt(1);
//            //shoots right
//            if (random == 1) {
//                hitShot.setHasRight(false);
//                Position shot = new Position(hitShot.getPos().x + 1, hitShot.getPos().y);
//                setAdjecentBoardFieldsFalse(getBoardFieldByPosition(shot.x, shot.y, hitMap), hitMap);
//                hitMap.remove(getBoardFieldByPosition(shot.x, shot.y, hitMap));
//                return shot;
//                //shoot left
//            } else {
//                hitShot.setHasLeft(false);
//                Position shot = new Position(hitShot.getPos().x - 1, hitShot.getPos().y);
//                setAdjecentBoardFieldsFalse(getBoardFieldByPosition(shot.x, shot.y, hitMap), hitMap);
//                hitMap.remove(getBoardFieldByPosition(shot.x, shot.y, hitMap));
//                return shot;
//
//            }
//        } else if (hitShot.HasUp()) {
//            hitShot.setHasUp(false);
//            Position shot = new Position(hitShot.getPos().x, hitShot.getPos().y + 1);
//            setAdjecentBoardFieldsFalse(getBoardFieldByPosition(shot.x, shot.y, hitMap), hitMap);
//            hitMap.remove(getBoardFieldByPosition(shot.x, shot.y, hitMap));
//            return shot;
//
//        } else if (hitShot.HasDown()) {
//            hitShot.setHasDown(false);
//            Position shot = new Position(hitShot.getPos().x, hitShot.getPos().y - 1);
//            setAdjecentBoardFieldsFalse(getBoardFieldByPosition(shot.x, shot.y, hitMap), hitMap);
//            hitMap.remove(getBoardFieldByPosition(shot.x, shot.y, hitMap));
//            return shot;
//
//        } else if (hitShot.HasRight()) {
//            hitShot.setHasRight(false);
//            Position shot = new Position(hitShot.getPos().x + 1, hitShot.getPos().y);
//            setAdjecentBoardFieldsFalse(getBoardFieldByPosition(shot.x, shot.y, hitMap), hitMap);
//            hitMap.remove(getBoardFieldByPosition(shot.x, shot.y, hitMap));
//            return shot;
//
//        } else if (hitShot.HasLeft()) {
//            hitShot.setHasLeft(false);
//            Position shot = new Position(hitShot.getPos().x - 1, hitShot.getPos().y);
//            setAdjecentBoardFieldsFalse(getBoardFieldByPosition(shot.x, shot.y, hitMap), hitMap);
//            hitMap.remove(getBoardFieldByPosition(shot.x, shot.y, hitMap));
//            return shot;
//
//        } else {
//            return null;
//        }
//    }
//
//    void setAdjecentBoardFieldsFalse(BoardFields temp, List<BoardFields> hitMap
//    ) {
//        if (temp.HasUp()) {
//            temp.getBoardFieldsByPosition(temp.getPos().x, temp.getPos().y + 1, hitMap).setHasUp(false);
//        }
//        if (temp.HasDown()) {
//            temp.getBoardFieldsByPosition(temp.getPos().x, temp.getPos().y - 1, hitMap).setHasDown(false);
//        }
//        if (temp.HasLeft()) {
//            temp.getBoardFieldsByPosition(temp.getPos().x - 1, temp.getPos().y + 1, hitMap).setHasLeft(false);
//        }
//        if (temp.HasRight()) {
//            temp.getBoardFieldsByPosition(temp.getPos().x + 1, temp.getPos().y + 1, hitMap).setHasRight(false);
//
//        }
    }

    BoardFields getBoardFieldByPosition(int x, int y, List<BoardFields> hitMap
    ) {
        for (BoardFields o : hitMap) {
            if (o.getPos().x == x && o.getPos().y == y) {
                return o;
            }
        }
        return null;
    }
}
