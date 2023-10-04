package Model;

import Model.Base.Attackable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static Model.MoveEnum.LEFTJUMP;
import static Model.MoveEnum.RIGHTJUMP;

@Data
@AllArgsConstructor
@Slf4j
public class Mario extends Model.Base.Character implements Attackable {

    private boolean isAlive;
    private boolean hasWeapon;

    private boolean isJumping;

    private int jumpDirection;

    public Mario() {
        this.setName("Mario");
        this.isAlive = true;
        log.info("Mario created {}", this);
    }
    //overloading constructor will generate by @AllArgsConstructor

    @Override
    public void attack(List<Cask> casks) {
        System.out.println("Mario Attacking");
        log.info("Mario attacking");
        if (hasWeapon) {
            casks.removeIf(x -> withinRange(x) && !x.isFire);
            log.info("Mario attacking successfully");
            return;
        }
        System.out.println("No weapon");
        log.info("Mario attacking failed, No weapon.");
    }

    // overloading attack function
    //sometimes player may press attack button even there are no casks.
    public void attack() {
        log.info("No Casks, do nothing");
    }


    //move function
    public void move(MoveEnum moveEnum) {
        log.info("Mario Moving from {}, {} with MoveEnum: {}", this.getX(), this.getY(), moveEnum);
        switch (moveEnum) {
            case LEFT:
            case RIGHT:
                this.setY(this.getY() + moveEnum.getValue());
                break;
            case UP:
            case DOWN:
                this.setX(this.getX() + moveEnum.getValue());
                break;
            case LEFTJUMP:
                this.isJumping = true;
                this.jumpDirection = -1;
                this.setX(this.getX() + 0.5);
                this.setY(this.getY() + moveEnum.getValue());
                break;
            case RIGHTJUMP:
                this.isJumping = true;
                this.jumpDirection = 1;
                this.setX(this.getX() + 0.5);
                this.setY(this.getY() + moveEnum.getValue());
                break;
            case JUMP:
                this.isJumping = true;
                this.jumpDirection = 0;
                this.setX(this.getX() + 0.5);
                break;
            default:
                log.error("Invalid moveEnum, {}", moveEnum);
                throw new IllegalArgumentException("Invalid moveEnum:" + moveEnum);
        }
    }

    //while jump, we need to have two steps, jump and finish jump
    public void finishJumping() {
        switch (jumpDirection) {
            case 1:
                this.setY(this.getY() + RIGHTJUMP.getValue());
                break;
            case -1:
                this.setY(this.getY() + LEFTJUMP.getValue());
            case 0:
            default:
                break;

        }
        this.setX(this.getX() - 0.5);
        this.jumpDirection = 0;
        this.isJumping = false;
        log.info("Mario finished jump to {},{}", this.getX(), this.getY());
    }

// determine that is any cask in the range of mario's attack range
    private boolean withinRange(Cask cask) {
        // in the left/right next to mario
        if (cask.getX() == this.getX() && Math.abs(cask.getY() - this.getY()) == 1) {
            log.info("Can attack cask: {}", cask);
            return true;
        }
        // in the up/down to mario
        //otherwise false
        return cask.getY() == this.getY() && Math.abs(cask.getX() - this.getX()) == 1;
    }

}
