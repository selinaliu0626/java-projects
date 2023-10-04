package Model;

import lombok.extern.slf4j.Slf4j;
// @ slf4j log library


//this is use singleton model to keep in the same game there is only one Mario, gorilla and princess
@Slf4j
public class CharacterFactory {
    private static Mario mario;
    private static Gorilla gorilla;
    private static Princess princess;

    public static Mario getMario() {
        if (mario == null) {
            mario = new Mario();
        }
        return mario;
    }

    public static Gorilla getGorilla() {
        if (gorilla == null) {
            gorilla = new Gorilla();
        }
        return gorilla;
    }

    public static Princess getPrincess() {
        if (princess == null) {
            princess = new Princess();
        }
        return princess;
    }
}
