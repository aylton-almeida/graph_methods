package models;

public class MinimumSpanningTree {
    int[][] adjacencyMatrix;
    int startPrimIndex = 0;

    public MinimumSpanningTree(int[][] graph) {
        this.getMSTKruskal();
    }

    public MinimumSpanningTree(int[][] graph, int v1) {
        this.adjacencyMatrix = graph;
        this.startPrimIndex = v1;
        this.printPrim();
    }

    // A utility function to print the constructed MST stored in
    // parent[]
    void printMST(int parent[], int graph[][])
    {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < graph.length; i++)
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
    }

    // A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    int minKey(int key[], boolean mstSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < adjacencyMatrix.length; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }


    void printPrim() {
        // Array to store constructed MST
        int parent[] = new int[adjacencyMatrix.length];

        // Key values used to pick minimum weight edge in cut
        int key[] = new int[adjacencyMatrix.length];

        // To represent set of vertices not yet included in MST
        boolean mstSet[] = new boolean[adjacencyMatrix.length];

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
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);

            // Add the picked vertex to the MST Set
            mstSet[u] = true;

            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < adjacencyMatrix.length; v++)
                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (adjacencyMatrix[u][v] != 0 && mstSet[v] == false && adjacencyMatrix[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = adjacencyMatrix[u][v];
                }
        }
        printMST(parent, adjacencyMatrix);
    }

    MinimumSpanningTree getMSTKruskal() {
        return this;
    }

}
