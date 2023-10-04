package Model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// move class
public enum MoveEnum {
    LEFT(-1.0),
    RIGHT(1.0),
    UP(1.0),
    DOWN(-1.0),
    LEFTJUMP(-0.5),
    RIGHTJUMP(0.5),
    JUMP(0);

    private final double value;



    private static Map<String, MoveEnum> moveLookup = Collections
            .unmodifiableMap(new HashMap<String, MoveEnum>() {
                {
                    put("LEFT", MoveEnum.LEFT);
                    put("RIGHT", MoveEnum.RIGHT);
                    put("UP", MoveEnum.UP);
                    put("DOWN", MoveEnum.DOWN);
                    put("LEFTJUMP", MoveEnum.LEFTJUMP);
                    put("RIGHTJUMP", MoveEnum.RIGHTJUMP);
                    put("JUMP", MoveEnum.JUMP);
                }
            });

    private MoveEnum(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public static MoveEnum getEnum(String val) {
        return moveLookup.get(val.toUpperCase());
    }
}