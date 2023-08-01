package parser;

import ast.PrintStatement;
import ast.AssignmentStatement;
import ast.BinaryExpression;
import ast.BlockStatement;
import ast.BreakStatement;
import ast.ClassDefine;
import ast.ClassExpression;
import ast.ClassStatement;
import ast.ConditionalExpression;
import ast.ContinueStatement;
import ast.DoWhileStatement;
import ast.VariableExpression;
import ast.Expression;
import ast.ForStatement;
import ast.FunctionDefine;
import ast.FunctionStatement;
import ast.FunctionalExpression;
import ast.IfStatement;
import ast.PrintlineStatement;
import ast.ReturnStatement;
import ast.ValueExpression;
import ast.Statement;
import ast.UnaryExpression;
import ast.WhileStatement;
import java.util.ArrayList;
import java.util.List;
import lib.Function;

/**
 *
 * @author NNI
 */
public final class Parser {
    
    private static final Token EOF = new Token(TokenType.EOF, "");

    private final List<Token> tokens;
    private final int size;
    
    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }
    
    public Statement parse() {
        final BlockStatement result = new BlockStatement();
        while (!match(TokenType.EOF)) {
            result.add(statement());
        }
        return result;
    }
    
    private Statement block() {
        final BlockStatement block = new BlockStatement();
        consume(TokenType.LBRACE);
        while (!match(TokenType.RBRACE)) {
            Statement st = statement();
            block.add(st);
            if (st.toString().contains("def")){
                System.out.print("FIND FUNCTION");
            }
        }
        return block;
    }
    
    private Statement statementOrBlock() {
        if (get(0).getType() == TokenType.LBRACE) return block();
        return statement();
    }
    
    private Statement statement() {
        if (match(TokenType.PRINT)) {
            return new PrintStatement(expression());
        }
        if (match(TokenType.PRINTLINE)) {
            return new PrintlineStatement(expression());
        }
        if (match(TokenType.IF)) {
            return ifElse();
        }
        if (match(TokenType.DO)) {
            return doWhileStatement();
        }
        if (match(TokenType.WHILE)) {
            return whileStatement();
        }
        if (match(TokenType.FOR)) {
            return forStatement();
        }
        if (match(TokenType.BREAK)) {
            return new BreakStatement();
        }
        if (match(TokenType.CONTINUE)) {
            return new ContinueStatement();
        }
        if (match(TokenType.RETURN)) {
            return new ReturnStatement(expression());
        }
        if (match(TokenType.FUNCTION)) {
            return functionDefine();
        }
        if (match(TokenType.CLASS)) {
            return classDefine();
        }
        if (get(0).getType() == TokenType.WORD && get(1).getType() == TokenType.LPAREN && get(2).getType() == TokenType.RPAREN && get(3).getType() == TokenType.DOT) {
            System.out.print("aaaaaaaaaaaaaaaaaaaaaaa");
            return new ClassStatement(clas());
        }
        if (get(0).getType() == TokenType.WORD && get(1).getType() == TokenType.LPAREN) {
            return new FunctionStatement(function());
        }
        return assignmentStatement();
    }
    
    private Statement assignmentStatement() {
        // WORD EQ
        final Token current = get(0);
        if (match(TokenType.WORD) && get(0).getType() == TokenType.EQ) {
            final String variable = current.getText();
            consume(TokenType.EQ);
            return new AssignmentStatement(variable, expression());
        }
        throw new RuntimeException("Unknown statement");
    }
    
    private Statement ifElse() {
        final Expression condition = expression();
        final Statement ifStatement = statementOrBlock();
        final Statement elseStatement;
        if (match(TokenType.ELSE)) {
            elseStatement = statementOrBlock();
        } else {
            elseStatement = null;
        }
         return new IfStatement(condition, ifStatement, elseStatement);
    }
    
    private Statement whileStatement() {
        final Expression condition = expression();
        final Statement statement = statementOrBlock();
        return new WhileStatement(condition, statement);
    }
    
    private Statement doWhileStatement() {
        final Statement statement = statementOrBlock();
        consume(TokenType.WHILE);
        final Expression condition = expression();
        return new DoWhileStatement(condition, statement);
    }
    
    private Statement forStatement() {
        final Statement initialization = assignmentStatement();
        consume(TokenType.COMMA);
        final Expression termination = expression();
        consume(TokenType.COMMA);
        final Statement increment = assignmentStatement();
        final Statement statement = statementOrBlock();
        return new ForStatement(initialization, termination, increment, statement);
    }
    
    private FunctionalExpression function() {
        final String name = consume(TokenType.WORD).getText();
        consume(TokenType.LPAREN);
        final FunctionalExpression function = new FunctionalExpression(name);
        while (!match(TokenType.RPAREN)) {
            function.addArgument(expression());
            match(TokenType.COMMA);
        }
        return function;
    }
    
    private ClassExpression clas() {
        final String name = consume(TokenType.WORD).getText();
        System.out.print(get(0));
        consume(TokenType.LPAREN);
        System.out.print(get(0));
        consume(TokenType.RPAREN);
        System.out.print(get(0));
        
        if (get(0).getType() != TokenType.DOT) {
            throw new RuntimeException("No dot");
        }
        consume(TokenType.DOT);
        System.out.print(get(0));
        final String funcName = consume(TokenType.WORD).getText();
        System.out.print(get(0));
        consume(TokenType.LPAREN);
        System.out.print(get(0));
        
        final ClassExpression clas = new ClassExpression(name, funcName);
        
        while (!match(TokenType.RPAREN)) {
            System.out.print(get(0));
            clas.addArgument(expression());
            match(TokenType.COMMA);
        }
        System.out.print(get(0));
        
        return clas;
    }
    
    private FunctionDefine functionDefine() {
        final String name = consume(TokenType.WORD).getText(); 
        consume(TokenType.LPAREN);
        final List<String> argNames = new ArrayList<>();
        while (!match(TokenType.RPAREN)) {
            argNames.add(consume(TokenType.WORD).getText()); 
            match(TokenType.COMMA);
        }
        final Statement body = statementOrBlock(); 
        return new FunctionDefine(name, argNames, body);
    }
    
    private ClassDefine classDefine() {
        final String name = consume(TokenType.WORD).getText(); 
        consume(TokenType.LPAREN);
        consume(TokenType.RPAREN);
        final List<Function> argNames = new ArrayList<>();

        final Statement body = statementOrBlock(); 
        System.out.print("\nbody " + body + "\nbody ");
        String[] str = body.toString().split("\n");
        System.out.println("!НАЧАЛО!");
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < str[i].length() - 3; j++) {
                if (str[i].regionMatches(j, "def", 0, 3)) {
                    System.out.println("Найдена функция");
                }
            }
        }
        return new ClassDefine(name, body, argNames);
    }
    
    private Expression expression() {
        return logicalOr();
    }
    
    private Expression logicalOr() {
        Expression result = logicalAnd();
        
        while (true) {
            if (match(TokenType.BARBAR)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.OR, result, logicalAnd());
                continue;
            }
            break;
        }
        
        return result;
    }
    
    private Expression logicalAnd() {
        Expression result = equality();
        
        while (true) {
            if (match(TokenType.AMPAMP)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.AND, result, equality());
                continue;
            }
            break;
        }
        
        return result;
    }
    
    private Expression equality() {
        Expression result = conditional();
        
        if (match(TokenType.EQEQ)) {
            return new ConditionalExpression(ConditionalExpression.Operator.EQUALS, result, conditional());
        }
        if (match(TokenType.EXCLEQ)) {
            return new ConditionalExpression(ConditionalExpression.Operator.NOT_EQUALS, result, conditional());
        }
        
        return result;
    }
    
    private Expression conditional() {
        Expression result = additive();
        
        while (true) {
            if (match(TokenType.LT)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LT, result, additive());
                continue;
            }
            if (match(TokenType.LTEQ)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LTEQ, result, additive());
                continue;
            }
            if (match(TokenType.GT)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GT, result, additive());
                continue;
            }
            if (match(TokenType.GTEQ)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GTEQ, result, additive());
                continue;
            }
            break;
        }
        
        return result;
    }
    
    private Expression additive() {
        Expression result = multiplicative();
        
        while (true) {
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression('+', result, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression('-', result, multiplicative());
                continue;
            }
            break;
        }
        
        return result;
    }
    
    private Expression multiplicative() {
        Expression result = unary();
        
        while (true) {
            // 2 * 6 / 3 
            if (match(TokenType.STAR)) {
                result = new BinaryExpression('*', result, unary());
                continue;
            }
            if (match(TokenType.SLASH)) {
                result = new BinaryExpression('/', result, unary());
                continue;
            }
            break;
        }
        
        return result;
    }
    
    private Expression unary() {
        if (match(TokenType.MINUS)) {
            return new UnaryExpression('-', primary());
        }
        if (match(TokenType.PLUS)) {
            return primary();
        }
        return primary();
    }
    
    private Expression primary() {
        final Token current = get(0);
        if (match(TokenType.NUMBER)) {
            return new ValueExpression(Double.parseDouble(current.getText()));
        }
        if (match(TokenType.HEX_NUMBER)) {
            return new ValueExpression(Long.parseLong(current.getText(), 16));
        }
        if (get(0).getType() == TokenType.WORD && get(1).getType() == TokenType.LPAREN && get(2).getType() == TokenType.RPAREN && get(3).getType() == TokenType.DOT) {
            return clas();
        }
        if (get(0).getType() == TokenType.WORD && get(1).getType() == TokenType.LPAREN) {
            return function();
        }
        if (match(TokenType.WORD)) {
            return new VariableExpression(current.getText());
        }
        if (match(TokenType.TEXT)) {
            return new ValueExpression(current.getText());
        }
        if (match(TokenType.LPAREN)) {
            Expression result = expression();
            match(TokenType.RPAREN);
            return result;
        }
        throw new RuntimeException("Unknown expression");
    }
    
    private Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) throw new RuntimeException("Token " + current + " doesn't match " + type);
        pos++;
        return current;
    }
    
    private boolean match(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) return false;
        pos++;
        return true;
    }
    
    private Token get(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= size) return EOF;
        return tokens.get(position);
    }

}
