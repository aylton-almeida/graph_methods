package utils;

import models.DirectedGraph;
import models.Graph;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileManagerTest {
    FileManager fileManager;

    // it will crash because nonDirectedGraph file has changed
    @Test
    void readNonDirectedGraphFile() {
        fileManager = new FileManager("nonDirectedGraph.txt");

        Graph graph = new Graph(3);
        graph.addEdgeFromString("1;2;4");
        graph.addEdgeFromString("1;3;7");
        graph.addEdgeFromString("2;3;10");

        try {
            assertEquals(graph, fileManager.readNonDirectedGraphFile(), "should create a similar graph from the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readDirectedGraphFile() {
        fileManager = new FileManager("directedGraph.txt");

        DirectedGraph graph = new DirectedGraph(3);
        graph.addEdgeFromString("1;2;4;1");
        graph.addEdgeFromString("1;2;11;-1");
        graph.addEdgeFromString("1;3;7;1");
        graph.addEdgeFromString("2;3;10;-1");

        try {
            assertEquals(graph, fileManager.readDirectedGraphFile(), "should create a similar graph from the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}