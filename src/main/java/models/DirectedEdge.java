package models;

/**
 * The edge direction is guided by the ArrayList order, so given two vertices v1 and v2,
 * when the array is [v1, v2] the directions goes from v1 to v2, while [v2, v1] the direction
 * is from v2 to v1
 */
public class DirectedEdge extends Edge {
    /**
     * Creates edge from file string
     *
     * @param fileString String from file
     */
    DirectedEdge(String fileString) {
        super(Double.parseDouble(fileString.split(";")[2]));

        String[] strings = fileString.split(";");
        if (strings[3].equals("1")) {
            this.vertices.add(new Vertice(Integer.parseInt(strings[0].strip())));
            this.vertices.add(new Vertice(Integer.parseInt(strings[1].strip())));
        } else {
            this.vertices.add(new Vertice(Integer.parseInt(strings[1].strip())));
            this.vertices.add(new Vertice(Integer.parseInt(strings[0].strip())));
        }
    }

    /**
     * Creates edge from values
     *
     * @param weight Value needed to run the edge
     * @param v1     first vertice
     * @param v2     second vertice
     */
    DirectedEdge(double weight, int v1, int v2) {
        super(weight);
        this.vertices.add(new Vertice(v1));
        this.vertices.add(new Vertice(v2));
    }

    /**
     * Compares two directed edges and says if they are equal and point at the same direction
     *
     * @param o edge to be compared to
     * @return if the edges are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectedEdge that = (DirectedEdge) o;
        return Double.compare(that.weight, weight) == 0 &&
                vertices.equals(that.vertices);
    }

    /**
     * Compares two edges and says if they are equal ignoring their weight
     *
     * @param o edge to be compared to
     * @return if the edges are equal ignoring their weight
     */
    public boolean equalsIgnoreWeight(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectedEdge that = (DirectedEdge) o;
        return vertices.equals(that.vertices);
    }
}
