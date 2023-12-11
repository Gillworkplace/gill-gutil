
package com.gill.gutil.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jakarta.validation.constraints.Min;

class ValidatorUtilTest {

    static class Bean {

        @Min(1)
        private final int age;

        public Bean(int age) {
            this.age = age;
        }
    }

    @Test
    void testValidate_retFalse() {
        assertFalse(ValidatorUtil.validate(new Bean(0)));
    }

    @Test
    void testValidate_retTrue() {
        assertTrue(ValidatorUtil.validate(new Bean(1)));
    }

    @Test
    void testValidateWithStr() {
        assertFalse(ValidatorUtil.validateWithStr(new Bean(0)).isEmpty());
    }
}
