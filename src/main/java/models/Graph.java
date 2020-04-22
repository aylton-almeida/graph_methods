package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Graph {
    int verticesNumber;
    final List<Vertice> vertices = new ArrayList<>();
    final List<Edge> edges = new ArrayList<>();
    MinimumSpanningTree minimumSpanningTree;
    int[][] adjMatrix;

    public void setVerticesNumber(int verticesNumber) {
        this.verticesNumber = verticesNumber;
    }

    public Graph(int verticesNumber) {
        this.setVerticesNumber(verticesNumber);
        adjMatrix = new int[verticesNumber][verticesNumber];
    }

    // print minimum spanning tree - kruskal's algorithm
    public void printMinimumSpanningTree() {
        if (this.isConnected())
            this.minimumSpanningTree = new MinimumSpanningTree(this);
        else
            System.out.println("Cannot print MST - Kruskal because the graph is not connected");
    }

    // print minimum spanning tree - prim's algorithm
    public void printMinimumSpanningTree(int v1) {
        if (this.isConnected())
            this.minimumSpanningTree = new MinimumSpanningTree(this.adjMatrix, this.vertices.get(v1).getValue());
        else
            System.out.println("Cannot print MST - Prim because the graph is not connected");
    }

    void addEdgeToAdjMatrix(Edge e) {
        adjMatrix[e.vertices.get(0).getValue() - 1][e.vertices.get(1).getValue() - 1] = e.weight;
        adjMatrix[e.vertices.get(1).getValue() - 1][e.vertices.get(0).getValue() - 1] = e.weight;
    }

    // utility function to print adjacency matrix
    public void printAdjMatrix() {
        StringBuilder s = new StringBuilder();

        System.out.println("--- adjacency matrix ---");
        System.out.print("   ");
        for (int i = 0; i < verticesNumber; i++)
            System.out.print(i + " ");
        System.out.println("");

        for (int i = 0; i < verticesNumber; i++) {
            s.append(i + ": ");
            for (int j : adjMatrix[i]) {
                s.append((j == 1?1:0) + " ");
            }
            s.append("\n");
        }
        System.out.println(s.toString());
        System.out.println("--------------");
    }

    /**
     * Add edge to graph from string and update vertices
     *
     * @param edgeString edge string
     */
    public void addEdgeFromString(String edgeString) {
        Edge e = new Edge(edgeString);
        this.edges.add(e);
        this.addEdgeToAdjMatrix(e);
        this.updateVertices();
    }

    /**
     * Add isolated vertice
     *
     * @param v1 vertice to be added
     */
    public void addIsolatedVertice(Vertice v1) {
        if (!this.vertices.contains(v1)) this.vertices.add(v1);
    }

    /**
     * Update vertices list
     */
    public void updateVertices() {
        this.edges.forEach(e -> e.vertices.forEach(v -> {
            if (!this.vertices.contains(v)) this.vertices.add(v);
        }));
    }

    /**
     * Tests if two vertices are adjacent or not
     *
     * @param v1 first vertice
     * @param v2 second vertice
     * @return if they are adjacent or not
     */
    public boolean isAdjacent(Vertice v1, Vertice v2) {
        Edge testEdge = new Edge(v1, v2);
        boolean isAdjacent = false;
        for (Edge edge : edges)
            if (testEdge.equalsIgnoreWeight(edge)) {
                isAdjacent = true;
                break;
            }
        return isAdjacent;
    }

    /**
     * Returns the degree for the given vertice
     *
     * @param v1 vertice to be analyzed
     * @return vertice degree or -1 if vertice is not part of the graph
     */
    int getDegree(Vertice v1) {
        return this.vertices.contains(v1)
                ? (int) this.edges.stream().filter(edge -> edge.vertices.contains(v1)).count()
                : -1;
    }

    boolean isIsolated(Vertice v1) {
        return this.getDegree(v1) == 0;
    }

    /**
     * Returns if the vertice is pending or not
     *
     * @param v1 vertice to be analyzed
     * @return if vertice is pending or not
     */
    public boolean isPending(Vertice v1) {
        return this.getDegree(v1) == 1;
    }

    /**
     * Returns if the graph is regular or not
     *
     * @return if the graph is regular or not
     */
    public boolean isRegular() {
        List<Integer> degrees = new ArrayList<>();
        this.vertices.forEach(vertice -> degrees.add(this.getDegree(vertice)));
        boolean isRegular = true;
        int firstDegree = degrees.get(0);
        for (int degree : degrees) {
            if (degree != firstDegree) {
                isRegular = false;
                break;
            }
        }
        return isRegular;
    }

    /**
     * Returns if the graph is null or not
     *
     * @return if the graph is null or not
     */
    public boolean isNull() {
        return this.edges.isEmpty();
    }

    /**
     * Returns if the graph has loops or not
     *
     * @return if the graph has loops or not
     */
    boolean hasLoops() {
        boolean hasLoops = false;
        for (Edge edge : this.edges) {
            if (edge.vertices.get(0).equals(edge.vertices.get(1))) {
                hasLoops = true;
                break;
            }
        }
        return hasLoops;
    }

    /**
     * Returns if the graph has parallel edges or not
     *
     * @return if the graph has parallel edges or not
     */
    boolean hasParallelEdges() {
        boolean hasParallelEdges = false;
        // for each edge, check if there is another edge linking its vertices
        List<Edge> filteredEdges = new ArrayList<>(this.edges);
        int loopCount = 0;
        for (Edge testEdge : this.edges) {
            // check if current tested edge exists in remaining edges
            if (loopCount != 0) {
                filteredEdges.clear();
                filteredEdges.addAll(this.edges);
            }
            filteredEdges.remove(testEdge);
            for (Edge edge : filteredEdges) {
                if (edge.equalsIgnoreWeight(testEdge)) {
                    hasParallelEdges = true;
                    break;
                }
            }
            loopCount++;
        }
        return hasParallelEdges;
    }

    /**
     * Returns if the graph is simple or not
     *
     * @return if the graph is simple or not
     */
    public boolean isSimple() {
        return !this.hasLoops() && !this.hasParallelEdges();
    }

    /**
     * Returns if the graph is complete or not
     *
     * @return if the graph is complete or not
     */
    public boolean isComplete() {
        // for each vertice, check if it is adjacent to all other vertices
        if (this.isSimple()) {
            List<Vertice> filteredVertices = new ArrayList<>(this.vertices);
            int loopCount = 0;
            for (Vertice testVertice : this.vertices) {
                // check if current tested edge is adjacent in remaining vertices
                if (loopCount != 0) {
                    filteredVertices.clear();
                    filteredVertices.addAll(this.vertices);
                }
                filteredVertices.remove(testVertice);
                for (Vertice vertice : filteredVertices) {
                    if (!this.isAdjacent(testVertice, vertice)) {
                        return false;
                    }
                }
                loopCount++;
            }
            return true;
        }
        return false;
    }

    /**
     * Depth-first search
     */
    void DepthFirstSearch(int source, boolean[] visited){

        //mark the vertice as visited
        visited[source] = true;

        List<Vertice> adjList = this.edges
                .stream()
                .filter(e -> e.vertices.get(0)
                        .equals(this.vertices.get(source))
                )
                .map(e -> e.vertices.get(1))
                .collect(Collectors.toList());

        for (Vertice v : adjList) {
            int index = v.getValue() - 1;
            if(!visited[index]){
                //make recursive call from neighbor
                DepthFirstSearch(index, visited);
            }
        }
    }

    /**
     * Returns if the graph is connected or not
     *
     * @return if the graph is connected or not
     */
    public boolean isConnected() {
        int vertices = this.vertices.size();

        //created visited array
        boolean[] visited = new boolean[vertices];

        DepthFirstSearch(0, visited);

        //check if all the vertices are visited, if yes then graph is connected
        int count = 0;
        for (boolean b : visited) {
            if (b)
                count++;
        }
        return vertices == count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        return verticesNumber == graph.verticesNumber &&
                vertices.equals(graph.vertices) &&
                edges.equals(graph.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(verticesNumber, vertices, edges);
    }
}
