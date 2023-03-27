package classic_cs_problems_in_Java.charpter_2_searchProblems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class GenericSearch {
    public static <T extends Comparable<T>> boolean linearContains(List<T> list, T key) {
        for (T item : list) {
            if (item.compareTo(key) == 0) {
                return true; // found a match
            }
        }
        return false;
    }

    // assumes *list* is already sorted
    public static <T extends Comparable<T>> boolean binaryContains(List<T> list, T key) {
        int low = 0;
        int high = list.size() - 1;
        
        while (low <= high) { // while there is still a search spaces
            int middle = (low + high) / 2;
            int comparison = list.get(middle).compareTo(key);
            if (comparison < 0) { // middle codon is < key
                low = middle + 1;
            } else if (comparison > 0) { // middle coden is > key
                high = middle - 1;
            } else { // middle codon is equal to key
                return true;
            }
        }
        return false;
    }

    // use the Node class to keep track of how we got from one state to another state (or from one place to another place) as we search.
    public static class Node<T> implements Comparable<Node<T>> {
        final T state;
        Node<T> parent;
        double cost;
        double heuristic;

        // for dfs and bfs we won't use cost and heuristic
        Node(T state, Node<T> parent) {
            this.state = state;
            this.parent = parent;
        }

        // for astar we will use cost and heuristic
        Node(T state, Node<T> parent, double cost, double heuristic) {
            this.state = state;
            this.parent = parent;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(Node<T> other) {
            Double mine = cost + heuristic;
            Double theirs = other.cost + other.heuristic;
            return mine.compareTo(theirs);
        }
    }
    // * A _goalTest_, being a Predicate<T>, is any function that takes a T (in our case, a MazeLocation) and returns a boolean.
    // * _successors_ is any function that takes a T and returns a List of T
    public static <T> Node<T> dfs(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        // frontier is where we've yet to go
        Stack<Node<T>> frontier = new Stack<>();
        frontier.push(new Node<>(initial,null));
        // explored is where we've been
        Set<T> explored = new HashSet<>();
        explored.add(initial);

        // keep going while there is more to explore
        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.pop();
            T currentState = currentNode.state;
            // if we found the goal, we're done
            if (goalTest.test(currentState)) {
                return currentNode;
            }
            // check where we can go next and haven't explored
            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue; // skip children we already explored
                }
                explored.add(child);
                frontier.push(new Node<>(child, currentNode));
            }
        }
        return null; // went through everything and ever found goal
        
    }

    public static <T> Node<T> bfs(T initial, Predicate<T> goalTest, Function<T,List<T>> successors) {
        // frontier is where we've yet to go
        Queue<Node<T>> frontier = new LinkedList<>();
        frontier.offer(new Node<>(initial, null));
        // explored is where've been
        Set<T> explored = new HashSet<>();
        explored.add(initial);

        // keep going while there is more to explore
        while (!frontier.isEmpty()){
            Node<T> currentNode = frontier.poll();
            T currentSate = currentNode.state;
            // if we found the goal, we're done
            if (goalTest.test(currentSate)) {
                return currentNode;
            }
            // check where we can go next and haven't explored
            for (T child : successors.apply(currentSate)) {
                if (explored.contains(child)) {
                    continue; // skip children we already explored
                } 
                explored.add(child);
                frontier.offer(new Node<>(child, currentNode));
            }
        }
        return null; // went through everything and never found goal
    }

    // The Path from the start to the goal can be reconstructed by working backward from this Node and its priors using the parent property.
    public static <T> List<T> nodeToPath(Node<T> node) {
        List<T> path = new ArrayList<>();
        path.add(node.state);
        // work backwards from end to front
        while (node.parent != null) {
            node = node.parent;
            path.add(0,node.state); // add to front
        }
        return path;
    }
   
   public static <T> Node<T> astar (T initial, Predicate<T> goalTest, Function<T,List<T>> successors, ToDoubleFunction<T> heuristic) {
    //    frontier is where we've yet to go
    PriorityQueue<Node<T>> frontier = new PriorityQueue<>();
    frontier.offer(new Node<>(initial, null, 0.0, heuristic.applyAsDouble(initial)));
    // explored is where we've been
    Map<T, Double> explored = new HashMap<>();
    explored.put(initial, 0.0);
    // keep going while there is mroe to explore
    while (!frontier.isEmpty()) {
        Node<T> currentNode = frontier.poll();
        T currentState = currentNode.state;
        // if we found the gal, we're done
        if (goalTest.test(currentState)) {
            return currentNode;
        }
        // check where we can go next and haven't explored
        for (T child : successors.apply(currentState)) {
            // 1 here assumes a grid, need a cost function for more sophisticated apps
            double newCost = currentNode.cost + 1;
            if (!explored.containsKey(child) || explored.get(child) > newCost) {
                explored.put(child, newCost);
                frontier.offer(new Node<>(child, currentNode, newCost, heuristic.applyAsDouble(child)));
            }
        }
    }
    return null; // went through everything and never found goal
   }
   
   
   
    public static void main(String[] args) {
        System.out.println(linearContains(List.of(1, 5, 15, 15, 15, 15, 20), 5)); // true
        System.out.println(binaryContains(List.of("a", "d", "e", "f", "z", "f"), "f")); // true
        System.out.println(binaryContains(List.of("john", "mark", "ronald", "sarah", "sheila"), "Janet")); // false

    }
}