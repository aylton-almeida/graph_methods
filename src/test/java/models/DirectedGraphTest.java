package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {
    DirectedGraph graph;

    @BeforeEach
    void setUp() {
        this.graph = new DirectedGraph(5);
    }

    @Test
    void addEdgeFromString() {
        assertEquals(0, this.graph.edges.size(), "Should have no edge");

        this.graph.addEdgeFromString("1;2;4;1");
        assertEquals(new DirectedEdge(4, 1, 2), this.graph.edges.get(0), "Should have one edge connected from v1 to v2");

        this.graph.addEdgeFromString("1;3;2;-1");
        assertEquals(new DirectedEdge(2, 3, 1), this.graph.edges.get(1), "Should have a new edge connected from v3 to v1");
    }

    @Test
    void getEntryDegree() {
        this.graph.addEdgeFromString("1;2;0;-1");
        this.graph.addEdgeFromString("1;3;0;-1");
        this.graph.addEdgeFromString("1;3;0;1");
        this.graph.addEdgeFromString("4;2;0;1");

        assertEquals(2, this.graph.getEntryDegree(new Vertice(1)), "should return degree 2");
        assertEquals(1, this.graph.getEntryDegree(new Vertice(2)), "should return degree 1");
        assertEquals(0, this.graph.getEntryDegree(new Vertice(4)), "should return degree 0");
        assertEquals(-1, this.graph.getEntryDegree(new Vertice(5)), "should return vertice is not part of graph");
    }

    @Test
    void getExitDegree() {
        this.graph.addEdgeFromString("1;2;0;-1");
        this.graph.addEdgeFromString("1;3;0;1");
        this.graph.addEdgeFromString("1;3;0;1");
        this.graph.addEdgeFromString("4;2;0;-1");

        assertEquals(2, this.graph.getExitDegree(new Vertice(1)), "should return degree 2");
        assertEquals(2, this.graph.getExitDegree(new Vertice(2)), "should return degree 1");
        assertEquals(0, this.graph.getExitDegree(new Vertice(4)), "should return degree 0");
        assertEquals(-1, this.graph.getExitDegree(new Vertice(5)), "should return vertice is not part of graph");
    }

    @Test
    void hasCycle(){
        DirectedGraph graph = new DirectedGraph(3);
        graph.addEdgeFromString("1;2;4;1");
        graph.addEdgeFromString("1;2;11;-1");
        graph.addEdgeFromString("1;3;7;1");
        graph.addEdgeFromString("2;3;10;-1");

        assertTrue(graph.hasCycle(), "should return it has a cycle");

        graph = new DirectedGraph(3);
        graph.addEdgeFromString("1;2;4;-1");
        graph.addEdgeFromString("3;2;11;-1");
        graph.addEdgeFromString("1;3;7;1");

        assertFalse(graph.hasCycle(), "should return is has no cycle");
    }
}