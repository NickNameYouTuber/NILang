/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author NNI
 */

public final class FunctionStatement implements Statement {
    
    private final FunctionalExpression function;
    
    public FunctionStatement(FunctionalExpression function) {
        this.function = function;
    }

    @Override
    public void execute() {
        function.eval();
    }

    @Override
    public String toString() {
        return function.toString();
    }
}