package se.su.inlupp;

import java.util.*;

public class BFSPathFinder<T> implements PathFinder<T> {

    @Override
    public Path<T> findPath(Graph<T> graph, T from, T to) {
        if (!graph.hasNode(from) || !graph.hasNode(to)) {
            return null;
        }
        LinkedList<T> queue = new LinkedList<>();
        HashSet<T> visited = new HashSet<>(); // the visited list is needed to know whether a neighboring node should be put in the queue, use hashSet
        Map<T, T> predecessors = new HashMap<>(); // maps the child node to the parent node so we can do map.get(node) to get a node's parent
        Map<T, Edge<T>> nodeToPredecessorEdge = new HashMap<>();  // maps the node to the edge that was taken to get it
        queue.add(from);
        visited.add(from);
        while (!queue.isEmpty()) {
            T current = queue.poll();
            if (current.equals(to)) {
                return reconstructPath(from, to, predecessors, nodeToPredecessorEdge);
            }
            for (Edge<T> neighborEdge : graph.getEdgesFrom(current)) {
                T neighbor = neighborEdge.getDestination();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    predecessors.put(neighbor, current);
                    nodeToPredecessorEdge.put(neighbor, neighborEdge);
                    queue.add(neighbor);
                }
            }
        }
        return null;
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

