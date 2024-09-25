package com.app.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

class Graph {
    private int V; // Number of vertices
    private List<List<Integer>> adj; // Adjacency list representation of graph

    // Constructor
    public Graph(int vertices) {
        this.V = vertices;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    // Function to add an edge to the graph
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }

    // Helper function to perform topological sort using DFS
    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        // Mark the current node as visited
        visited[v] = true;

        // Recur for all the vertices adjacent to this vertex
        for (int neighbor : adj.get(v)) {
            if (!visited[neighbor]) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }

        // Push current vertex to stack which stores the result
        stack.push(v);
    }

    // Function to perform topological sorting
    private Stack<Integer> topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];

        // Perform DFS from each vertex to do topological sort
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        return stack;
    }

    // Function to find the longest path in the DAG starting from the source vertex
    public int longestPath(int src) {
        Stack<Integer> topOrder = topologicalSort(); // Step 1: Get topological order

        // Step 2: Initialize distances to all vertices as minus infinity except the source
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MIN_VALUE);
        dist[src] = 0;

        // Step 3: Process vertices in topological order
        while (!topOrder.isEmpty()) {
            int u = topOrder.pop();

            // Update distances of all adjacent vertices of u
            if (dist[u] != Integer.MIN_VALUE) {
                for (int neighbor : adj.get(u)) {
                    if (dist[neighbor] < dist[u] + 1) {
                        dist[neighbor] = dist[u] + 1;
                    }
                }
            }
        }

        // Step 4: Find the maximum distance
        int longestPath = Arrays.stream(dist).max().getAsInt();
        return longestPath;
    }

    public static void main(String[] args) {
        // Create a graph given in the example
        Graph g = new Graph(6);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);

        int startVertex = 0;
        System.out.println("The longest path starting from vertex " + startVertex + " is: " + g.longestPath(startVertex));
    }
}

