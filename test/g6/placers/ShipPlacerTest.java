package g6.placers;

import battleship.implementations.StartFleet;
import battleship.implementations.BoardImpl;
import battleship.interfaces.Position;
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
        assertEquals(placer.getShotMap().get(2, 8), 1);
    }

}
