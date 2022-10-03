import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public class Parser {
    private final LexicalAnalyzer lex;
    private boolean isVariable = true;

    public Parser(InputStream input) throws ParseException {
        lex = new LexicalAnalyzer(input);
    }

    public Parser(String s) throws ParseException {
        lex = new LexicalAnalyzer(
                new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));

    }

    Tree S() throws ParseException {
        var tree = new Tree("S");
        while (true) {
            var curPos = lex.curPos();
            var curToken = lex.curToken();
            lex.nextToken();
            switch (curToken) {
                case VAR -> {
                    lex.expect(Token.IDENTIFIER, "variable name", curPos);
                    tree.addChild(new Tree("var"));
                }
                case IDENTIFIER -> {
                    if (isVariable && lex.curToken() != Token.COLON) {
                        throw LexicalAnalyzer.error("colon expected at position", curPos);
                    } else if (!isVariable && lex.curToken() != Token.RIGHT_ANGLE_BRACKET) {
                        throw LexicalAnalyzer.error("> expected at position", curPos);
                    }
                    String node = isVariable ? "variable_name" : "type";
                    isVariable = false;
                    tree.addChild(new Tree(node));
                }
                case COLON -> {
                    lex.expect(Token.ARRAY, "Array", curPos);
                    tree.addChild(new Tree(":"));
                }
                case ARRAY -> {
                    lex.expect(Token.LEFT_ANGLE_BRACKET, "<", curPos);
                    tree.addChild(new Tree("Array"));
                }
                case LEFT_ANGLE_BRACKET -> {
                    lex.expect(Token.IDENTIFIER, "array type", curPos);
                    tree.addChild(new Tree("<"));
                }
                case RIGHT_ANGLE_BRACKET -> {
                    if (lex.curToken() != Token.SEMICOLON && lex.curToken() != Token.END) {
                        throw LexicalAnalyzer.error("illegal end of statement at position", curPos);
                    }
                    tree.addChild(new Tree(">"));
                    tree.addChild(E());
                    return tree;
                }
                default -> throw new AssertionError();
            }
        }
    }

    Tree E() throws ParseException {
        Tree tree = new Tree("E");
        while (true) {
            switch (lex.curToken()) {
                case SEMICOLON -> {
                    var curPos = lex.curPos();
                    lex.nextToken();
                    lex.expect(Token.END, "end of the statement", curPos);
                    tree.addChild(new Tree(";"));
                }
                case END -> {
                    tree.addChild(new Tree("$"));
                    return tree;
                }

                default -> throw new AssertionError();
            }
        }
    }

    Tree parse() throws ParseException {
        var curPos = lex.curPos();
        lex.nextToken();
        lex.expect(Token.VAR, "variable keyword", curPos);
        return S();
    }
}
