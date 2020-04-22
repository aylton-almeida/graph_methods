import models.Graph;
import utils.FileManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileManager f = new FileManager("nonDirectedGraph.txt");
        Graph g = new Graph(5);
        g = f.readNonDirectedGraphFile();
        g.printMinimumSpanningTree(0);
    }
}
