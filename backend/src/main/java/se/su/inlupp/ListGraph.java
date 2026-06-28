package se.su.inlupp;

import java.util.*;

public class ListGraph<T> implements Graph<T> {

  private HashMap<T, Set<EdgeClass>> adjacencyList = new HashMap<T, Set<EdgeClass>>();

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
    throw new UnsupportedOperationException("Unimplemented method 'disconnect'");
  }

  @Override
  public void setConnectionWeight(T node1, T node2, int weight) {
    throw new UnsupportedOperationException("Unimplemented method 'setConnectionWeight'");
  }

  @Override
  public Set<T> getNodes() {
    return new HashSet<>(adjacencyList.keySet());
    // throw new UnsupportedOperationException("Unimplemented method 'getNodes'");
  }

  @Override
  public Collection<Edge<T>> getEdgesFrom(T node) {
    throw new UnsupportedOperationException("Unimplemented method 'getEdgesFrom'");
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

