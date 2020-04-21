package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DirectedGraph {
    final int verticesNumber;
    final List<Vertice> vertices = new ArrayList<>();
    final List<DirectedEdge> edges = new ArrayList<>();

    public DirectedGraph(int verticesNumber) {
        this.verticesNumber = verticesNumber;
    }

    /**
     * Add edge to graph from string and update vertices
     *
     * @param edgeString edge string
     */
    public void addEdgeFromString(String edgeString) {
        this.edges.add(new DirectedEdge(edgeString));
        this.updateVertices();
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
     * Get vertice's entry degree
     *
     * @param v1 vertice to be analyzed
     * @return the entry degree -1 if vertice is not part of the graph
     */
    int getEntryDegree(Vertice v1) {
        return this.vertices.contains(v1)
                ? (int) this.edges.stream().filter(e -> e.vertices.get(1).equals(v1)).count()
                : -1;
    }

    /**
     * Get vertice's exit degree
     *
     * @param v1 vertice to be analyzed
     * @return the exit degree -1 if vertice is not part of the graph
     */
    int getExitDegree(Vertice v1) {
        return this.vertices.contains(v1)
                ? (int) this.edges.stream().filter(e -> e.vertices.get(0).equals(v1)).count()
                : -1;
    }

    /**
     * If the graph is cyclic or not using depth traversal algorithm
     *
     * @return if it has a cycle
     */
    boolean hasCycle() {
        //Visited and Stack arrays
        boolean[] visited = new boolean[verticesNumber];
        boolean[] recStack = new boolean[verticesNumber];

        //Call Recursive helper function
        for (int i = 0; i < this.vertices.size(); i++)
            if (hasCycleUtil(i, visited, recStack))
                return true;
        return false;
    }

    /**
     * Recursive helper function for hasCycle method
     *
     * @param v       vertice being analyzed
     * @param visited if it was visited or not
     * @return if the vertice has a cycle or not
     */
    private boolean hasCycleUtil(int v, boolean[] visited, boolean[] recStack) {

        if (recStack[v])
            return true;

        if (visited[v])
            return false;

        //Add current node to stack and set it as visited
        visited[v] = true;
        recStack[v] = true;

        List<Vertice> adjList = this.edges
                .stream()
                .filter(e -> e.vertices.get(0)
                        .equals(this.vertices.get(v))
                )
                .map(e -> e.vertices.get(1))
                .collect(Collectors.toList());

        for (Vertice vertice : adjList)
            if (hasCycleUtil(this.vertices.indexOf(vertice), visited, recStack))
                return true;

        recStack[v] = false;

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectedGraph that = (DirectedGraph) o;
        return verticesNumber == that.verticesNumber &&
                vertices.equals(that.vertices) &&
                edges.equals(that.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(verticesNumber, vertices, edges);
    }
}
