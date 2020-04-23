package models;

import java.util.Arrays;

public class MinimumSpanningTree {
    Graph graph;
    Edge[] edge;
    int[][] adjacencyMatrix;
    int startPrimIndex = 0;

    // get MST with Kruskal's algorithm
    public MinimumSpanningTree(Graph graph) {
        this.graph = graph;
        edge = new Edge[graph.edges.size()];
        for (int i = 0; i < graph.edges.size(); ++i)
            edge[i] = graph.edges.get(i);
        this.printKruskal();
    }

    // get MST with Prim's algorithm
    /**
     * create MST with Prim's algorithm
     *
     * @param v1 start vertice
     */
    public MinimumSpanningTree(int[][] graph, int v1) {
        this.adjacencyMatrix = graph;
        this.startPrimIndex = v1;
        this.printPrim();
    }

    // A utility function to print the constructed MST stored in parent[]
    void printMSTPrim(int[] parent, int[][] graph) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < graph.length; i++)
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
    }

    // A utility function to find the vertice with minimum key value, from the set of vertices not yet included in MST
    int minKey(int[] key, boolean[] mstSet) {
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < adjacencyMatrix.length; v++) {
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        }

        return min_index;
    }

    // print mst based on prim's algorithm
    void printPrim() {
        System.out.println("----- MST PRIM -----");
        // Array to store constructed MST
        int[] parent = new int[adjacencyMatrix.length];

        // Key values used to pick minimum weight edge in cut
        int[] key = new int[adjacencyMatrix.length];

        // To represent set of vertices not yet included in MST
        boolean[] mstSet = new boolean[adjacencyMatrix.length];

        // Initialize all keys as INFINITE
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        // Always include first 1st vertex in MST.
        key[0] = startPrimIndex; // Make key 0 so that this vertex is picked as first vertex
        parent[0] = -1; // First node is always root of MST

        // The MST will have V vertices
        for (int count = 0; count < adjacencyMatrix.length - 1; count++) {
            // Pick thd minimum key vertex from the set of vertices not yet included in MST
            int u = minKey(key, mstSet);

            // Add the picked vertex to the MST Set
            mstSet[u] = true;

            // Update key value and parent index of the adjacent vertices of the picked vertex.
            // Consider only those vertices which are not yet included in MST
            for (int v = 0; v < adjacencyMatrix.length; v++)
                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (adjacencyMatrix[u][v] != 0 && !mstSet[v] && adjacencyMatrix[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = adjacencyMatrix[u][v];
                }
        }
        printMSTPrim(parent, adjacencyMatrix);
        System.out.println("-----------------");
    }

    // A class to represent a subset for union-find (kruskal)
    class subset {
        int parent, rank;
    };

    // A utility function to find set of an element i (uses path compression technique)
    // path compression technique: since each element visited on the way to a root is part of the same set, all of these visited elements can be reattached directly to the root.
    int find(subset[] subsets, int i) {
        // find root and make root as parent of i (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    // A function that does union of two sets of x and y (uses union by rank)
    void Union(subset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root of high rank tree
        // (Union by Rank)
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

            // If ranks are same, then make one as root and increment
            // its rank by one
        else
        {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    // get MST with Kruskal
    void printKruskal() {
        System.out.println("----- MST KRUSKAL -----");

        Edge[] result = new Edge[graph.verticesNumber];  // This will store the resultant MST
        int e = 0;  // An index variable, used for result[]
        int i = 0;  // An index variable, used for sorted edges
        for (i = 0; i < graph.verticesNumber; ++i)
            result[i] = new Edge(0);

        // Step 1:  Sort all the edges in non-decreasing order of their weight.
        // If we are not allowed to change the given graph, we can create a copy of array of edges
        Arrays.sort(edge);

        // Allocate memory for creating adjacencyMatrix.length subsets
        subset[] subsets = new subset[graph.verticesNumber];
        for (i = 0; i < graph.verticesNumber; ++i)
            subsets[i] = new subset();

        // Create V subsets with single elements
        for (int v = 0; v < graph.verticesNumber; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0;  // Index used to pick next edge

        // Number of edges to be taken is equal to V-1
        while (e < graph.verticesNumber - 1) {
            // Step 2: Pick the smallest edge and increment the index for next iteration
            Edge next_edge = new Edge(0);
            next_edge = edge[i++];

            // x = source and y = destination
            int x = find(subsets, graph.vertices.indexOf(next_edge.vertices.get(0)));
            int y = find(subsets, graph.vertices.indexOf(next_edge.vertices.get(1)));

            // If including this edge does't cause cycle, include it in result and increment the index of result for next edge
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }

        // prints MST
        System.out.print("" + "Edge    \tWeight\n");
        for (i = 0; i < e; ++i) {
            System.out.println((graph.vertices.indexOf(result[i].vertices.get(0))) + " - " + (graph.vertices.indexOf(result[i].vertices.get(1))) + "      " + result[i].weight);
        }

        System.out.println("-----------------");
    }

}
