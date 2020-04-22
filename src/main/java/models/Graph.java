package models;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Graph {
    int verticesNumber;
    final List<Vertice> vertices = new ArrayList<>();
    final List<Edge> edges = new ArrayList<>();

    void setVerticesNumber(int verticesNumber) {
        this.verticesNumber = verticesNumber;
    }

    public Graph(int verticesNumber) {
        this.verticesNumber = verticesNumber;
    }

    Graph(int verticesNumber, List<Vertice> vertices) {
        this.verticesNumber = verticesNumber;
        this.vertices.addAll(vertices);
    }

    /**
     * Add edge to graph from string and update vertices
     *
     * @param edgeString edge string
     */
    public void addEdgeFromString(String edgeString) {
        this.edges.add(new Edge(edgeString));
        this.updateVertices();
    }

    /**
     * Add edge to graph from 2 edges
     *
     * @param v1 first edge
     * @param v2 second edge
     */
    void addEdge(Vertice v1, Vertice v2) {
        this.edges.add(new Edge(v1, v2));
        this.updateVertices();
    }

    /**
     * Checks if edge is inside edges list ignoring its weight
     *
     * @param e edge to be checked
     * @return if the edge is inside the edges list ignoring it's weight
     */
    boolean edgesContainsIgnoringWeight(Edge e) {
        boolean contains = false;
        for (Edge edge : this.edges)
            if (edge.equalsIgnoreWeight(e))
                contains = true;
        return contains;
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
        this.vertices.sort(Vertice::compareTo);
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

    /**
     * if the graph is eulerian or not, that is, if all it's vertices has degree 2
     *
     * @return returns true or false
     */
    public boolean isEulerian() {
        for (Vertice vertice : vertices) {
            if (getDegree(vertice) % 2 != 0) {
                return false;
            }
        }
        return true;
    }


    boolean isIsolated(Vertice v1) {
        return this.getDegree(v1) == 0;
    }

    /**
     * Returns if the graph has a eulerian path, that is, only two vertices with odd degree
     *
     * @return if the graph is unicursal
     */
    public boolean isUnicursal() {
        int cont = 0;
        for (Vertice vertice : vertices) {
            if (getDegree(vertice) % 2 != 0) {
                cont++;
            }
        }
        return cont == 2;
    }

    /**
     * Given two graphs with the same vertices, the complementary of the first graph will have all
     * edges it didn't have
     *
     * @return the complementary Graph from this
     */
    public Graph getComplementary() {
        Graph newGraph = new Graph(verticesNumber, this.vertices);

        for (Vertice v1 : this.vertices)
            for (Vertice v2 : this.vertices)
                if (!v1.equals(v2)
                        && !this.edgesContainsIgnoringWeight(new Edge(v1, v2))
                        && !newGraph.edges.contains(new Edge(v1, v2)))
                    newGraph.addEdge(v1, v2);

        return newGraph;
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

    @Override
    public String toString() {
        return "Graph{" +
                "verticesNumber=" + verticesNumber +
                ", vertices=" + vertices +
                ", edges=" + edges +
                '}';
    }
}
