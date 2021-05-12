package com.deepoove.poi.tl.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.util.Preconditions;

public class PreconditionsTest {

    BiFunction<String, String, String> func = (cur, min) -> "Require Apach POI version at least " + min
            + ", but now is " + cur + ", please check the dependency of project.";

    @Test
    public void testCheckMinimumVersion1() {
        Preconditions.checkMinimumVersion("4.0.1", "4.0.0", func);
        Preconditions.checkMinimumVersion("4.0.0", "4.0.0", func);
        Preconditions.checkMinimumVersion("4.0.0", "3.16", func);
        Preconditions.checkMinimumVersion("3.16-beta1", "3.16", func);
        Preconditions.checkMinimumVersion("3.17", "3.16", func);
    }

    @Test
    public void testCheckMinimumVersion2() {
        assertThrows(IllegalStateException.class, () -> Preconditions.checkMinimumVersion("3.15-beta1", "3.16", func));
    }

    @Test
    public void testCheckMinimumVersion3() {
        assertThrows(IllegalStateException.class, () -> Preconditions.checkMinimumVersion("3.15-beta1", "4.0.0", func));

    }

    @Test
    public void testCheckMinimumVersion4() {
        assertThrows(IllegalStateException.class, () -> Preconditions.checkMinimumVersion("3.8", "4.0.0", func));
    }

    @Test
    public void testCheckMinimumVersion5() {
        assertThrows(IllegalStateException.class, () -> Preconditions.checkMinimumVersion("3.8-beta1", "4.0.0", func));
    }

    @Test
    public void testCheckMinimumVersion6() {
        assertThrows(IllegalStateException.class,
                () -> Preconditions.checkMinimumVersion("3.0.2-FINAL", "4.0.0", func));
    }

    @Test
    public void testCheckMinimumVersion7() {
        assertThrows(IllegalStateException.class, () -> Preconditions.checkMinimumVersion("3.16", "4.0.0", func));
    }

}
