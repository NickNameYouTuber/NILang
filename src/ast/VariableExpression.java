package ast;

import lib.Value;
import lib.Variables;

/**
 *
 * @author NNI
 */
public final class VariableExpression implements Expression {
    
    private final String name;
    
    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value eval() {
        if (!Variables.isExists(name)) throw new RuntimeException("Constant does not exists");
        return Variables.get(name);
    }

    @Override
    public String toString() {
//        return String.format("%s [%f]", name, Constants.get(name));
        return String.format("%s", name);
    }
}
