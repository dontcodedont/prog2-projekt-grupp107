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
        try {
            this.weight = weight;
        } catch (IllegalArgumentException e) {
            System.out.println("weight cannot be negative");
        }
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getName() + " " + weight + " " + node;
    }
}
