package se.su.inlupp;

import java.util.*;

public class ListGraph<T> implements Graph<T> {

    private HashMap<T, HashSet<EdgeClass>> adjacencyList = new HashMap<T, HashSet<EdgeClass>>();

    @Override
    public void add(T node) {
        if (!hasNode(node)) adjacencyList.put(node, new HashSet<>());
        // throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void remove(T node) {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean hasNode(T node) {
        return adjacencyList.containsKey(node);
        // throw new UnsupportedOperationException("Unimplemented method 'hasNode'");
    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {
        try {
            EdgeClass e1 = new EdgeClass(node1, weight, name);
            EdgeClass e2 = new EdgeClass(node2, weight, name);
            adjacencyList.get(node1).add(e1);
            adjacencyList.get(node2).add(e2);
        } catch (NoSuchElementException e) {
            System.out.println("No such node");
        } catch (IllegalArgumentException e) {
            System.out.println("weight cannot be negative");
        } catch (IllegalStateException e) {
            System.out.println("nodes are already connected");
        }
        throw new UnsupportedOperationException("Unimplemented method 'connect'");
    }

    @Override
    public void disconnect(T node1, T node2) {
        try {
            // Might cause problems when run multiple times or when a node counts as not existing anymore
            HashSet<EdgeClass> node1Edges = adjacencyList.get(node1);
            for (EdgeClass edge : node1Edges) {
                if (edge.getDestination().equals(node2)) {
                    adjacencyList.get(node1).remove(edge);
                }
            }
            HashSet<EdgeClass> node2Edges = adjacencyList.get(node2);
            for (EdgeClass edge : node2Edges) {
                if (edge.getDestination().equals(node1)) {
                    adjacencyList.get(node2).remove(edge);
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("No such node");
        } catch (IllegalStateException e) {
            System.out.println("No edge exists between nodes");
        }
        // throw new UnsupportedOperationException("Unimplemented method 'disconnect'");
    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {
        try {
            // might use other methods instead later
            HashSet<EdgeClass> node1Edges = adjacencyList.get(node1);
            for (EdgeClass edge : node1Edges) {
                if (edge.getDestination().equals(node2)) {
                    EdgeClass newWeightEdge = new EdgeClass(node2, weight, edge.getName());
                    adjacencyList.get(node1).remove(edge);
                    adjacencyList.get(node1).add(newWeightEdge);
                }
            }
            HashSet<EdgeClass> node2Edges = adjacencyList.get(node2);
            for (EdgeClass edge : node2Edges) {
                if (edge.getDestination().equals(node1)) {
                    EdgeClass newWeightEdge = new EdgeClass(node1, weight, edge.getName());
                    adjacencyList.get(node2).remove(edge);
                    adjacencyList.get(node2).add(newWeightEdge);
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("No such node");
        } catch (IllegalStateException e) {
            System.out.println("No edge exists between nodes");
        }
        // throw new UnsupportedOperationException("Unimplemented method 'setConnectionWeight'");
    }

    @Override
    public Set<T> getNodes() {
        return new HashSet<>(adjacencyList.keySet());
        // throw new UnsupportedOperationException("Unimplemented method 'getNodes'");
    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {
        try {
            // might be a disaster due to not understanding <T>
            Collection<Edge<T>> edgeClasses = (Collection<Edge<T>>)(Collection<T>) adjacencyList.get(node);
            return edgeClasses;
        } catch (NoSuchElementException e) {
            System.out.println("No such node");
            return null; // said missing return statement when having a return in the try block
        }
        // throw new UnsupportedOperationException("Unimplemented method 'getEdgesFrom'");
    }

    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) {
        throw new UnsupportedOperationException("Unimplemented method 'getEdgeBetween'");
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }
}

