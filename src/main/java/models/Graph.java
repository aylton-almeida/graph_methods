package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Graph {
    final int verticesNumber;
    final List<Vertice> vertices = new ArrayList<>();
    final List<Edge> edges = new ArrayList<>();

    public Graph(int verticesNumber) {
        this.verticesNumber = verticesNumber;
    }

    /**
     * Add edge to graph from string and update vertices
     *
     * @param edgeString edge string
     */
    public void addEdgeFromString(String edgeString) {
        Edge newEdge = new Edge(edgeString);
        if (!this.edges.contains(newEdge))
            this.edges.add(new Edge(edgeString));
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
