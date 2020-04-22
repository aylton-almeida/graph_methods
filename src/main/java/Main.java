import models.Graph;
import utils.FileManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Prim's Expected Output");
        System.out.println("" +
                "Edge \tWeight\n" +
                "0 - 1\t7\n" +
                "4 - 2\t5\n" +
                "0 - 3\t5\n" +
                "1 - 4\t7\n" +
                "3 - 5\t6\n" +
                "4 - 6\t9");

        FileManager f = new FileManager("primExample.txt");
        Graph g = new Graph(5);
        g = f.readNonDirectedGraphFile();
        g.printMinimumSpanningTree(0);

//        System.out.println("Kruskal's Expected Output");
//        System.out.println("Edge \tWeight\n" +
//                "2 - 3   4\n" +
//                "0 - 3   5\n" +
//                "0 - 1   10");
//        System.out.println("-------------------------");
//
//        FileManager fm = new FileManager("kruskalExample.txt");
//        Graph gr = new Graph(5);
//        gr = fm.readNonDirectedGraphFile();
//        gr.printMinimumSpanningTree();
    }
}
