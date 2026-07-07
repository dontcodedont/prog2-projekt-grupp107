package se.su.inlupp;

public class EdgeClass<T> implements Edge<T> {
    private final T node;
    private int weight;
    private final String name;

    public EdgeClass(T node, int weight, String name) {
        this.node = node;
        this.weight = weight;
        this.name = name;
    }

    @Override
    public T getDestination() {
        return node;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int weight) {
            if (weight < 0) {
                throw new IllegalArgumentException("The weight of an edge cannot be negative.");
            }
            this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "till " + node + " med " + getName() + " tar " + weight;
    }
}
