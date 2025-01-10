
/*
Author: Matt Farzaneh
Student number: 251370889

Description:
The maze class makes a maze using the graph data structure previously made
It reads the maze file and solves the path by getting from the entrance to
the exit while not using more coins than the coin requirement.
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Maze {
	
	// Instance variables
    private Graph mazeGraph;
    // ID for entrance, exit, and number of coins needed to travel maze
    private int entrance;
    private int exit;
    private int coinsReq;

    // Constructor
    public Maze(String inputFile) throws MazeException {
        try {
            File file = new File(inputFile);
            if (!file.exists() || !file.isFile()) {
                throw new MazeException("File not found or invalid: " + inputFile);
            }

            // Initialize the graph and variables by reading file given
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                readInput(br);
            }
            finally {
                br.close();
            }
        }
        catch (IOException e) {
            throw new MazeException("Error reading input file: " + e.getMessage());
        }
        catch (GraphException e) {
            throw new MazeException("Error creating the graph: " + e.getMessage());
        }
    }

    // Getter
    public Graph getGraph() {
        return mazeGraph;
    }

    // Uses DFS to find valid path
    public Iterator<GraphNode> solve() {
        // If no entrance or exit found
        if (entrance == -1 || exit == -1) {
            return null;
        }

        try {
            GraphNode startNode = mazeGraph.getNode(entrance);
            List<GraphNode> path = new ArrayList<>();
            boolean pathFound = DFS(coinsReq, startNode, path);

            if (pathFound) {
                return path.iterator();
            }
            else {
            	// No path found
                return null;
            }
        }
        catch (GraphException e) {
            return null;
        }
    }

    // Uses DFS to help solve maze
    private boolean DFS(int remainingCoins, GraphNode currentNode, List<GraphNode> path) throws GraphException {
        path.add(currentNode);
        // Mark current node to show it has been visited
        currentNode.mark(true);
        
        // If we reach exit with enough coins
        if (currentNode.getName() == exit && remainingCoins >= 0) {
            return true;
        }

        // Check all neighbors
        Iterator<GraphEdge> edges = mazeGraph.incidentEdges(currentNode);
        while (edges != null && edges.hasNext()) {
            GraphEdge edge = edges.next();
            GraphNode neighbor;
            if (edge.firstEndpoint().equals(currentNode)) {
                neighbor = edge.secondEndpoint();
            }
            else {
                neighbor = edge.firstEndpoint();
            }

            // If the neighbor is unvisited and we have enough coins to continue
            if (!neighbor.isMarked() && remainingCoins >= edge.getType()) {
                boolean foundPath = DFS(remainingCoins - edge.getType(), neighbor, path);
                if (foundPath) {
                    return true;
                }
            }
        }

        // Backtrack (unmark the node and remove it from path if no valid path is found)
        path.remove(path.size() - 1);
        currentNode.mark(false);
        return false;
    }

    // Read input file
    private void readInput(BufferedReader inputReader) throws IOException, GraphException, MazeException {
        // Read the first four integers
        int S = Integer.parseInt(inputReader.readLine().trim());
        int A = Integer.parseInt(inputReader.readLine().trim());
        int L = Integer.parseInt(inputReader.readLine().trim());
        coinsReq = Integer.parseInt(inputReader.readLine().trim());

        // *** Total nodes = A * L ***
        mazeGraph = new Graph(A * L);
        String[] grid = new String[2 * L - 1];

        // Read grid lines
        for (int i = 0; i < grid.length; i++) {
            String line = inputReader.readLine();
            if (line == null || line.trim().length() != (2 * A - 1)) {
                throw new MazeException("Invalid grid format at line " + (i + 1));
            }
            grid[i] = line.trim();
        }

        entrance = -1;
        exit = -1;

        // Process the grid
        for (int row = 0; row < L; row++) {
            for (int col = 0; col < A; col++) {
                int currentNode = row * A + col;

                // Identify room characters
                char roomChar = grid[row * 2].charAt(col * 2);
                if (roomChar == 's') {
                    if (entrance != -1) throw new MazeException("Multiple entrances found.");
                    entrance = currentNode;
                }
                else if (roomChar == 'x') {
                    if (exit != -1) throw new MazeException("Multiple exits found.");
                    exit = currentNode;
                }

                // Add horizontal edges
                if (col < A - 1) {
                    char hEdge = grid[row * 2].charAt(col * 2 + 1);
                    insertEdge(currentNode, currentNode + 1, hEdge);
                }

                // Add vertical edges
                if (row < L - 1) {
                    char vEdge = grid[row * 2 + 1].charAt(col * 2);
                    insertEdge(currentNode, currentNode + A, vEdge);
                }
            }
        }
        // Make sure both entrance and exit are found
        if (entrance == -1) throw new MazeException("No entrance found in the maze.");
        if (exit == -1) throw new MazeException("No exit found in the maze.");
    }


    // Adds edge between 2 nodes
    private void insertEdge(int node1, int node2, char edgeType) throws GraphException {
        int linkType = 0;
        String label = "corridor";
        
        // Wall can't pass through ** Very high number **
        if (edgeType == 'w') {
            linkType = Integer.MAX_VALUE;
            label = "wall";
        }
        // Door with coins needed to pass
        else if (Character.isDigit(edgeType)) {
            linkType = Character.getNumericValue(edgeType);
            label = "door";
        }

        GraphNode n1 = mazeGraph.getNode(node1);
        GraphNode n2 = mazeGraph.getNode(node2);

        mazeGraph.insertEdge(n1, n2, linkType, label);
    }

}

