/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import lib.Value;
import java.util.ArrayList;
import java.util.List;
import lib.Function;
import lib.Functions;
import lib.UserDefinedFunction;
import lib.Variables;

/**
 *
 * @author NNI
 */
public final class ClassExpression implements Expression {

    private final String name;
    private final String funcName;
    private final List<Expression> arguments;
    
    public ClassExpression(String name, String funcName) {
        this.name = name;
        this.funcName = funcName;
        arguments = new ArrayList<>();
    }

    public ClassExpression(String name, String funcName, List<Expression> arguments) {
        this.name = name;
        this.funcName = funcName;
        this.arguments = arguments;
    }
    
    public void addArgument(Expression arg) {
        arguments.add(arg);
    }

    @Override
    public Value eval() {
        final int size = arguments.size();
        final Value[] values = new Value[size];
        for (int i = 0; i < size; i++) {
            values[i] = arguments.get(i).eval();
        }
        System.out.print(arguments);
        final Function function = Functions.get(funcName);
        if (function instanceof UserDefinedFunction) {
            final UserDefinedFunction userFunction = (UserDefinedFunction) function;
            if (size != userFunction.getArgsCount()) throw new RuntimeException("Args count mismatch");
            
            Variables.push();
            for (int i = 0; i < size; i++) {
                Variables.set(userFunction.getArgsName(i), values[i]);
            }
            final Value result = userFunction.execute(values);
            Variables.pop();
            return result;
        }
        return function.execute(values);
    }

    @Override
    public String toString() {
        return name + "(" + arguments.toString() + ")";
    }
}
