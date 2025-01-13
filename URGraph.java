/*
 * @author [Eray Bozoglu]
 * @netid [ebozoglu]
 * @course CSC172 - Data Structures and Algorithms
 * @project Project 3: Street Mapping
 */

import java.util.ArrayList;
import java.util.List;

// Main Graph class representing the street network
public class URGraph {
    private ArrayList<URGraphNode> nodes;

    public URGraph() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(URGraphNode node) {
        nodes.add(node);
    }

    public void addEdge(URGraphNode source, URGraphNode destination,
                        double weight, String roadID) {
        URGraphEdge edge = new URGraphEdge(source, destination, weight, roadID);
        source.addEdge(edge);
    }

    public List<URGraphNode> getNodes() {
        return nodes;
    }

    // Method to find a node by its intersection ID
    public URGraphNode findNode(String intersectionID) {
        for (URGraphNode node : nodes) {
            if (node.getIntersectionID().equals(intersectionID)) {
                return node;
            }
        }
        return null;
    }
}