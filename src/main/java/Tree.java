import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.*;


public class Tree {

    GrammarElementsCounter grammarElementsCounter = new GrammarElementsCounter();
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

    public void display() {
        System.setProperty("org.graphstream.ui", "javafx");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Graph graph = new MultiGraph("Tree", false, false);
        try {
            graphTraversal(graph, this);
            graph.display(true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private Node graphTraversal(Graph graph, Tree tree) {
        var treeNodeName = tree.node;

        Long valueByKey = grammarElementsCounter.get(tree.node);
        treeNodeName += valueByKey;
        valueByKey += 1;
        grammarElementsCounter.put(tree.node, valueByKey);

        var treeNode = graph.addNode(treeNodeName);

        treeNode.addAttribute("ui.style", "shape:circle;" +
                "fill-color: green;size: 30px; text-alignment: center; text-size: 15px;");

        treeNode.addAttribute("ui.label", tree.node);
        String finalTreeNodeName = treeNodeName;

        tree.children.forEach(
                child -> {
                    var childNode = graphTraversal(graph, child);
                    graph.addEdge(finalTreeNodeName + " -> " + childNode.getId(), treeNode, childNode);
                }
        );

        return treeNode;
    }
}