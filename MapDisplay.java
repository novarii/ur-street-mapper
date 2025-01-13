/*
 * @author [Eray Bozoglu]
 * @netid [ebozoglu]
 * @course CSC172 - Data Structures and Algorithms
 * @project Project 3: Street Mapping
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MapDisplay extends JPanel {
    private URGraph graph;
    private List<URGraphNode> pathToHighlight;
    private double minLat, maxLat, minLong, maxLong;
    private static final int MARGIN = 50;  // Space around the edges
    private double pathDistance;

    public MapDisplay(URGraph graph, List<URGraphNode> path, double distance) {
        this.graph = graph;
        this.pathToHighlight = path;
        this.pathDistance = distance;
        this.setPreferredSize(new Dimension(1600, 1200));
        findMapBoundaries();
    }

    // Find the map boundaries to scale everything correctly
    private void findMapBoundaries() {
        // Start with the first node's values
        URGraphNode firstNode = graph.getNodes().get(0);
        minLat = maxLat = firstNode.getLatitude();
        minLong = maxLong = firstNode.getLongitude();

        // Find min and max values
        for (URGraphNode node : graph.getNodes()) {
            if (node.getLatitude() < minLat) minLat = node.getLatitude();
            if (node.getLatitude() > maxLat) maxLat = node.getLatitude();
            if (node.getLongitude() < minLong) minLong = node.getLongitude();
            if (node.getLongitude() > maxLong) maxLong = node.getLongitude();
        }
    }

    // Convert geographic coordinates to screen coordinates
    private int getScreenX(double longitude) {
        double screenWidth = getWidth() - 2 * MARGIN;
        return MARGIN + (int)(((longitude - minLong) / (maxLong - minLong)) * screenWidth);
    }

    private int getScreenY(double latitude) {
        double screenHeight = getHeight() - 2 * MARGIN;
        // Flip Y coordinates since latitude increases northward but Y coordinates increase southward
        return getHeight() - (MARGIN + (int)(((latitude - minLat) / (maxLat - minLat)) * screenHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw all roads in black
        g.setColor(Color.BLACK);
        for (URGraphNode node : graph.getNodes()) {
            int x1 = getScreenX(node.getLongitude());
            int y1 = getScreenY(node.getLatitude());

            // Draw each road segment
            for (URGraphEdge edge : node.getEdges()) {
                URGraphNode dest = edge.getDestination();
                int x2 = getScreenX(dest.getLongitude());
                int y2 = getScreenY(dest.getLatitude());
                g.drawLine(x1, y1, x2, y2);
            }
        }

        // Draw all intersections as small circles
        for (URGraphNode node : graph.getNodes()) {
            int x = getScreenX(node.getLongitude());
            int y = getScreenY(node.getLatitude());
            g.fillOval(x - 1, y - 1, 3, 3);  // 3x3 circle centered on the point
        }

        // If there's a path to highlight, draw it in red
        if (pathToHighlight != null && !pathToHighlight.isEmpty()) {
            // Draw the distance in the top right corner
            g.setColor(Color.BLACK);
            String distanceText = String.format("Distance: %.2f miles", pathDistance);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(distanceText);
            g.drawString(distanceText, getWidth() - textWidth - 20, 30);

            // Draw the path
            g.setColor(Color.RED);
            for (int i = 0; i < pathToHighlight.size() - 1; i++) {
                URGraphNode current = pathToHighlight.get(i);
                URGraphNode next = pathToHighlight.get(i + 1);

                int x1 = getScreenX(current.getLongitude());
                int y1 = getScreenY(current.getLatitude());
                int x2 = getScreenX(next.getLongitude());
                int y2 = getScreenY(next.getLatitude());

                g.drawLine(x1, y1, x2, y2);
            }
        }
    }
}