# ur-street-mapper
## Contact Information
Name: Eray Bozoglu
Email: ebozoglu@u.rochester.edu
NetID: ebozoglu
Course: CSC172 - Data Structures and Algorithms
Project: Project 3 - Street Mapping

## Project Synopsis
This project implements a street mapping system that reads intersection and road data from a file, creates a graph representation, visualizes the map, and finds shortest paths between intersections using Dijkstra's algorithm.

### Design & Implementation Choices
1. Graph Implementation:
   - Used adjacency list representation for memory efficiency
   - Bidirectional edges for roads (can travel both ways)
   - Edge weights calculated using Haversine formula for real-world distances
   - Custom Priority Queue implementation for Dijkstra's algorithm

2. Map Visualization:
   - JFrame and custom JPanel for map display
   - Dynamic scaling to fit all intersections in window
   - Black lines for regular roads
   - Red lines for highlighting shortest path
   - Distance shown in miles (converted from kilometers)

3. Path Finding:
   - Implemented Dijkstra's algorithm with Priority Queue
   - Track modified nodes for efficient reset
   - Distance calculations in kilometers, displayed in miles
   - Returns null if no path exists

### Runtime Analysis
1. Map Plotting:
   - Reading map data: O(E + V) where E = number of roads, V = number of intersections
   - Drawing the map: O(E) for drawing all roads
   - Overall complexity: O(E + V)

2. Shortest Path (Dijkstra's Algorithm):
   - Time Complexity: O((E + V) log V) with Priority Queue
   - Space Complexity: O(V) for storing distances and predecessors

### Notable Obstacles I Overcame
1. GUI Scaling Challenges:
   - Initially struggled with properly scaling coordinates to fit all roads and intersections in the window
   - Implemented dynamic min/max coordinate tracking for proper scaling
   - Added padding to ensure intersections weren't cut off at edges

2. Dijkstra's Algorithm Implementation:
   - Had to carefully track visited nodes and maintain shortest paths
   - Managing the priority queue updates for shorter paths found
   - Ensuring proper path reconstruction from predecessor nodes
   - Handling edge cases where no path exists between intersections

3. Hash Table Performance:
   - Current linear probing implementation slows down with large datasets like nys.txt
   - Required careful handling of collision resolution
   - Memory usage becomes significant with large maps

### Areas for Improvement
In a future version, I would:

1. Optimize the Hash Table:
   - Switch to quadratic probing from linear probing
   - Add dynamic resizing based on load
   - Implement better collision handling

2. Enhance the GUI:
   - Add mouse-click intersection selection
   - Include zoom and pan features
   - Show street names on hover
   - Build a better UI for choosing start/end points

### Run Instructions
1. Compile StreetMapper.java: javac StreetMapper.java
2. Run the program: java StreetMapper <map_file> [--show] [--directions <start_intersection> <end_intersection>]
Example: java StreetMapper monroe.txt --show --directions i50 i500

### Example Output
Path from i50 to i500:
-> i50
-> i49
-> i74426
-> i74427
-> i74428
-> i38033
-> i38055
-> i38054
-> i38053
-> i38052
-> i38051
-> i38050
-> i38049
-> i38048
-> i38047
-> i38046
-> i38045
-> i38044
-> i38043
-> i38042
-> i38041
-> i38040
-> i27373
-> i27372
-> i27371
-> i27370
-> i27369
-> i504
-> i503
-> i502
-> i501
-> i500
Total distance: 1.85 miles

### Files Included
1. StreetMapper.java - Main program class
2. URGraph.java - Graph implementation
3. URGraphNode.java - Node/Intersection representation
4. URGraphEdge.java - Edge/Road representation
5. PathFinder.java - Dijkstra's algorithm implementation
6. GraphIn.java - File parsing and graph construction
7. MapDisplay.java - GUI visualization
8. README.txt - This file

### Notes
- Distances are calculated using the Haversine formula for accuracy
- Map visualization automatically scales to show all intersections
- Coordinates are expected in decimal format
