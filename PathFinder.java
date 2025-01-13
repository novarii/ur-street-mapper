/*
 * @author [Eray Bozoglu]
 * @netid [ebozoglu]
 * @course CSC172 - Data Structures and Algorithms
 * @project Project 3: Street Mapping
 */

import java.util.*;

/**
 * Class to handle path finding operations in a street network using Dijkstra's algorithm.
 * This implementation is specific to street networks where nodes represent intersections
 * and edges represent road segments.
 */

public class PathFinder {
    private URGraph streetGraph;
    private URPriorityQueue<URGraphNode> pq;
    private Set<URGraphNode> modifiedNodes;  // Track modified nodes
    private double totalDistance;
    private URGraphNode prevNode;

    // Constructor
    public PathFinder(URGraph graph) {
        streetGraph = graph;
        pq = new URPriorityQueue<>();
        modifiedNodes = new HashSet<>();
        totalDistance = 0;
        prevNode = null;
    }

    // Main path finding method
    public List<URGraphNode> findShortestPath(String startID, String endID) {
        URGraphNode startNode = streetGraph.findNode(startID);
        URGraphNode endNode = streetGraph.findNode(endID);

        initializePathFinding(startNode);
        runDijkstra();
        return reconstructPath(startNode, endNode);
    }

    // Helper method to initialize data structures before running algorithm
    private void initializePathFinding(URGraphNode startNode) {
        reset();
        startNode.setDistance(0);
        modifiedNodes.add(startNode);
        pq.insert(startNode);
    }

    // Method to run Dijkstra's algorithm
    private void runDijkstra() {
        while (!pq.isEmpty()) {
            URGraphNode curr = pq.deleteMin();

            for (URGraphEdge edge : curr.getEdges()) {
                URGraphNode neighbor = edge.getDestination();
                double newDistance = curr.getDistance() + edge.getWeight();

                if (newDistance < neighbor.getDistance()) {
                    neighbor.setDistance(newDistance);
                    neighbor.setPredecessor(curr);
                    modifiedNodes.add(neighbor);

                    // Only add to queue if we found a better path
                    if (!pq.contains(neighbor)) {
                        pq.insert(neighbor);
                    } else {
                        pq.delete(neighbor);
                        pq.insert(neighbor);
                    }
                }
            }
        }
    }

    // Helper method to reconstruct the path after running Dijkstra's
    private List<URGraphNode> reconstructPath(URGraphNode start, URGraphNode end) {
        // Check if path exists
        if (end.getDistance() == Double.MAX_VALUE) {
            return null;
        }

        URGraphNode curr = end;
        List<URGraphNode> path = new ArrayList<>();

        while (curr != start) {
            if (curr == null) {
                return null; // No path exists
            }
            path.add(curr);
            curr = curr.getPredecessor();
        }
        path.add(start);
        totalDistance = path.getFirst().getDistance();
        return path.reversed();
    }

    public double getPathDistance() {
        return totalDistance;
    }

//    public List<String> getPathStreetNames(List<URGraphNode> path)

    private void reset() {
        for (URGraphNode node : modifiedNodes) {
            node.reset();
        }
        modifiedNodes.clear();
        pq.clear();
        totalDistance = 0;
        prevNode = null;
    }
}