import Model.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Data
public class DonkeyKong {

    private static final String[] commands = {"attack", "left", "right", "up", "down", "jump", "leftJump", "rightJump"};

    private Mario mario = CharacterFactory.getMario();

    private Gorilla gorilla = CharacterFactory.getGorilla();

    private Princess princess = CharacterFactory.getPrincess();

    private Canvas canvas;

    private List<Cask> casks;

    private Oil oil;

    private Weapon weapon;

    public void start() {
        log.info("Game Starting");
        // define all elements in this game
        // ladder locations
        // 1: ladder's bot
        // 2: ladder's top
        // 3: broken ladder's bot
        // 4: broken ladder's top
        int[][] ladders = {{0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 4, 1, 0, 0, 0, 0, 0, 2, 0},
                {0, 2, 0, 3, 0, 2, 1, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 4, 1, 0, 0, 0, 0, 0, 3, 2, 0},
                {0, 2, 0, 0, 2, 3, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 1, 4, 0, 0, 1, 0, 0, 2, 0},
                {0, 2, 2, 2, 2, 0, 0, 0, 2, 0, 0, 0, 0}};
        // 0: floor with 0 slope
        // 1: floor with 1 slope
        // -1: floor with -1 slope
        // 2: no floor
        int[][] floors = {{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
                {2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
                {2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2}};


        oil = new Oil(0, 0);

        weapon = new Weapon(3, 0);

        canvas = new Canvas(ladders, floors, mario, gorilla, princess);
        casks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        //game begins
        while (mario.isAlive()) {
            System.out.println("Mario current location: " + mario.getX() + "," + mario.getY());
            if (checkWin(mario, princess)) {
                log.info("Mario win");
                System.out.println("Mario win");
                break;
            }
            if (mario.isJumping()) {
                mario.finishJumping();
            } else {
                // check if floor is empty, if so mario will fall
                if (floors[(int) mario.getX()][(int) mario.getY()] == 2) {
                    log.info("Empty floor, Mario falling.");
                    mario.setX(mario.getX() - 1);
                } else {
                    System.out.println("Enter Move Command\n0: Attack: \n1: Left\n2: Right\n3: Up\n4: Down\n5: Jump\n6: LeftJump\n7: RightJump\n8: ExitGame");
                    int commandIndex = scanner.nextInt();
                    //check invalid command
                    if (commandIndex < 0 || commandIndex > 8) {
                        System.out.println("Invalid commandIndex[0-8]. Please enter again");
                        continue;
                    }
                    //exit command
                    if (commandIndex == 8) {
                        log.info("User exit game.");
                        System.out.println("Exit game.");
                        break;
                    }
                    //broken ladders, mario could not climb
                    if ((commandIndex == 3 && ladders[(int) mario.getX()][(int) mario.getY()] != 1) || (commandIndex == 4 && ladders[(int) mario.getX()][(int) mario.getY()] != 2)) {
                        System.out.println("No ladder here. Please enter again");
                        continue;
                    }
                    System.out.println("Executing Command: " + commands[commandIndex]);
                    // attack
                    if (commandIndex == 0) {
                        mario.attack(casks);
                    } else { // move
                        System.out.println("Mario Moving");
                        mario.move(MoveEnum.getEnum(commands[commandIndex]));
                    }
                }
            }
            //check whether Mario could get weapon or not
            if (mario.getX() == weapon.getX() && mario.getY() == weapon.getY()) {
                log.info("Mario get weapon.");
                System.out.println("Mario get weapon.");
                mario.setHasWeapon(true);
            }
            //update canvas
            syncCanvas(floors, ladders, casks, oil, mario);

            gorilla.attack(casks);
            // check whether Mario die or not
            if (checkCollisionAndOutSpace(casks, floors, mario)) {
                log.info("Mario die, game over");
                System.out.println("Mario die, game over");
            }
        }
        scanner.close();
    }

 // checkCollisionAndOutSpace
    //is mario has same position with cask or will fall
    boolean checkCollisionAndOutSpace(List<Cask> casks, int[][] floors, Mario mario) {
        for (Cask cask : casks) {
            // if mario and cask in same position, that means mario will die
            if (cask.getX() == mario.getX() && cask.getY() == mario.getY()) {
                mario.setAlive(false);
                return true;
            }
        }
        // mario falls out of bounds
        if (mario.getX() < 0 || mario.getX() >= floors.length) {
            mario.setAlive(false);
            return true;
        }
        return mario.getY() < 0 || mario.getY() >= floors[(int) mario.getX()].length;
    }



    //check some circumstances then synchronized canvas
    void syncCanvas(int[][] floors, int[][] ladders, List<Cask> casks, Oil oil, Mario mario) {
        log.info("Casks syncing.");
        for (Cask cask : casks) {
            // fire can clime ladder
            if (cask.isFire()) {
                double xDiff = mario.getX() - cask.getX();
                double yDiff = mario.getY() - cask.getY();
                if (Math.abs(xDiff) > Math.abs(yDiff) && (
                        //could take ladder up or down
                        (xDiff > 0 && (ladders[(int) cask.getX()][(int) cask.getY()] == 1 || ladders[(int) cask.getX()][(int) cask.getY()] == 3)) ||
                                (xDiff < 0 && (ladders[(int) cask.getX()][(int) cask.getY()] == 2 || ladders[(int) cask.getX()][(int) cask.getY()] == 4))
                )) {
                    // no ladder, choose a way to move which is closer to the mario position
                    if (xDiff > 0) {
                        cask.setX(cask.getX() + 1);
                    } else {
                        cask.setX(cask.getX() - 1);
                    }
                } else {
                    if (yDiff > 0) {
                        cask.setY(cask.getY() + 1);
                    } else {
                        cask.setY(cask.getY() - 1);
                    }
                }
                // not fire, but touched the oil
            } else if (cask.getX() == oil.getX() && cask.getY() == oil.getY()) {
                // oil fired cask
                cask.setFire(true);
            } else {// else move according the slope
                int slope = floors[(int) cask.getX()][(int) cask.getY()];
                // down to lower floor.
                if (slope == 2) {
                    cask.setX(cask.getX() - 1);
                } else {
                    // roll by slope.
                    cask.setY(cask.getY() + slope);
                }
            }
        }
    }

    // check win function
    // if mario get the princess's position, win
    boolean checkWin(Mario mario, Princess princess) {
        if (mario.getX() == princess.getX() && mario.getY() == princess.getY()) {
            return true;
        }
        return false;
    }
}
