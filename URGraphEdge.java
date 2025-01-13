/*
 * @author [Eray Bozoglu]
 * @netid [ebozoglu]
 * @course CSC172 - Data Structures and Algorithms
 * @project Project 3: Street Mapping
 */

// Class to represent a street segment between intersections
public class URGraphEdge {
    private URGraphNode source;
    private URGraphNode destination;
    private double weight;
    private String roadID;

    public URGraphEdge(URGraphNode source, URGraphNode destination,
                       double weight, String roadID) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.roadID = roadID;
    }

    public URGraphNode getSource() {
        return source;
    }

    public URGraphNode getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }

    public String getRoadID() {
        return roadID;
    }
}
