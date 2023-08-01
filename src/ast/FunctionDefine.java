/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.List;
import lib.Functions;
import lib.UserDefinedFunction;

/**
 *
 * @author NNI
 */
public class FunctionDefine implements Statement{

    private final String name;
    private final List<String> argNames;
    private final Statement body;
    
    public FunctionDefine(String name, List<String> argNames, Statement body) {
        this.name = name;
        this.argNames = argNames;
        this.body = body;
    }

    @Override
    public void execute() {
        Functions.set(name, new UserDefinedFunction(argNames, body));
    }

    @Override
    public String toString() {
        return "def (" + argNames.toString() + ") " + body.toString();
    }
    
}
