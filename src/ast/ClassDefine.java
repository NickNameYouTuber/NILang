/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.List;
import lib.Classes;
import lib.Function;
import lib.Functions;
import lib.UserDefinedClass;
import lib.UserDefinedFunction;

/**
 *
 * @author NNI
 */
public class ClassDefine implements Statement{

    private final String name;
    private final Statement body;
    private final List<Function> argNames;
    
    public ClassDefine(String name, Statement body, List<Function> argNames) {
        this.argNames = argNames;
        this.name = name;
        this.body = body;
    }

    @Override
    public void execute() {
        Classes.set(name, new UserDefinedClass(body, argNames));
    }

    @Override
    public String toString() {
        return "\nclass " + name + "() {\n" + body.toString() + "\n}";
    }
    
}
