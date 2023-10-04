import Model.*;
import lombok.Data;


@Data
public class Canvas {
    // ladder locations
    // 1: ladder's bot
    // 2: ladder's top
    // 3: broken ladder's bot
    // 4: broken ladder's top
    private int[][] ladders;
    // 0: floor with 0 slope
    // 1: floor with 1 slope
    // -1: floor with -1 slope
    // 2: no floor
    private int[][] floors;

    public Canvas(int[][] ladders, int[][] floors, Mario mario, Gorilla gorilla, Princess princess) {
        this.ladders = ladders;
        this.floors = floors;

        // new Canvas starts
        // init Mario's location
        // [0, 0] is kept for Oil.
        mario.setX(0);
        mario.setY(1);

        gorilla.setX(floors.length - 2);
        gorilla.setY(0);

        princess.setX(floors.length - 1);
        princess.setY(1);

    }
}
