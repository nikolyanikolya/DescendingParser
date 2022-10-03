import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TreeTest {
    @Test
    void tree_to_String() {
        Tree tree = new Tree("S",
                new Tree("var"),
                new Tree("variable_name"),
                new Tree(":"),
                new Tree("Array"),
                new Tree("<"),
                new Tree("type"),
                new Tree(">"),
                new Tree("E", new Tree(";"), new Tree("$")));

        String expected = """

                S -> [var, variable_name, :, Array, <, type, >,\s
                E -> [;, $]]""";
        assertThat(tree.toString()).isEqualTo(expected);
    }
}
