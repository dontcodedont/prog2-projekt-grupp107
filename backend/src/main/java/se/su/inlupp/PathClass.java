package se.su.inlupp;

import java.util.*;

public class PathClass<T> implements Path<T> {

    private final T startNode;
    private final T endNode;
    private final List<T> traversedNodes;
    private final List<Edge<T>> traversedEdges;

    public PathClass(T startNode, T endNode, List<T> nodes, List<Edge<T>> edges) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.traversedNodes = nodes;
        this.traversedEdges = edges;
    }

    public T getStart() {
        return startNode;
    }

    public T getEnd() {
        return endNode; // might need to be the destination of the last node instead
    }

    public int getTotalWeight() {
        int totalWeight = 0;
        for (Edge<T> edge : traversedEdges) {
            totalWeight += edge.getWeight();
        }
        return totalWeight;
    }

    public List<Edge<T>> getEdges() {
        return traversedEdges;
    }

    public List<T> getNodes() {
        return traversedNodes;
    }

    public String toString() {
        StringBuilder path = new StringBuilder();
        for (int i  = 0; i < traversedNodes.size(); i++) {
            path.append(traversedNodes.get(i).toString());
            if (i <  traversedNodes.size() - 1) {
                path.append(traversedEdges.get(i).toString());
            }
        }
        path.append(getTotalWeight());
        return path.toString();
    }

    public Iterator<Edge<T>> iterator() {
        return traversedEdges.iterator();
    }
}
