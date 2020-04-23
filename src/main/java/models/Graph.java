package models;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Graph {
    int verticesNumber;
    final List<Vertice> vertices = new ArrayList<>();
    final List<Edge> edges = new ArrayList<>();

    public void setVerticesNumber(int verticesNumber) {
        this.verticesNumber = verticesNumber;
    }

    Graph(int verticesNumber, List<Vertice> vertices) {
        this.verticesNumber = verticesNumber;
        this.vertices.addAll(vertices);
    }

    public Graph(int verticesNumber) {
        this.setVerticesNumber(verticesNumber);
    }

    /**
     * Add edge to graph from string and update vertices
     *
     * @param edgeString edge string
     */
    public void addEdgeFromString(String edgeString) {
        this.edges.add(new Edge(edgeString));
        this.updateVertices();
    }

    /**
     * Add edge to graph from 2 edges
     *
     * @param v1 first edge
     * @param v2 second edge
     */
    void addEdge(Vertice v1, Vertice v2) {
        this.edges.add(new Edge(v1, v2));
        this.updateVertices();
    }

    /**
     * Checks if edge is inside edges list ignoring its weight
     *
     * @param e edge to be checked
     * @return if the edge is inside the edges list ignoring it's weight
     */
    boolean edgesContainsIgnoringWeight(Edge e) {
        boolean contains = false;
        for (Edge edge : this.edges)
            if (edge.equalsIgnoreWeight(e))
                contains = true;
        return contains;
    }

    /**
     * Add isolated vertice
     *
     * @param v1 vertice to be added
     */
    public void addIsolatedVertice(Vertice v1) {
        if (!this.vertices.contains(v1)) this.vertices.add(v1);
    }

    /**
     * Removes vertices from graph
     *
     * @param v vertice to be removed
     * @return list of edges removed
     */
    List<Edge> removeVertice(Vertice v) {
        List<Edge> toBeRemovedEdges = new ArrayList<>();
        for (Edge e : this.edges) {
            if (e.vertices.contains(v)) {
                toBeRemovedEdges.add(e);
            }
        }
        this.edges.removeAll(toBeRemovedEdges);
        return toBeRemovedEdges;
    }

    /**
     * Update vertices list
     */
    public void updateVertices() {
        this.edges.forEach(e -> e.vertices.forEach(v -> {
            if (!this.vertices.contains(v)) this.vertices.add(v);
        }));
        this.vertices.sort(Vertice::compareTo);
    }

    /**
     * Tests if two vertices are adjacent or not
     *
     * @param v1 first vertice
     * @param v2 second vertice
     * @return if they are adjacent or not
     */
    public boolean isAdjacent(Vertice v1, Vertice v2) {
        Edge testEdge = new Edge(v1, v2);
        boolean isAdjacent = false;
        for (Edge edge : edges)
            if (testEdge.equalsIgnoreWeight(edge)) {
                isAdjacent = true;
                break;
            }
        return isAdjacent;
    }

    /**
     * Returns the degree for the given vertice
     *
     * @param v1 vertice to be analyzed
     * @return vertice degree or -1 if vertice is not part of the graph
     */
    int getDegree(Vertice v1) {
        return this.vertices.contains(v1)
                ? (int) this.edges.stream().filter(edge -> edge.vertices.contains(v1)).count()
                : -1;
    }

    /**
     * if the graph is eulerian or not, that is, if all it's vertices has degree 2
     *
     * @return returns true or false
     */
    public boolean isEulerian() {
        for (Vertice vertice : vertices) {
            if (getDegree(vertice) % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns if the vertice is isolated in this graph
     *
     * @param v1 vertice to be tested
     * @return true or false accordingly
     */
    boolean isIsolated(Vertice v1) {
        return this.getDegree(v1) == 0;
    }

    /**
     * Returns if the graph has a eulerian path, that is, only two vertices with odd degree
     *
     * @return if the graph is unicursal
     */
    public boolean isUnicursal() {
        int cont = 0;
        for (Vertice vertice : vertices) {
            if (getDegree(vertice) % 2 != 0) {
                cont++;
            }
        }
        return cont == 2;
    }

    /**
     * Given two graphs with the same vertices, the complementary of the first graph will have all
     * edges it didn't have
     *
     * @return the complementary Graph from this
     */
    public Graph getComplementary() {
        Graph newGraph = new Graph(verticesNumber, this.vertices);

        for (Vertice v1 : this.vertices)
            for (Vertice v2 : this.vertices)
                if (!v1.equals(v2)
                        && !this.edgesContainsIgnoringWeight(new Edge(v1, v2))
                        && !newGraph.edges.contains(new Edge(v1, v2)))
                    newGraph.addEdge(v1, v2);

        return newGraph;
    }

    /**
     * Returns if the vertice is pending or not
     *
     * @param v1 vertice to be analyzed
     * @return if vertice is pending or not
     */
    public boolean isPending(Vertice v1) {
        return this.getDegree(v1) == 1;
    }

    /**
     * Returns if the graph is regular or not
     *
     * @return if the graph is regular or not
     */
    public boolean isRegular() {
        List<Integer> degrees = new ArrayList<>();
        this.vertices.forEach(vertice -> degrees.add(this.getDegree(vertice)));
        boolean isRegular = true;
        int firstDegree = degrees.get(0);
        for (int degree : degrees) {
            if (degree != firstDegree) {
                isRegular = false;
                break;
            }
        }
        return isRegular;
    }

    /**
     * Returns if the graph is null or not
     *
     * @return if the graph is null or not
     */
    public boolean isNull() {
        return this.edges.isEmpty();
    }

    /**
     * Returns if the graph has loops or not
     *
     * @return if the graph has loops or not
     */
    boolean hasLoops() {
        boolean hasLoops = false;
        for (Edge edge : this.edges) {
            if (edge.vertices.get(0).equals(edge.vertices.get(1))) {
                hasLoops = true;
                break;
            }
        }
        return hasLoops;
    }

    /**
     * Returns if the graph has parallel edges or not
     *
     * @return if the graph has parallel edges or not
     */
    boolean hasParallelEdges() {
        boolean hasParallelEdges = false;
        // for each edge, check if there is another edge linking its vertices
        List<Edge> filteredEdges = new ArrayList<>(this.edges);
        int loopCount = 0;
        for (Edge testEdge : this.edges) {
            // check if current tested edge exists in remaining edges
            if (loopCount != 0) {
                filteredEdges.clear();
                filteredEdges.addAll(this.edges);
            }
            filteredEdges.remove(testEdge);
            for (Edge edge : filteredEdges) {
                if (edge.equalsIgnoreWeight(testEdge)) {
                    hasParallelEdges = true;
                    break;
                }
            }
            loopCount++;
        }
        return hasParallelEdges;
    }

    /**
     * Returns if the graph is simple or not
     *
     * @return if the graph is simple or not
     */
    public boolean isSimple() {
        return !this.hasLoops() && !this.hasParallelEdges();
    }

    /**
     * Returns if the graph is complete or not
     *
     * @return if the graph is complete or not
     */
    public boolean isComplete() {
        // for each vertice, check if it is adjacent to all other vertices
        if (this.isSimple()) {
            List<Vertice> filteredVertices = new ArrayList<>(this.vertices);
            int loopCount = 0;
            for (Vertice testVertice : this.vertices) {
                // check if current tested edge is adjacent in remaining vertices
                if (loopCount != 0) {
                    filteredVertices.clear();
                    filteredVertices.addAll(this.vertices);
                }
                filteredVertices.remove(testVertice);
                for (Vertice vertice : filteredVertices) {
                    if (!this.isAdjacent(testVertice, vertice)) {
                        return false;
                    }
                }
                loopCount++;
            }
            return true;
        }
        return false;
    }

    /**
     * Depth-first search
     */
    void DepthFirstSearch(int source, boolean[] visited) {

        //mark the vertice as visited
        visited[source] = true;

        List<Vertice> adjList = this.edges
                .stream()
                .filter(e -> e.vertices.contains(this.vertices.get(source)))
                .map(e -> {
                    int i = e.vertices.indexOf(this.vertices.get(source));
                    return i == 0 ? e.vertices.get(1) : e.vertices.get(0);
                })
                .collect(Collectors.toList());

        for (Vertice v : adjList) {
            int index = this.vertices.indexOf(v);
            if (!visited[index]) {
                //make recursive call from neighbor
                DepthFirstSearch(index, visited);
            }
        }
    }

    /**
     * Returns if the graph is connected or not
     *
     * @return if the graph is connected or not
     */
    public boolean isConnected() {
        //created visited array
        boolean[] visited = new boolean[verticesNumber];

        DepthFirstSearch(0, visited);

        //check if all the vertices are visited, if yes then graph is connected
        int count = 0;
        for (boolean b : visited) {
            if (b)
                count++;
        }
        return verticesNumber == count;
    }

    /**
     * Get the number of cut vertices in the graph
     *
     * @return the number of cut vertices
     */
    int getCutVertices() {
        int countVertices = 0;
        for (int i = 0; i < this.vertices.size(); i++) {
            Vertice v = this.vertices.get(i);
            List<Edge> removedEdges = this.removeVertice(v);
            this.setVerticesNumber(--verticesNumber);
            this.vertices.remove(v);
            if (!this.isConnected()) countVertices++;
            this.edges.addAll(removedEdges);
            this.setVerticesNumber(++verticesNumber);
            this.vertices.add(0, v);
        }
        return countVertices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        return verticesNumber == graph.verticesNumber &&
                vertices.equals(graph.vertices) &&
                edges.equals(graph.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(verticesNumber, vertices, edges);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "verticesNumber=" + verticesNumber +
                ", vertices=" + vertices +
                ", edges=" + edges +
                '}';
    }
}
