/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author NNI
 */
public final class ContinueStatement extends RuntimeException implements Statement {

    @Override
    public void execute() {
        throw this;
    }

    @Override
    public String toString() {
        return "continue";
    }
}