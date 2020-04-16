package utils;

import models.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {
    FileManager fileManager;

    @BeforeEach
    void setUp() {
        fileManager = new FileManager("nonDirectedGraph.txt");
    }

    @Test
    void readFile() {
        Graph graph = new Graph(3);
        graph.addEdgeFromString("1;2;4");
        graph.addEdgeFromString("1;3;7");
        graph.addEdgeFromString("2;3;10");

        try {
            assertEquals(graph, fileManager.readFile(), "should create a similar graph from the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}