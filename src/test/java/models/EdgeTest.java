package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    @Test
    void testEquals() {
        assertEquals(new Edge(0, 1, 2), new Edge(0, 1, 2), "should say edges are the same");
        assertEquals(new Edge(0, 1, 2), new Edge(0, 2, 1), "should say edges are the same with inverted vertices");
        assertNotEquals(new Edge(0, 3, 2), new Edge(0, 2, 1), "should say edges are not the same");
    }
}