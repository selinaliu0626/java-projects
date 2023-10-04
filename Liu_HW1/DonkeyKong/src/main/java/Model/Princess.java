package Model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Princess extends Model.Base.Character {
    public Princess() {
        this.setName("Princess");
        log.info("Princess created {}", this);
    }
}
