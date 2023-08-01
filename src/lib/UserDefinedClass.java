/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import ast.ReturnStatement;
import ast.Statement;
import java.util.List;

/**
 *
 * @author NNI
 */
public final class UserDefinedClass implements Class {
    
    private final List<Function> argNames;
    private final Statement body;
    
    public UserDefinedClass(Statement body, List<Function> argNames) {
        this.argNames = argNames;
        this.body = body;
    }
    
    public int getArgsCount() {
        return argNames.size();
    }
    
    public Function getArgsName(int index) {
        if (index < 0 || index >= getArgsCount()) throw new RuntimeException("sss");
        return argNames.get(index);
    }

    @Override
    public Value execute() {
        try {
            body.execute();
            return NumberValue.ZERO;
        } catch (ReturnStatement rt) {
            return rt.getResult();
        }
    }
}