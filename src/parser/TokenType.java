package parser;

/**
 *
 * @author NNI
 */
public enum TokenType {

    NUMBER, // number
    HEX_NUMBER, // hex number
    WORD, // variable
    TEXT, // text/string
    
    PRINT, // print
    PRINTLINE, // print new line
    IF, // if
    ELSE, // else
    WHILE, // while
    FOR, // for
    DO, // do while
    BREAK, // break
    CONTINUE, // continue
    FUNCTION, // function
    RETURN, // return
    CLASS, // class
    USE, // import/use
    
    PLUS, // +
    MINUS, // -
    STAR, // *
    SLASH, // /
    EQ, // =
    EQEQ, // ==
    EXCL, // !
    EXCLEQ, // !=
    LT, // <
    LTEQ, // <=
    GT, // >
    GTEQ, // >=
    
    BAR, // |
    BARBAR, // ||
    AMP, // &
    AMPAMP, // &&
    
    LPAREN, // (
    RPAREN, // )
    LBRACE, // {
    RBRACE, // }
    COMMA, // ,
    DOT, // .
    
    EOF // end of programm
}
