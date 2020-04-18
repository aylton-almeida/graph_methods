package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    Graph graph;
    Graph graph2;

    @BeforeEach
    void setUp() {
        this.graph = new Graph(3);
        this.graph2 = new Graph(3);
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
    }

    @Test
    void isAdjacent() {
        this.graph.addEdgeFromString("1;2;4");
        this.graph.addEdgeFromString("1;3;4");

        assertTrue(this.graph.isAdjacent(new Vertice(1), new Vertice(2)), "should return that vertices<1,2> are adjacent");
        assertTrue(this.graph.isAdjacent(new Vertice(2), new Vertice(1)), "should return that vertices<2,1> are adjacent");
        assertTrue(this.graph.isAdjacent(new Vertice(1), new Vertice(3)), "should return that vertices<1,3> are adjacent");
        assertFalse(this.graph.isAdjacent(new Vertice(3), new Vertice(2)), "should return that vertices are not adjacent");
    }

    @Test
    void getDegree() {
        this.graph.addEdgeFromString("1;2;0");
        this.graph.addEdgeFromString("1;3;0");
        this.graph.addEdgeFromString("1;3;0");
        this.graph.addIsolatedVertice(new Vertice(5));

        assertEquals(this.graph.getDegree(new Vertice(1)), 3, "should return degree 3");
        assertEquals(this.graph.getDegree(new Vertice(2)), 1, "should return degree 1");
        assertEquals(this.graph.getDegree(new Vertice(5)), 0, "should return degree 0");
        assertEquals(this.graph.getDegree(new Vertice(4)), -1, "should return vertice is not part of graph");

    }

    @Test
    void isIsolated() {
        this.graph.addEdgeFromString("1;2;0");
        this.graph.addIsolatedVertice(new Vertice(3));

        assertFalse(this.graph.isIsolated(new Vertice(1)), "should return vertice1 is not isolated");
        assertFalse(this.graph.isIsolated(new Vertice(5)), "should return vertice2 is not isolated");
        assertTrue(this.graph.isIsolated(new Vertice(3)), "should return that vertice is isolated");
    }

    @Test
    void addIsolatedVertice(){
        this.graph.addIsolatedVertice(new Vertice(1));

        assertTrue(this.graph.vertices.contains(new Vertice(1)), "should return that it contains the vertice");
        assertTrue(this.graph.isIsolated(new Vertice(1)), "should return that the vertice is isolated");

    }

    @Test
    void isPending() {
        this.graph.addEdgeFromString("1;2;4");
        this.graph.addEdgeFromString("1;3;7");
        this.graph.addEdgeFromString("2;3;10");
        this.graph.addEdgeFromString("4;2;3");

        assertFalse(this.graph.isPending(new Vertice(1)), "should return that vertice is not pending");
        assertTrue(this.graph.isPending(new Vertice(4)), "should return that vertice is pending");
    }

    @Test
    void isRegular() {
        this.graph.addEdgeFromString("1;2;4");
        this.graph.addEdgeFromString("1;3;7");
        this.graph.addEdgeFromString("2;3;10");

        this.graph2.addEdgeFromString("1;2;4");
        this.graph2.addEdgeFromString("1;3;7");
        this.graph2.addEdgeFromString("2;3;10");
        this.graph2.addEdgeFromString("4;2;3");

        assertTrue(this.graph.isRegular(), "should return that graph is regular");
        assertFalse(this.graph2.isRegular(), "should return that graph is not regular");
    }

    @Test
    void isNull() {
        this.graph2.addEdgeFromString("1;2;4");

        assertTrue(this.graph.isNull(), "should return that graph is null");
        assertFalse(this.graph2.isNull(), "should return that graph is not null");
    }

}