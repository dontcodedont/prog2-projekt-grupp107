package se.su.inlupp;

import java.util.*;

public class DFSPathFinder<T> implements PathFinder<T> {

    @Override
    public Path<T> findPath(Graph<T> graph, T from, T to) {
        if (!graph.hasNode(from) || !graph.hasNode(to)) {
            return null;
        }
        Set<T> visited = new HashSet<>();
        Map<T, Edge<T>> nodeToPredecessorEdge = new HashMap<>();
        Map<T, T> predecessors = new HashMap<>();
        boolean found = pathExplorer(graph, from, to, visited, predecessors, nodeToPredecessorEdge);
        if (!found) {
            return null;
        }
        return reconstructPath(from, to, predecessors, nodeToPredecessorEdge);
    }

    public boolean pathExplorer(Graph<T> graph, T current, T end, Set<T> visited, Map<T, T> predecessors, Map<T, Edge<T>> nodeToPredecessorEdge) {
        if (current.equals(end)) {
            return true;
        }
        visited.add(current);
        for (Edge<T> edge : graph.getEdgesFrom(current)) {
            T neighbor = edge.getDestination();
            if (!visited.contains(neighbor)) {
                predecessors.put(neighbor, current);
                nodeToPredecessorEdge.put(neighbor, edge);
                if (pathExplorer(graph, neighbor, end, visited, predecessors, nodeToPredecessorEdge)) {
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

