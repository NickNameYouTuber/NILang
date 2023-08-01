/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import lib.Value;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author NNI
 */
public class Classes {
    private static final NumberValue ZERO = new NumberValue(0);
    private static final List<Function> functions = new ArrayList<>();
    private static final Map<String, Class> classes;
    
    static {
        classes = new HashMap<>();

    }
    
    public static boolean isExists(String key) {
        return classes.containsKey(key);
    }
    
    public static Class get(String key) {
        if (!isExists(key)) throw new RuntimeException("Unknown class " + key);
        System.out.println(key);
        return classes.get(key);
    }
    
    public static void set(String key, Class clas) {
        classes.put(key, clas);
    }
}
