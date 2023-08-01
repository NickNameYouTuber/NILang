package nilang;



import ast.Statement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import parser.Lexer;
import parser.Parser;
import parser.Token;

/**
 * @author NNI
 */
public final class Main {

    public static void main(String[] args) throws IOException {
        final String input = new String( Files.readAllBytes(Paths.get("programm.nil")), "UTF-8");
        final List<Token> tokens = new Lexer(input).tokenize();
        for (Token token : tokens) {
            System.out.println(token);
        }
        
        final Statement program = new Parser(tokens).parse();
        System.out.println(program.toString());
        program.execute();
    }
}
