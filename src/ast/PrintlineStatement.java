package ast;

/**
 *
 * @author NNI
 */
public final class PrintlineStatement implements Statement {
    
    private final Expression expression;

    public PrintlineStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        System.out.println(expression.eval());
    }

    @Override
    public String toString() {
        return "print " + expression;
    }
}
