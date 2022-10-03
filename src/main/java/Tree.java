import java.util.*;
import java.util.stream.Collectors;

public class Tree {
    String node;
    List<Tree> children = new ArrayList<>();

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
    }

    public void addChild(final Tree tree) {
        children.add(tree);
    }

    @Override
    public String toString() {
        if (children.isEmpty()) {
            return node;
        }

        return "\n" + node
                + " -> [" +
                children.stream()
                        .filter(Objects::nonNull)
                        .map(Tree::toString)
                        .collect(Collectors.joining(", "))
                + "]";
    }
}