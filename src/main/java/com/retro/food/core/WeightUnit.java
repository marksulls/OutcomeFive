package com.retro.food.core;

import static javax.measure.unit.SI.*;
import static javax.measure.unit.NonSI.*;

public enum WeightUnit {
    POUNDS {
        public double toKilograms(double d) {
            return POUND.getConverterTo(KILOGRAM).convert(d);
        }
        public double toGrams(double d) {
            return POUND.getConverterTo(GRAM).convert(d);
        }
     },
     KILOGRAMS {
         public double toPounds(double d) {
             return KILOGRAM.getConverterTo(POUND).convert(d);
         }
         public double toGrams(double d) {
             return KILOGRAM.getConverterTo(GRAM).convert(d);
         }
     },
     OUNCES {
         public double toPounds(double d) {
             return OUNCE.getConverterTo(POUND).convert(d);
         }
         public double toKilograms(double d) {
             return OUNCE.getConverterTo(KILOGRAM).convert(d);
         }
         public double toGrams(double d) {
             return OUNCE.getConverterTo(GRAM).convert(d);
         }
     };
     
     public double toKilograms(double d) {
         throw new AbstractMethodError();
     }
     public double toPounds(double d) {
         throw new AbstractMethodError();
     }
     public double toGrams(double d) {
         throw new AbstractMethodError();
     }
     public double toOunces(double d) {
         throw new AbstractMethodError();
     }
}
