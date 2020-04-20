package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Graph {
    int verticesNumber;
    final List<Vertice> vertices = new ArrayList<>();
    final List<Edge> edges = new ArrayList<>();
    LinkedList<Integer>[] adjList;

    public void setVerticesNumber(int verticesNumber) {
        this.verticesNumber = verticesNumber;
        this.adjList = new LinkedList[verticesNumber];
        for (int i = 0; i < verticesNumber; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    public Graph(int verticesNumber) {
        this.verticesNumber = verticesNumber;
        adjList = new LinkedList[verticesNumber];
        for (int i = 0; i < verticesNumber; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    Edge formatEdgeString(String edgeString) {
        String[] strings = edgeString.split(";");
        int v1 = Integer.parseInt(strings[0].strip());
        int v2 = Integer.parseInt(strings[1].strip());
        double weight = Double.parseDouble(strings[2]);
        this.updateAdjList(v1, v2);
        return new Edge(weight, v1, v2);
    }

    /**
     * Add edge to graph from string and update vertices
     *
     * @param edgeString edge string
     */
    public void addEdgeFromString(String edgeString) {
        this.edges.add(formatEdgeString(edgeString));
        this.updateVertices();
    }

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
     * Update adjacency list
     */
    public void updateAdjList(int source, int destination) {
        this.adjList[source - 1].addFirst(destination - 1);
        this.adjList[destination - 1].addFirst(source - 1);
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
    void DFS(int source, LinkedList<Integer> adjList [], boolean[] visited){

        //mark the vertice as visited
        visited[source] = true;

        //travel the neighbors
        for (int i = 0; i <adjList[source].size() ; i++) {
            int neighbor = adjList[source].get(i);
            if(!visited[neighbor]){
                //make recursive call from neighbor
                DFS(neighbor, adjList, visited);
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
        LinkedList<Integer> adjList [] = this.adjList;

        //created visited array
        boolean[] visited = new boolean[vertices];

        //start the DFS from vertice 0
        DFS(0, adjList, visited);

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
