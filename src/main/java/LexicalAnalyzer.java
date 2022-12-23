import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import static java.lang.Character.isJavaIdentifierPart;
import static java.lang.Character.isWhitespace;

public class LexicalAnalyzer {
    private final InputStream is;
    private int curChar;
    private int curPos;
    private Token curToken = Token.END;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    public void expect(Token expected, String message, Integer position) throws ParseException {
        if (curToken != expected) {
            throw error(message + " expected at position: ", position);
        }
    }

    public static ParseException error(String message, Integer curPos) {
        return new ParseException(message, curPos);
    }
    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        switch (curChar) {
            case ',' -> {
                nextChar();
                curToken = Token.COMMA;
            }
            case ':' -> {
                nextChar();
                curToken = Token.COLON;
            }
            case ';' -> {
                nextChar();
                curToken = Token.SEMICOLON;
            }
            case '>' -> {
                nextChar();
                curToken = Token.RIGHT_ANGLE_BRACKET;
            }
            case '<' -> {
                nextChar();
                curToken = Token.LEFT_ANGLE_BRACKET;
            }
            case -1 -> curToken = Token.END;
            default -> {
                StringBuilder str = new StringBuilder();
                while (isJavaIdentifierPart((char) curChar)) {
                    str.append((char) curChar);
                    nextChar();
                }
                switch (str.toString()) {
                    case "var" -> curToken = Token.VAR;

                    case "Array" -> curToken = Token.ARRAY;

                    case "Map" -> curToken = Token.MAP;

                    default -> curToken = Token.IDENTIFIER;
                }
            }
        }

    }

    public int curPos() {
        return curPos;
    }

    public Token curToken() {
        return curToken;
    }

    private boolean isBlank(int c) {
        return isWhitespace((char) c);
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

}
