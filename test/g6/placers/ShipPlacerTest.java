package g6.placers;

import battleship.implementations.StartFleet;
import battleship.implementations.BoardImpl;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShipPlacerTest {
    
    public ShipPlacerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testIncoming() {
        Random rnd = new Random();
        int sizeX = 10;
        int sizeY = 10;
        int[] ships = {2, 3, 3, 4, 5};
        BoardImpl board = new BoardImpl(sizeX, sizeY);
        StartFleet fleet = new StartFleet(ships);
        Position position = new Position(2, 8);
        ShipPlacer placer = new ShipPlacer(sizeX, sizeY, rnd);
        placer.placeShips(fleet, board);
        placer.incoming(position);
        placer.getShotMap().get(2, 8);
        assertEquals(placer.getShotMap().get(2, 8), 1);
    }
    
    @Test
    public void testCheckIfOtherShips() {
        Random rnd = new Random();
        int sizeX = 5;
        int sizeY = 1;
        int[] ships = {5};
        //placerer et skib over hele boardet
        StartFleet fleet = new StartFleet(ships);
        BoardImpl board = new BoardImpl(sizeX, sizeY);
        ShipPlacer placer = new ShipPlacer(sizeX, sizeY, rnd);
        placer.placeShips(fleet, board);

        //kontrollerer at checkIfOtherShips returnerer false
        int[] ships2 = {5};
        StartFleet fleet2 = new StartFleet(ships2);
        Ship s = fleet2.getShip(0);
        int x = rnd.nextInt(sizeX - (s.size() - 1));
        int y = rnd.nextInt(sizeY);
        assertEquals(placer.checkIfOtherShips(s, x, y, false), false);
        assertEquals(placer.checkIfOtherShips(s, x, y, true), false);
    }
    
    @Test
    
    public void testSortHighestFirst() {
        Random rnd = new Random();
        int sizeX = 10;
        int sizeY = 10;
        ArrayList<Ship> plainShips = new ArrayList<>();
        int[] ships = {1, 2, 3, 4, 5};
        StartFleet fleet = new StartFleet(ships);
        ShipPlacer placer = new ShipPlacer(sizeX, sizeY, rnd);
        for (Ship s : fleet) {
            plainShips.add(s);
        }
        assertEquals(plainShips.get(0).size(), 1);
        assertEquals(plainShips.get(4).size(), 5);
        placer.sortHighestFirst(plainShips);
        assertEquals(plainShips.get(0).size(), 5);
        assertEquals(plainShips.get(4).size(), 1);
    }
}
