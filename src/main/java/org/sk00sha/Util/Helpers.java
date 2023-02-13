package org.sk00sha.Util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Helpers {

    public static boolean convertStringToBoolean(String accusedBoolean){
        Map <String, Boolean> mapOfTruth=new HashMap<>();
        mapOfTruth.put("FALSE",false);
        mapOfTruth.put("TRUE",true);
        boolean finalValue=false;
        try{
            finalValue= mapOfTruth.get(accusedBoolean);
        }catch (Exception e){
            System.out.println(e);
        }
        return finalValue;
    }
}
