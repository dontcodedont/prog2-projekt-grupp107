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
    public void remove(T node) {
        if (!hasNode(node)) {
            throw new NoSuchElementException("The node is not in the graph.");
        }
        if (adjacencyList.get(node).isEmpty()) {
            adjacencyList.remove(node);
        } else { // no need to crate a copy of adjacencyList to search through since lambda-expressions handle it
            adjacencyList.values().forEach(nodes -> nodes.removeIf(edge -> edge.getDestination().equals(node)));
            adjacencyList.remove(node);
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
        if (!hasNode(node1) || !hasNode(node2)) {
            throw new NoSuchElementException("One or both nodes are not in the graph.");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("The weight cannot be negative.");
        }
        if (getEdgeBetween(node1, node2) != null) {
            throw new IllegalStateException("The nodes are already connected.");
        }
        EdgeClass<T> edge1 = new EdgeClass<T>(node1, weight, name);
        EdgeClass<T> edge2 = new EdgeClass<T>(node2, weight, name);
        adjacencyList.computeIfAbsent(node1, k -> new HashSet<>()).add(edge2);
        adjacencyList.computeIfAbsent(node2, k -> new HashSet<>()).add(edge1);
        // throw new UnsupportedOperationException("Unimplemented method 'connect'");
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
        // throw new UnsupportedOperationException("Unimplemented method 'disconnect'");
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
        // throw new UnsupportedOperationException("Unimplemented method 'setConnectionWeight'");
    }

    @Override
    public Set<T> getNodes() {
        return new HashSet<>(adjacencyList.keySet());
        // throw new UnsupportedOperationException("Unimplemented method 'getNodes'");
    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {

        if (!hasNode(node)) {
            throw new NoSuchElementException("The node is not in the graph.");
        }
        Collection<Edge<T>> edgeClasses = (Collection<Edge<T>>) (Collection<T>) adjacencyList.get(node);// might be a bad due to not understanding <T>
        return edgeClasses;
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

