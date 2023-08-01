/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import lib.Value;

/**
 *
 * @author NNI
 */
public final class ReturnStatement extends RuntimeException implements Statement {

    private final Expression expression;
    private Value result;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }
    
    
    public Value getResult() {
        return result;
    }
    
    @Override
    public void execute() {
        result = expression.eval();
        throw this;
    }

    @Override
    public String toString() {
        return "return";
    }
}