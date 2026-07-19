package se.su.inlupp;

import java.util.*;

public class DFSPathFinder<T> implements PathFinder<T> {

    @Override
    public Path<T> findPath(Graph<T> graph, T from, T to) {
        if (!graph.hasNode(from) || !graph.hasNode(to)) {
            return null;
        }
        Set<T> visited = new HashSet<>();
        Map<T, T> predecessors = new HashMap<>(); // maps the child node to the parent node so we can do map.get(node) to get a node's parent
        Map<T, Edge<T>> nodeToPredecessorEdge = new HashMap<>(); // maps the node to the edge that was taken to get it
        boolean found = pathExplorer(graph, from, to, visited, predecessors, nodeToPredecessorEdge);
        if (!found) {
            return null;
        }
        return reconstructPath(from, to, predecessors, nodeToPredecessorEdge);
    }

    public boolean pathExplorer(Graph<T> graph, T current, T to, Set<T> visited, Map<T, T> predecessors, Map<T, Edge<T>> nodeToPredecessorEdge) {
        if (current.equals(to)) {
            return true;
        }
        visited.add(current);
        for (Edge<T> edge : graph.getEdgesFrom(current)) {
            T neighbor = edge.getDestination();
            if (!visited.contains(neighbor)) {
                predecessors.put(neighbor, current);
                nodeToPredecessorEdge.put(neighbor, edge);
                if (pathExplorer(graph, neighbor, to, visited, predecessors, nodeToPredecessorEdge)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Path<T> reconstructPath(T from, T to, Map<T, T> predecessors, Map<T, Edge<T>> nodeToPredecessorEdge) {
        LinkedList<T> orderedNodes = new LinkedList<>();
        LinkedList<Edge<T>> orderedEdges = new LinkedList<>();
        T current = to;
        orderedNodes.addFirst(current);
        while (!current.equals(from)) {
            Edge<T> edgeTaken = nodeToPredecessorEdge.get(current);
            T parent = predecessors.get(current);
            orderedEdges.addFirst(edgeTaken);
            orderedNodes.addFirst(parent);
            current = parent;
        }
        return new PathClass<>(from, to, orderedNodes, orderedEdges);
    }
}

