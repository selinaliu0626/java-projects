import Model.Gorilla;
import Model.Mario;
import Model.Princess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CanvasTest {
    private static Mario mario;
    private static Gorilla gorilla;
    private static Princess princess;
    private static Canvas canvas;
    private static int[][] ladders;
    private static int[][] floors = {{0}, {0}, {0}, {0}, {0}, {0}};

    @BeforeAll
    static void setUp() {
        mario = new Mario();
        gorilla = new Gorilla();
        princess = new Princess();
        canvas = new Canvas(ladders, floors, mario, gorilla, princess);
    }

    @Test
    public void test() {
        Assertions.assertEquals(ladders, canvas.getLadders());
        Assertions.assertEquals(floors, canvas.getFloors());
        Assertions.assertEquals(0, mario.getX());
        Assertions.assertEquals(1, mario.getY());
        Assertions.assertEquals(4, gorilla.getX());
        Assertions.assertEquals(0, gorilla.getY());
        Assertions.assertEquals(5, princess.getX());
        Assertions.assertEquals(1, princess.getY());
    }
}
