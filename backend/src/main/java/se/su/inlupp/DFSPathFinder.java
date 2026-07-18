package se.su.inlupp;

import java.util.*;

public class DFSPathFinder<T> implements PathFinder<T> {

  @Override
  public Path<T> findPath(Graph<T> graph, T from, T to) {
    if (!graph.hasNode(from) || !graph.hasNode(to)) {
      return null;
    }
    Set<T> visited = new HashSet<>();

    // start creating a path from the beginning to add current node to traversedNodes along the way
      // OR to avoid having to remove irrelevant nodes after realizing that the path is wrong make a separate visitedNodes list for this class
    // is "current" the "to" node, then reconstruct the taken path
      // to reconstruct the path we need to know the "parent" of "current" up until the "from" node with a linkedList or hashMap?
    // look at the current node's neighbors if they've not been visited yet make that one the new current node
    // put the other neighbors on a "stack" or a "to be explored list"
    // throw new UnsupportedOperationException("Unimplemented method 'findPath'");
    return null; // todo remove
  }

  public boolean helper() {
    return false;
  }

  public Path<T> reconstructPath() {
    return null;
  }
}

