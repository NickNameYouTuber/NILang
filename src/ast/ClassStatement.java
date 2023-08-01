/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author NNI
 */

public final class ClassStatement implements Statement {
    
    private final ClassExpression clas;
    
    public ClassStatement(ClassExpression clas) {
        this.clas = clas;
    }

    @Override
    public void execute() {
        clas.eval();
    }

    @Override
    public String toString() {
        return clas.toString();
    }
}