import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ParserTest {

    private ParserTest() {
    }

    @Test
    void successful_parse() throws ParseException {
        String s = "var x: Array<Integer>;";
        Tree expected = new Tree("S",
                new Tree("var"),
                new Tree("variable_name"),
                new Tree(":"),
                new Tree("A",
                        new Tree("Array"),
                        new Tree("<"),
                        new Tree("A",
                                new Tree("type")),
                        new Tree(">")),
                new Tree("E", new Tree(";"), new Tree("$")));

        assertThat(new Parser(
                s)
                .parse()).usingRecursiveComparison().isEqualTo(expected);
    }


    @Test
    void colon_missed() {
        String s = "var x> Array<Integer>;";

        assertThatThrownBy(() -> new Parser(s)
                .parse())
                .isInstanceOf(ParseException.class)
                .hasMessageStartingWith("colon expected at position");
    }

    @Test
    void right_angle_bracket_missed() {
        String s = "var x: Array<Integer:;";

        assertThatThrownBy(() -> new Parser(s)
                .parse())
                .isInstanceOf(ParseException.class)
                .hasMessageStartingWith("> expected at position");
    }

    @Test
    void illegal_end_of_declaration_statement() {
        String s = "var x: Array<Integer>$";

        assertThatThrownBy(() -> new Parser(s)
                .parse())
                .isInstanceOf(ParseException.class)
                .hasMessageStartingWith("illegal end of statement at position");
    }


    @Test
    void not_an_array() {
        String s = "var x: NotArray<Integer>;";

        assertThatThrownBy(() -> new Parser(s)
                .parse())
                .isInstanceOf(ParseException.class)
                .hasMessageStartingWith("Array expected at position");
    }

    @Test
    void var_keyword_missed() {
        String s = "notVar x: Array<Integer>;";

        assertThatThrownBy(() -> new Parser(s)
                .parse())
                .isInstanceOf(ParseException.class)
                .hasMessageStartingWith("variable keyword expected at position");
    }

    @Test
    void left_angle_bracket_missed() {
        String s = "var x: Array:Integer>;";

        assertThatThrownBy(() -> new Parser(s)
                .parse())
                .isInstanceOf(ParseException.class)
                .hasMessageStartingWith("< expected at position");
    }

    @Test
    void incorrect_name_of_type() {
        String s = "var x: Array<->;";

        assertThatThrownBy(() -> new Parser(s)
                .parse())
                .isInstanceOf(ParseException.class)
                .hasMessageStartingWith("> expected at position");
    }

    @Test
    void incorrect_name_of_variable() {
        String s = "var -: Array<Integer>;";

        assertThatThrownBy(() -> new Parser(s)
                .parse())
                .isInstanceOf(ParseException.class)
                .hasMessageStartingWith("colon expected at position");
    }

    @Test
    void successful_parse_with_a_lot_of_whitespaces() throws ParseException {
        String s = "var    x   :  \r  Array<Integer> \t\n;   ";
        Tree expected = new Tree("S",
                new Tree("var"),
                new Tree("variable_name"),
                new Tree(":"),
                new Tree("A",
                        new Tree("Array"),
                        new Tree("<"),
                        new Tree("A",
                                new Tree("type")),
                        new Tree(">")),
                new Tree("E", new Tree(";"), new Tree("$")));

        assertThat(new Parser(s).parse())
                .usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void successful_parse_when_semicolon_missed() throws ParseException {
        String s = "var x: Array<Integer>";
        Tree expected = new Tree("S",
                new Tree("var"),
                new Tree("variable_name"),
                new Tree(":"),
                new Tree("A",
                        new Tree("Array"),
                        new Tree("<"),
                        new Tree("A",
                                new Tree("type")),
                        new Tree(">")),
                new Tree("E", new Tree("$")));

        assertThat(new Parser(s).parse())
                .usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void successful_when_nested_arrays() throws ParseException {
        String s = "var x: Array<Array<Integer>>";
        Tree expected = new Tree("S",
                new Tree("var"),
                new Tree("variable_name"),
                new Tree(":"),
                new Tree("A", new Tree("Array"),
                        new Tree("<"), new Tree("A",
                        new Tree("A",
                                new Tree("Array"),
                                new Tree("<"),
                                new Tree("A",
                                        new Tree("type")),
                                new Tree(">"))), new Tree(">")),
                new Tree("E", new Tree("$")));

        assertThat(new Parser(s).parse())
                .usingRecursiveComparison().isEqualTo(expected);
    }

}
