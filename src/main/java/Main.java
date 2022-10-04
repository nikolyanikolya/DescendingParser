import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
    public static void main(String[] args) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/main/java/tree.txt"), UTF_8)) {
            Parser parser = new Parser("var x: Array<Array<Integer>>;");
            var s = parser.parse().toString().trim();
            writer.write(s, 0, s.length());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ParseException e) {
            System.err.println("Parse exception: " + e.getLocalizedMessage() + " " + e.getErrorOffset());
        }


    }

}