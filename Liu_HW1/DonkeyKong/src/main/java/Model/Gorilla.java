package Model;

import Model.Base.Attackable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
public class Gorilla extends Model.Base.Character implements Attackable {

    Random random = new Random();

    public Gorilla() {
        this.setName("Gorilla");
        log.info("Gorilla created {}", this);
    }

    // Gorilla throw cask
    @Override
    public void attack(List<Cask> casks) {
        int attackWilling = random.nextInt(100);
        if (attackWilling > 80) {
            log.info("Gorilla attack, Throw cask at location {},{}", this.getX(), this.getY() + 1);
            casks.add(new Cask(this.getX(), this.getY() + 1));
        }
    }
}
