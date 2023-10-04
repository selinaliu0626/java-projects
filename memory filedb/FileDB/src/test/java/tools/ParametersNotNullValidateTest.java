package tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParametersNotNullValidateTest {

    @Test
    void validateFailedTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ParametersNotNullValidate.validate(null, ""));
    }

    @Test
    void validateSuccessTest() {
        ParametersNotNullValidate.validate("1", "2");
    }
}