/*
 * @author [Eray Bozoglu]
 * @netid [ebozoglu]
 * @course CSC172 - Data Structures and Algorithms
 * @project Project 3: Street Mapping
 */

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class StreetMapper {
    private final double KM_TO_MILES = 0.621371;

    private URGraph graph;
    private PathFinder pathFinder;
    private boolean showMap;
    private boolean showDirections;
    private String startIntersection;
    private String endIntersection;

    public StreetMapper() {
        this.graph = new URGraph();
        this.showMap = false;
        this.showDirections = false;
    }

    public void parseInput(String[] input) {
        if (input.length < 2) {
            System.err.println("Correct input format: java StreetMap map. txt [-- show ] [ -- directions startIntersection endIntersection ]");
            System.exit(1);
        }

        String inputFile = input[0];
        loadMapFromFile(inputFile);

        for (int i = 1; i < input.length; i++) {
            if (input[i].equals("--show")) {
                showMap = true;
            } else if (input[i].equals("--directions")) {
                if (i + 2 >= input.length) {
                    System.err.println("Directions require a start and end intersection.");
                    System.exit(1);
                }
                showDirections = true;
                if (!input[i + 1].equals("--show")) {
                    startIntersection = input[++i];
                    endIntersection = input[++i];
                } else {
                    showMap = true;
                    ++i;
                    startIntersection = input[++i];
                    endIntersection = input[++i];
                }

            } else {
                System.err.println(" ");
            }
        }
    }

    private void loadMapFromFile(String inputFile) {
        GraphIn reader = new GraphIn();
        try {
            graph = reader.readGraphFromData(inputFile);
            pathFinder = new PathFinder(graph);
        } catch (IOException e) {
            System.err.println("Error reading map file.");
            System.exit(1);
        }
    }

    private void displayMap() {
        List<URGraphNode> pathToShow = null;
        if (showDirections) {
            pathToShow = pathFinder.findShortestPath(startIntersection, endIntersection);
        }

        JFrame frame = new JFrame("UR Street Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MapDisplay mapPanel = new MapDisplay(graph, pathToShow, pathToShow != null ? pathFinder.getPathDistance() * KM_TO_MILES : 0.0);
        frame.add(mapPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void processMap() {
        if (showMap) {
            displayMap();
        }

        if (showDirections) {
            displayDirections();
        }
    }

    private void displayDirections() {
        List<URGraphNode> path = pathFinder.findShortestPath(startIntersection, endIntersection);
        if (path == null) {
            System.out.println("No path found between " + startIntersection + " and " + endIntersection);
            return;
        }

        double distance = pathFinder.getPathDistance() * KM_TO_MILES;
        System.out.println("Path from " + startIntersection + " to " + endIntersection + ":");
        for (URGraphNode node : path) {
            System.out.println(" -> " + node.getIntersectionID());
        }
        System.out.printf("Total distance: %.2f miles%n", distance);
    }

    public static void main(String[] args) {
        StreetMapper mapper = new StreetMapper();
        mapper.parseInput(args);
        mapper.processMap();
    }
}
