package Model;

import lombok.Data;
// @Data could automaticlly generate getter /setters when compile successfully
@Data
public class Cask {
    //position of cask [x,y]
    private double x;
    private double y;
    // when touched Oil[0,0] will convert into fire.
    boolean isFire;

    //constructor
    public Cask(double x, double y) {
        this.x = x;
        this.y = y;
        isFire = false;
    }

    // move function
    public void move(int horizon, int vertical) {
        this.x += horizon;
        this.y += vertical;
    }
}
