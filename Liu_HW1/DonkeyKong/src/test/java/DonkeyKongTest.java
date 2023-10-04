import Model.Cask;
import Model.CharacterFactory;
import Model.Mario;
import Model.Princess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class DonkeyKongTest {
    private static DonkeyKong donkeyKong;
    private static int[][] floors = {{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2}};

    @BeforeAll
    static void setUp() {
        donkeyKong = new DonkeyKong();
    }

    @Test
    public void checkCollisionAndOutSpaceTrue() {
        Cask cask = new Cask(2, 2);
        Mario mario = CharacterFactory.getMario();
        mario.setX(2);
        mario.setY(2);
        donkeyKong.setMario(mario);
        boolean collision = donkeyKong.
                checkCollisionAndOutSpace(Collections.singletonList(cask),
                        floors, mario);

        Assertions.assertTrue(collision);
    }

    @Test
    public void checkCollisionAndOutSpaceFalse() {
        Cask cask = new Cask(0, 0);
        Mario mario = CharacterFactory.getMario();
        mario.setX(0);
        mario.setY(1);
        donkeyKong.setMario(mario);
        boolean collision = donkeyKong.
                checkCollisionAndOutSpace(Collections.singletonList(cask),
                        floors, mario);

        Assertions.assertFalse(collision);
    }

    @Test
    public void checkWinTrue() {
        Princess princess = new Princess();
        Mario mario = new Mario();
        boolean isWin = donkeyKong.checkWin(mario, princess);
        Assertions.assertTrue(isWin);
    }

    @Test
    public void checkWinFalse() {
        Princess princess = new Princess();
        Mario mario = new Mario();
        mario.setX(2);
        boolean isWin = donkeyKong.checkWin(mario, princess);
        Assertions.assertFalse(isWin);
    }
}
