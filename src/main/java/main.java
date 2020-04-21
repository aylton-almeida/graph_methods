import models.Graph;
import utils.FileManager;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        FileManager f = new FileManager("nonDirectedGraph.txt");
        Graph g = new Graph(4);
        g = f.readFile();
    }
}
