import java.util.HashMap;
import java.util.Map;

public class GrammarElementsCounter {
    private final Map<String, Long> grammarElementsCounter = new HashMap<>(Map.of(
            "S", 0L,
            "A", 0L,
            "E", 0L,
            "type", 0L,
            "variable_name", 0L,
            "Array", 0L,
            "<", 0L,
            ">", 0L,
            ":", 0L
    ));

    {
        grammarElementsCounter.put(";", 0L);
        grammarElementsCounter.put("$", 0L);
        grammarElementsCounter.put("var", 0L);
        grammarElementsCounter.put("Map", 0L);
        grammarElementsCounter.put(",", 0L);
    }

    public Long get(String key) {
        return grammarElementsCounter.get(key);
    }

    public void put(String key, Long value) {
        grammarElementsCounter.put(key, value);
    }
}

