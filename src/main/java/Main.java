import models.Graph;
import utils.FileManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph(0);
        FileManager fileManager = new FileManager("nonDirectedGraph.txt");
        graph = fileManager.readFile();

    }
}
