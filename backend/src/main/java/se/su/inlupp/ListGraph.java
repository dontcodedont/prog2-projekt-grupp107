package se.su.inlupp;

import java.util.*;

public class ListGraph<T> implements Graph<T> {

    private HashMap<T, HashSet<EdgeClass<T>>> adjacencyList = new HashMap<>(); // is hashset not initialized due to no "new"?

    @Override
    public void add(T node) {
        if (!hasNode(node)) adjacencyList.put(node, new HashSet<>());
        // throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void remove(T node) { //TODO DO NOT PUT EXCEPTION CHECKS INSIDE TRY-CATCH BLOCKS!!!
        try {
            // nothing gets thrown NoSuchElementException doesn't get caught maybe because of ConcurrentModificationException?
            if (!hasNode(node)) {
                throw new NoSuchElementException();
            }
            if (/*adjacencyList.get(node) == null || */adjacencyList.get(node).isEmpty()) {
                adjacencyList.remove(node);
            } else { // might need to crate a copy of adjacencyList to search through
                adjacencyList.values().forEach(nodes -> nodes.removeIf(edge -> edge.getDestination().equals(node)));
                adjacencyList.remove(node);
            }
        } catch (NoSuchElementException e) {
            System.out.println("The node is not in the graph.");
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
            if (!hasNode(node1) || !hasNode(node2)) {
                throw new NoSuchElementException();
            }
            if (weight < 0) {
                throw new IllegalArgumentException();
            }
            if (getEdgeBetween(node1, node2) != null) {
                throw new IllegalStateException();
            }
            EdgeClass<T> edge1 = new EdgeClass<T>(node1, weight, name);
            EdgeClass<T> edge2 = new EdgeClass<T>(node2, weight, name);
            adjacencyList.computeIfAbsent(node1, k -> new HashSet<>()).add(edge2);
            adjacencyList.computeIfAbsent(node2, k -> new HashSet<>()).add(edge1);
            // adjacencyList.get(node1).add(edge2);
            // adjacencyList.get(node2).add(edge1);
        } catch (NoSuchElementException e) {
            System.out.println("One or both nodes are not in the graph.");
        } catch (IllegalArgumentException e) {
            System.out.println("The weight cannot be negative.");
        } catch (IllegalStateException e) {
            System.out.println("The nodes are already connected.");
        }
        // throw new UnsupportedOperationException("Unimplemented method 'connect'");
    }

    @Override
    public void disconnect(T node1, T node2) {
        try {
            // Might cause problems when run multiple times or when a node counts as not existing anymore
            // Might have created bad copies (referring to the same list instead of a copy)
            if (!hasNode(node1) || !hasNode(node2)) {
                throw new NoSuchElementException();
            }
            if (getEdgeBetween(node1, node2) == null) {
                throw new IllegalStateException();
            }
            HashSet<EdgeClass<T>> node1Edges = adjacencyList.get(node1);
            for (EdgeClass<T> edge : node1Edges) {
                if (edge.getDestination().equals(node2)) {
                    adjacencyList.get(node1).remove(edge);
                }
            }
            HashSet<EdgeClass<T>> node2Edges = adjacencyList.get(node2);
            for (EdgeClass<T> edge : node2Edges) {
                if (edge.getDestination().equals(node1)) {
                    adjacencyList.get(node2).remove(edge);
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("One or both nodes are not in the graph.");
        } catch (IllegalStateException e) {
            System.out.println("No edge exists between both nodes");
        }
        // throw new UnsupportedOperationException("Unimplemented method 'disconnect'");
    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {
        try {
            // might use other methods instead later
            // might have made bad copies (referring to the same list instead of a copy)
            if (!hasNode(node1) || !hasNode(node2) || getEdgeBetween(node1, node2) == null) {
                throw new NoSuchElementException();
            }
            if (weight < 0) {
                throw new IllegalArgumentException();
            }
            HashSet<EdgeClass<T>> node1Edges = adjacencyList.get(node1);
            for (EdgeClass<T> edge : node1Edges) {
                if (edge.getDestination().equals(node2)) {
                    EdgeClass<T> newWeightEdge = new EdgeClass<T>(node2, weight, edge.getName());
                    adjacencyList.get(node1).remove(edge);
                    adjacencyList.get(node1).add(newWeightEdge);
                }
            }
            HashSet<EdgeClass<T>> node2Edges = adjacencyList.get(node2);
            for (EdgeClass<T> edge : node2Edges) {
                if (edge.getDestination().equals(node1)) {
                    EdgeClass<T> newWeightEdge = new EdgeClass<T>(node1, weight, edge.getName());
                    adjacencyList.get(node2).remove(edge);
                    adjacencyList.get(node2).add(newWeightEdge);
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("One or both nodes are not in the graph, or no edge exists between both nodes.");
        } catch (IllegalArgumentException e) {
            System.out.println("The weight of an edge cannot be negative.");
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
            if (!hasNode(node)) {
                throw new NoSuchElementException();
            }
            Collection<Edge<T>> edgeClasses = (Collection<Edge<T>>) (Collection<T>) adjacencyList.get(node);// might be a disaster due to not understanding <T>
            return edgeClasses;
        } catch (NoSuchElementException e) {
            System.out.println("The node is not in the graph.");
        }
        return Collections.emptySet();// might be a disaster due to not understanding <T>
        // throw new UnsupportedOperationException("Unimplemented method 'getEdgesFrom'");
    }

    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) {
        if (!hasNode(node1) || !hasNode(node2)) {
            throw new NoSuchElementException("One or both nodes are missing in the graph.");
        }
        var edges = adjacencyList.get(node1);
        if (edges == null) {
            return null;
        }
        for (EdgeClass<T> edge : edges) {
            if (edge.getDestination().equals(node2)) {
                return edge;
            }
        }
        return null;
        // throw new UnsupportedOperationException("Unimplemented method 'getEdgeBetween'");
    }

    public String toString() {
        String graph = "Graph adjacency list:\n";
        for (T node : adjacencyList.keySet()) {
            graph += "Node " + node + " is connected to node: "; // the node of type <T> might not get represented well as a String
            for (EdgeClass<T> edge : adjacencyList.get(node)) {
                graph += edge.getDestination() + " ";
            }
            graph += "\n";
        }
        return graph;
    }

    @Override
    public Iterator<T> iterator() {
        return adjacencyList.keySet().iterator();
        // throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }
}

