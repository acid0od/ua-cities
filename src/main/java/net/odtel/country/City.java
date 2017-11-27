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
    String name;
    String aria;
    String iq;
    String population;
    String square;
    String populationDensity;
    
    @Override
    public String toString() {
        return name + ", " + aria + ", " + iq + ", " + toNumber(population) + ", " + square + ", " + populationDensity + "";
    }
    
    private String toNumber(String baseString) {
        List<String> tokensList = new ArrayList<>();
        boolean inQuotes = true;
        StringBuilder b = new StringBuilder();
        for (char c : baseString.toCharArray()) {
            if (c >= '0' && c <= '9' && inQuotes) {
                b.append(c);
            }
            if (c == '(') {
                inQuotes = false;
            }
        }
        
        return b.toString();
    }
}
