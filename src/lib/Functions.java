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
import lib.Value;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author NNI
 */
public class Functions {
    private static final NumberValue ZERO = new NumberValue(0);
    private static final Map<String, Function> functions;
    
    static {
        functions = new HashMap<>();
        functions.put("sin", new Function() {

            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("One arg expected");
                return new NumberValue(Math.sin(args[0].asNumber()));
            }
        });
        functions.put("cos", new Function() {

            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("One arg expected");
                return new NumberValue(Math.cos(args[0].asNumber()));
            }
        });
        functions.put("echo", new Function() {

            @Override
            public Value execute(Value... args) {
                for (Value arg : args) {
                    System.out.println(arg.asString());
                }
                return ZERO;
            }
        });
        functions.put("read", new Function() {

            @Override
            public Value execute(Value... args) {
                if (args.length != 1)
                    throw new RuntimeException("One arg expected");

                String filePath = args[0].asString();
                StringBuilder content = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                        content.append("\n");
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error reading file: " + e.getMessage());
                }

                return new StringValue(content.toString());
            }
        });
        
        functions.put("write", new Function() {

            @Override
            public Value execute(Value... args) {
                if (args.length != 2)
                    throw new RuntimeException("Two args expected");

                String filePath = args[0].asString();
                String content = args[1].asString();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    writer.write(content);
                } catch (IOException e) {
                    throw new RuntimeException("Error writing to file: " + e.getMessage());
                }

                return new NumberValue(0);
            }
        });
    }
    
    public static boolean isExists(String key) {
        return functions.containsKey(key);
    }
    
    public static Function get(String key) {
        if (!isExists(key)) throw new RuntimeException("Unknown function " + key);
        System.out.println(key);
        return functions.get(key);
    }
    
    public static void set(String key, Function function) {
        functions.put(key, function);
    }
}
