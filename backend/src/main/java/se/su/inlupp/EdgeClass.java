package se.su.inlupp;

public class EdgeClass<T> implements Edge<T> {
    private T node;
    private int weight;
    private String name;

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
