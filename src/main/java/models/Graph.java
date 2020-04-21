package models;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Graph {
    final int verticesNumber;
    final List<Vertice> vertices = new ArrayList<>();
    final List<Edge> edges = new ArrayList<>();
    LinkedList<Integer>[] adjList;

    public Graph(int verticesNumber) {
        this.verticesNumber = verticesNumber;
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





    /**
     * Returns true or false
     *
     * @return true if the graph is eurelian  or false
     * if the graph is not eurelian
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
     * @return
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

    public Graph getComplementar() {
        Graph graph2 = new Graph(verticesNumber);

        for (Vertice vertice : vertices) {
            for (Vertice vertice1 : vertices) {

                if (isAdjacent(vertice, vertice1) != true && vertice != vertice1) {
                    graph2.addEdgeFromString("vertice;vertice1");
                }
            }
        }

        return graph2;
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
