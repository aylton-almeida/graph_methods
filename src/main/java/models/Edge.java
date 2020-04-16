package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Edge {
    final List<Vertice> vertices = new ArrayList<>();   //Should never be bigger than 2
    final double weight;

    /**
     * Create edge from values
     *
     * @param weight Value needed to run the edge
     * @param v1     first vertice
     * @param v2     second vertice
     */
    Edge(double weight, int v1, int v2) {
        this.vertices.add(new Vertice(v1));
        this.vertices.add(new Vertice(v2));
        this.weight = weight;
    }

    /**
     * Create edge from file string
     *
     * @param fileString String from file
     */
    Edge(String fileString) {
        String[] strings = fileString.split(";");
        this.vertices.add(new Vertice(Integer.parseInt(strings[0].strip())));
        this.vertices.add(new Vertice(Integer.parseInt(strings[1].strip())));
        this.weight = Double.parseDouble(strings[2]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.weight, weight) == 0 &&
                vertices.equals(edge.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, weight);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "vertices=" + vertices +
                ", weight=" + weight +
                '}';
    }
}
