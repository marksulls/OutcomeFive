package com.retro.food.core;

import static org.junit.Assert.assertEquals;

import javax.measure.quantity.Mass;
import javax.measure.unit.NonSI;

import org.jscience.physics.amount.Amount;
import org.junit.Test;

import static javax.measure.unit.SI.*;
import static javax.measure.unit.NonSI.*;

public class WeightUnitTest {

    @Test
    public final void testToKilograms() {
//        assertEquals(Double.valueOf(0.45359237),WeightUnit.POUNDS.toKilograms(Double.valueOf(1)));
//        assertEquals(Double.valueOf(1),WeightUnit.POUNDS.toKilograms(Double.valueOf(2.20462262185)));
    }
    
    @Test
    public final void testToPounds() {
//        assertEquals(Double.valueOf(2.20462262185),WeightUnit.KILOGRAMS.toPounds(Double.valueOf(1)));
    }
}
