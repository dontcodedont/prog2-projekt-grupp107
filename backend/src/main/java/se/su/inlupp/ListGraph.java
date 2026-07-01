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
        try {
            if (adjacencyList.get(node).isEmpty()) {
                adjacencyList.remove(node);
            } else { // might need to crate a copy of adjacencyList to search through
                for (EdgeClass edge : adjacencyList.get(node)) {
                    for (EdgeClass edge2 : adjacencyList.get(edge.getDestination())) { // potential fault in logic -> will always return null
                        if (edge2.getDestination().equals(node)) {
                            adjacencyList.get(edge.getDestination()).remove(edge2);
                        }
                    }
                    adjacencyList.get(node).remove(edge);
                }
                adjacencyList.remove(node);
            }
        } catch (NoSuchElementException e) {
            System.out.println("No such node");
        }
        // throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean hasNode(T node) {
        return adjacencyList.containsKey(node);
        // throw new UnsupportedOperationException("Unimplemented method 'hasNode'");
    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {
        try {
            EdgeClass edge1 = new EdgeClass(node1, weight, name);
            EdgeClass edge2 = new EdgeClass(node2, weight, name);
            adjacencyList.get(node1).add(edge2); // big problem, should be node1 connected to e2 check if it breaks anything else
            adjacencyList.get(node2).add(edge1);
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
            // Might have made bad copies (referring to the same list instead of a copy)
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
            // might have made bad copies (referring to the same list instead of a copy)
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
        try {
            for (EdgeClass edge : adjacencyList.get(node1)) {
                if (edge.getDestination().equals(node2)) {
                    return edge;
                }
            }
            return null;
        } catch (NoSuchElementException e) {
            System.out.println("No such node");
            return null; // said missing return statement when having a return in the try block
        }
        // throw new UnsupportedOperationException("Unimplemented method 'getEdgeBetween'");
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }
}

