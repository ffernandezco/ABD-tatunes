package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCondition {
    @Test
    void ValueMeetsCondition() {
        // INT
        Condition condition = new Condition("columna", "=", "3");
        assertTrue(condition.ValueMeetsCondition("3", Column.DataType.INT));
        assertFalse(condition.ValueMeetsCondition("10", Column.DataType.INT));

        // DOUBLE
        Condition condition1 = new Condition("columna1", "<", "5.0");
        assertTrue(condition1.ValueMeetsCondition("4.9", Column.DataType.DOUBLE));
        assertFalse(condition1.ValueMeetsCondition("5.1", Column.DataType.DOUBLE));

        // STRING
        Condition condition2 = new Condition("columna2", ">", "asier");
        assertTrue(condition2.ValueMeetsCondition("belen", Column.DataType.STRING));
        assertFalse(condition2.ValueMeetsCondition("asier", Column.DataType.STRING));
    }
}
