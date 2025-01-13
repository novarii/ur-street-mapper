/*
 * @author [Eray Bozoglu]
 * @netid [ebozoglu]
 * @course CSC172 - Data Structures and Algorithms
 * @project Project 3: Street Mapping
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphIn {
    private URGraph graph;
    private UR_HashTable<String, URGraphNode> intersectionMap;

    public GraphIn() {
        this.graph = new URGraph();
        this.intersectionMap = new URLinearHashTable<>();
    }

    public URGraph readGraphFromData(String file) throws IOException {
        try (BufferedReader bd = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bd.readLine()) != null) {
                processLine(line);
            }
        }
        return graph;
    }

    public void processLine(String line) {
        if (line.isEmpty()) return;

        String[] lineParts = line.split("\\s+");

        if (lineParts[0].equals("i")) {
            processIntersection(lineParts);
        } else if(lineParts[0].equals("r")) {
            processRoad(lineParts);
        } else {
            System.err.println("Invalid line format: " + line);
        }
    }
    
    public void processIntersection(String[] lineParts) {
        if (lineParts.length != 4) {
            System.err.println("Invalid intersection format");
        }

        try {
            String id = lineParts[1];
            double latitude = Double.parseDouble(lineParts[2]);
            double longitude = Double.parseDouble(lineParts[3]);

            URGraphNode newNode = new URGraphNode(latitude, longitude, id);
            graph.addNode(newNode);
            intersectionMap.put(id, newNode);

        } catch (NumberFormatException e) {
            System.err.println("Invalid intersection numer format");
        }
    }

    public void processRoad(String[] lineParts) {
        if (lineParts.length != 4) {
            System.err.println("Invalid road format");
        }

        String roadID = lineParts[1];
        String intersectionOneID = lineParts[2];
        String intersectionTwoID = lineParts[3];

        URGraphNode source = intersectionMap.get(intersectionOneID);
        URGraphNode destination = intersectionMap.get(intersectionTwoID);

        if (source == null || destination == null) {
            System.err.println("Invalid intersection reference in road: " + roadID);
            return;
        }

        double weight = calculateRoadWeight(source, destination);

        graph.addEdge(source, destination, weight, roadID);
        graph.addEdge(destination, source, weight, roadID);
    }

    // Haversine implementation inspired from https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
    public double calculateRoadWeight(URGraphNode source, URGraphNode destination) {
        double sourceLat = source.getLatitude();
        double sourceLong = source.getLongitude();

        double destinationLat = destination.getLatitude();
        double destinationLong = destination.getLongitude();

        // distance between latitudes and longitudes
        double dLat = Math.toRadians(destinationLat - sourceLat);
        double dLon = Math.toRadians(destinationLong - sourceLong);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                Math.cos(sourceLat) *
                Math.cos(destinationLat);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }
}
