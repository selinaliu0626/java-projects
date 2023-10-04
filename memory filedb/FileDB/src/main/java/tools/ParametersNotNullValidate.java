package tools;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class ParametersNotNullValidate {
    public static void validate(Object object, String message) {
        if (Objects.isNull(object)) {
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }
}
