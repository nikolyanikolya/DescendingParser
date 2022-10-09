import java.text.ParseException;


public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser("var x: Array<Array<Integer>>;");
            var graph = parser.parse();
            graph.display();
        } catch (ParseException e) {
            System.err.println("Parse exception: " + e.getLocalizedMessage() + " " + e.getErrorOffset());
        }


    }

}