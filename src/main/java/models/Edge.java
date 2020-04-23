package models;

import java.util.*;

public class Edge implements Comparable<Edge> {
    final List<Vertice> vertices = new ArrayList<>(); // Should never be bigger than 2
    final int weight;

    /**
     * Default Constructor
     * @param weight Value needed to run the edge
     */
    Edge(Integer weight) {this.weight = weight;}

    /**
     * Creates edge from values
     *
     * @param weight Value needed to run the edge
     * @param v1     first vertice
     * @param v2     second vertice
     */
    Edge(int weight, int v1, int v2) {
        this.vertices.add(new Vertice(v1));
        this.vertices.add(new Vertice(v2));
        this.weight = weight;
    }

    /**
     * Creates edge from two vertices
     *
     * @param v1 first vertice
     * @param v2 second vertice
     */
    Edge(Vertice v1, Vertice v2) {
        this.vertices.add(v1);
        this.vertices.add(v2);
        this.weight = 0;
    }

    /**
     * Creates edge from file string
     *
     * @param fileString String from file
     */
    Edge(String fileString) {
        String[] strings = fileString.split(";");
        this.vertices.add(new Vertice(Integer.parseInt(strings[0].strip())));
        this.vertices.add(new Vertice(Integer.parseInt(strings[1].strip())));
        this.weight = Integer.parseInt(strings[2]);
    }

    /**
     * Compares two edges and says if they are equal
     *
     * @param o edge to be compared to
     * @return if the edges are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Edge edge = (Edge) o;
        boolean isNormalOrderEqual = vertices.equals(edge.vertices);
        Collections.reverse(vertices);
        boolean isReverseOrderEqual = vertices.equals(edge.vertices);
        return Double.compare(edge.weight, weight) == 0 && (isNormalOrderEqual || isReverseOrderEqual);
    }

    /**
     * Compares two edges and says if they are equal ignoring their weight
     *
     * @param e edge to be compared to
     * @return if the edges are equal ignoring their weight
     */
    public boolean equalsIgnoreWeight(Edge e) {
        if (this == e)
            return true;
        if (e == null)
            return false;
        boolean isNormalOrderEqual = vertices.equals(e.vertices);
        Collections.reverse(vertices);
        boolean isReverseOrderEqual = vertices.equals(e.vertices);
        return isNormalOrderEqual || isReverseOrderEqual;
    }

    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, weight);
    }

    @Override
    public String toString() {
        return "Edge{" + "vertices=" + vertices + ", weight=" + weight + '}';
    }
}
