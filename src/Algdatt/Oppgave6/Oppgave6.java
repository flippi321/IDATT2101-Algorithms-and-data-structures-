package Algdatt.Oppgave6;
import java.io.*;
import java.util.*;

class Oppgave6 {
    // Java Program to Implement Dijkstra's Algorithm
// Using Priority Queue

    // Main class DPQ
    public static class GraphSolver {

        // Member variables of this class
        private int dist[];
        private Set<Integer> settled;
        private PriorityQueue<Node> pq;
        // Number of vertices
        private int V;
        List<List<Node> > adj;

        // Constructor of this class
        public GraphSolver(int V)
        {

            // This keyword refers to current object itself
            this.V = V;
            dist = new int[V];
            settled = new HashSet<Integer>();
            pq = new PriorityQueue<Node>(V, new Node());
        }

        // Method 1
        // Dijkstra's Algorithm
        public void dijkstra(List<List<Node> > adj, int src)
        {
            this.adj = adj;

            for (int i = 0; i < V; i++)
                dist[i] = Integer.MAX_VALUE;

            // Add source node to the priority queue
            pq.add(new Node(src, 0));

            // Distance to the source is 0
            dist[src] = 0;

            while (settled.size() != V) {

                // Terminating condition check when
                // the priority queue is empty, return
                if (pq.isEmpty())
                    return;

                // Removing the minimum distance node
                // from the priority queue
                int u = pq.remove().node;

                // Adding the node whose distance is
                // finalized
                if (settled.contains(u))

                    // Continue keyword skips execution for
                    // following check
                    continue;

                // We don't have to call e_Neighbors(u)
                // if u is already present in the settled set.
                settled.add(u);

                e_Neighbours(u);
            }
        }

        // Method 2
        // To process all the neighbours
        // of the passed node
        private void e_Neighbours(int u)
        {

            int edgeDistance = -1;
            int newDistance = -1;

            // All the neighbors of v
            for (int i = 0; i < adj.get(u).size(); i++) {
                Node v = adj.get(u).get(i);

                // If current node hasn't already been processed
                if (!settled.contains(v.node)) {
                    edgeDistance = v.cost;
                    newDistance = dist[u] + edgeDistance;

                    // If new distance is cheaper in cost
                    if (newDistance < dist[v.node])
                        dist[v.node] = newDistance;

                    // Add the current node to the queue
                    pq.add(new Node(v.node, dist[v.node]));
                }
            }
        }

        // Main driver method
        public static void main(String arg[]) throws IOException {
            int V = 5;
            int source = 1;
            String link = "src/Algdatt/Oppgave6/file";
            BufferedReader reader = new BufferedReader(new FileReader(link));
            String line;
            String[] values;
            int fromNode;
            int toNode;
            int value;

            // Adjacency list representation of the
            // connected edges by declaring List class object
            // Declaring object of type List<Node>
            List<List<Node>> adj = new ArrayList<List<Node> >();

            // Gets the first two numbers of the list
            line = line = reader.readLine();
            line = line.replaceAll("[ ]+", " ");
            values = line.split(" ");

            // Creates all the Nodes in the list
            for (int i = 0; (i < (Integer.parseInt(values[0]))); i++) {
                System.out.println(i);
                List<Node> item = new ArrayList<Node>();
                adj.add(item);
            }

            // Defines the Edges between the nodes
            while((line = reader.readLine()) != null) {
                line = line.replaceAll("[ ]+", " ");
                values = line.split(" ");
                fromNode = Integer.parseInt(values[0]);
                toNode = Integer.parseInt(values[1]);
                value = Integer.parseInt(values[2]);
                /*
                System.out.println(fromNode);
                System.out.println(toNode);
                System.out.println(value);
                */
                adj.get(fromNode).add(new Node(toNode, value));
            }

            // Calculating the single source shortest path
            GraphSolver dpq = new GraphSolver(V);
            dpq.dijkstra(adj, source);

            // Printing the shortest path to all the nodes
            // from the source node
            System.out.println("The shorted path from node " + source + ":");
            String length = "";
            for (int i = 0; i < dpq.dist.length; i++) {
                if (dpq.dist[i] == 0) length = "Starter";
                else if (dpq.dist[i] == Integer.MAX_VALUE) length = "Unreachable";
                else length = String.valueOf(dpq.dist[i]);
                System.out.println(source + " to " + i + " is " + length);
            }
        }
    }

    // Class 2
// Helper class implementing Comparator interface
// Representing a node in the graph
    static class Node implements Comparator<Node> {

        // Member variables of this class
        public int node;
        public int cost;

        // Constructors of this class

        // Constructor 1
        public Node() {}

        // Constructor 2
        public Node(int node, int cost)
        {

            // This keyword refers to current instance itself
            this.node = node;
            this.cost = cost;
        }

        // Method 1
        @Override public int compare(Node node1, Node node2)
        {

            if (node1.cost < node2.cost)
                return -1;

            if (node1.cost > node2.cost)
                return 1;

            return 0;
        }
    }
}