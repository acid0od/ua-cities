/* ===========================================================================
 * Copyright (c) 2015 Comcast Corp. All rights reserved.
 * ===========================================================================
 *
 * Author: Alexander Ievstratiev
 * Created: 11/27/2017  3:52 PM
 */
package net.odtel.country;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class City {
    private String name;
    private String aria;
    private String iq;
    private String population;
    private String square;
    private String populationDensity;
    
    @Override
    public String toString() {
        return name + ", " + aria + ", " + iq + ", " + toNumber(population) + ", " + square +  " (" + toNumber(square) + "), " + populationDensity + "";
    }
    
    public int getPop() {
        try {
            return Integer.valueOf(toNumber(population));
        } catch (Exception e) {
            return 0;
        }
    }
    
    public float getA() {
        try {
            return Float.valueOf(toNumber(square));
        } catch (Exception e) {
            return 0f;
        }
    }
    
    private String toNumber(String baseString) {
        boolean inQuotes = true;
        StringBuilder b = new StringBuilder();
        for (char c : baseString.toCharArray()) {
            if (c == '.' && inQuotes) {
                b.append(".");
            }
    
            if (c == ',' && inQuotes) {
                b.append(".");
            }
    
            if (c >= '0' && c <= '9' && inQuotes) {
                b.append(c);
            }
            if (c == '(' || c == '[') {
                inQuotes = false;
            }
        }
        
        return b.toString();
    }
}
