package models;

import java.util.Objects;

public class Vertice implements Comparable<Vertice>{
    final String name;

    /**
     * Makes the vertice name same as the pattern
     *
     * @param number vertice id
     */
    Vertice(int number) {
        this.name = "v" + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return name.equals(vertice.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Vertice{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Vertice o) {
        return Integer.compare(this.name.charAt(1), o.name.charAt(1));
    }
}
