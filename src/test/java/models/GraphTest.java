package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    Graph graph;

    @BeforeEach
    void setUp() {
        this.graph = new Graph(3);
    }

    @Test
    void updateVertices() {
        assertEquals(this.graph.vertices.size(), 0, "should't have any vertices at the start");

        this.graph.addEdgeFromString("1;2;4");
        assertEquals(new Vertice(1), this.graph.vertices.get(0), "should contain a vertice v1");
        assertEquals(new Vertice(2), this.graph.vertices.get(1), "should contain a vertice v2");
    }

    @Test
    void addEdgeFromString() {
        assertEquals(0, this.graph.edges.size(), "Should have no edge");

        this.graph.addEdgeFromString("1;2;4");
        assertEquals(new Edge(4, 1, 2), this.graph.edges.get(0), "Should have one edge connected to v1 and v2");

        this.graph.addEdgeFromString("1;3;2");
        assertEquals(new Edge(2, 1, 3), this.graph.edges.get(1), "Should have a new edge connected to v1 and v3");

        this.graph.addEdgeFromString("1;3;2");
        assertEquals(new Edge(2, 1, 3), this.graph.edges.get(1), "Should not add repeated edge");
    }
}