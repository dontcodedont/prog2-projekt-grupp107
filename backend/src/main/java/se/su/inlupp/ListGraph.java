package se.su.inlupp;

import java.util.*;

public class ListGraph<T> implements Graph<T> {

    private final HashMap<T, HashSet<EdgeClass<T>>> adjacencyList = new HashMap<>();

    @Override
    public void add(T node) {
        if (!hasNode(node)) adjacencyList.put(node, new HashSet<>());
    }

    @Override
    public void remove(T node) {
        if (!hasNode(node)) {
            throw new NoSuchElementException("The node is not in the graph.");
        }
        if (adjacencyList.get(node).isEmpty()) {
            adjacencyList.remove(node);
        } else { // no need to crate a copy of adjacencyList to search through since the lambda-expression handles it
            adjacencyList.values().forEach(nodes -> nodes.removeIf(edge -> edge.getDestination().equals(node)));
            adjacencyList.remove(node);
        }
    }

    @Override
    public boolean hasNode(T node) {
        return adjacencyList.containsKey(node);
    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {
        if (!hasNode(node1) || !hasNode(node2)) {
            throw new NoSuchElementException("One or both nodes are not in the graph.");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("The weight cannot be negative.");
        }
        if (getEdgeBetween(node1, node2) != null) {
            throw new IllegalStateException("The nodes are already connected.");
        }
        EdgeClass<T> edge1 = new EdgeClass<>(node1, weight, name);
        EdgeClass<T> edge2 = new EdgeClass<>(node2, weight, name);
        adjacencyList.computeIfAbsent(node1, k -> new HashSet<>()).add(edge2);
        adjacencyList.computeIfAbsent(node2, k -> new HashSet<>()).add(edge1);
    }

    @Override
    public void disconnect(T node1, T node2) {
        if (!hasNode(node1) || !hasNode(node2)) {
            throw new NoSuchElementException("One or both nodes are not in the graph.");
        }
        if (getEdgeBetween(node1, node2) == null) {
            throw new IllegalStateException("No edge exists between both nodes");
        }
        HashSet<EdgeClass<T>> node1Edges = new HashSet<>(adjacencyList.get(node1));
        for (EdgeClass<T> edge : node1Edges) {
            if (edge.getDestination().equals(node2)) {
                adjacencyList.get(node1).remove(edge);
            }
        }
        HashSet<EdgeClass<T>> node2Edges = new HashSet<>(adjacencyList.get(node2));
        for (EdgeClass<T> edge : node2Edges) {
            if (edge.getDestination().equals(node1)) {
                adjacencyList.get(node2).remove(edge);
            }
        }
    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {
        if (!hasNode(node1) || !hasNode(node2) || getEdgeBetween(node1, node2) == null) {
            throw new NoSuchElementException("One or both nodes are not in the graph, or no edge exists between both nodes.");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("The weight of an edge cannot be negative.");
        }
        EdgeClass<T> edge1to2 = (EdgeClass<T>) getEdgeBetween(node1, node2);
        EdgeClass<T> edge2to1 = (EdgeClass<T>) getEdgeBetween(node2, node1);
        edge1to2.setWeight(weight);
        edge2to1.setWeight(weight);
    }

    @Override
    public Set<T> getNodes() {
        return new HashSet<>(adjacencyList.keySet());
    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {

        if (!hasNode(node)) {
            throw new NoSuchElementException("The node is not in the graph.");
        }
        return new ArrayList<>(adjacencyList.get(node));
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
    }

    public String toString() {
        StringBuilder graph = new StringBuilder("[");
        for (T node : adjacencyList.keySet()) {
            graph.append(node).append(", "); // ugly commas at the end
            for (EdgeClass<T> edge : adjacencyList.get(node)) {
                graph.append(edge.toString()).append(", "); // ugly commas at the end
            }
        }
        graph.append("]");
        return graph.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return adjacencyList.keySet().iterator();
    }
}

