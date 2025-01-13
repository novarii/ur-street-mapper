/*
 * @author [Eray Bozoglu]
 * @netid [ebozoglu]
 * @course CSC172 - Data Structures and Algorithms
 * @project Project 3: Street Mapping
 */

import java.util.ArrayList;
import java.util.List;

// Class to represent a node/vertex in the street map
public class URGraphNode implements Comparable<URGraphNode> {
    private ArrayList<URGraphEdge> edges;
    private double latitude;
    private double longitude;
    private String intersectionID;

    //fields useful for dijkstra's algorithm
    private double distance;
    private URGraphNode predecessor;

    public URGraphNode(double latitude, double longitude, String intersectionID) {
        this.edges = new ArrayList<>();
        this.latitude = latitude;
        this.longitude = longitude;
        this.intersectionID = intersectionID;
        distance = Double.MAX_VALUE;
        predecessor = null;
    }

    public List<URGraphEdge> getEdges() {
        return edges;
    }

    public void addEdge(URGraphEdge edge) {
        edges.add(edge);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getIntersectionID() {
        return intersectionID;
    }

    public void reset() {
        this.distance = Double.MAX_VALUE;
        this.predecessor = null;
    }

    // Getters and setters for distance and predecessor
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setPredecessor(URGraphNode predecessor) {
        this.predecessor = predecessor;
    }

    public URGraphNode getPredecessor() {
        return predecessor;
    }

    @Override
    public int compareTo(URGraphNode o) {
        return Double.compare(this.distance, o.distance);
    }
}