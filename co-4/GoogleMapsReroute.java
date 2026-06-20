import java.util.*;

class Edge {
    int target, weight;

    Edge(int target, int weight) {
        this.target = target;
        this.weight = weight;
    }
}

class Node implements Comparable<Node> {
    int vertex, distance;

    Node(int vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public int compareTo(Node other) {
        return this.distance - other.distance;
    }
}

public class GoogleMapsReroute {

    static String[] names = {
            "KOR", "MGR", "HSR", "BTM",
            "JPN", "SIL", "BOM", "EC"
    };

    static List<Edge>[] graph = new ArrayList[8];

    static void addEdge(int u, int v, int w) {
        graph[u].add(new Edge(v, w));
        graph[v].add(new Edge(u, w));
    }

    static void removeEdge(int u, int v) {
        graph[u].removeIf(edge -> edge.target == v);
        graph[v].removeIf(edge -> edge.target == u);
    }

    static void dijkstra(int source, int destination) {
        int n = graph.length;
        int[] dist = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist[source] = 0;
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            for (Edge edge : graph[current.vertex]) {
                int newDist = dist[current.vertex] + edge.weight;

                if (newDist < dist[edge.target]) {
                    dist[edge.target] = newDist;
                    parent[edge.target] = current.vertex;
                    pq.add(new Node(edge.target, newDist));
                }
            }
        }

        System.out.println("Minimum Travel Time = " + dist[destination] + " minutes");

        List<Integer> path = new ArrayList<>();
        for (int v = destination; v != -1; v = parent[v]) {
            path.add(v);
        }
        Collections.reverse(path);

        System.out.print("Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(names[path.get(i)]);
            if (i != path.size() - 1)
                System.out.print(" -> ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        for (int i = 0; i < 8; i++) {
            graph[i] = new ArrayList<>();
        }

        // Roads with travel times (minutes)
        addEdge(0, 1, 8);   // KOR-MGR
        addEdge(0, 2, 5);   // KOR-HSR
        addEdge(0, 3, 6);   // KOR-BTM
        addEdge(1, 2, 4);   // MGR-HSR
        addEdge(1, 4, 7);   // MGR-JPN
        addEdge(2, 3, 2);   // HSR-BTM
        addEdge(2, 5, 6);   // HSR-SIL
        addEdge(3, 4, 5);   // BTM-JPN
        addEdge(4, 6, 4);   // JPN-BOM
        addEdge(5, 6, 3);   // SIL-BOM
        addEdge(6, 7, 5);   // BOM-EC

        System.out.println("Before Road Closure:");
        dijkstra(0, 7);

        // Accident closes road MGR ↔ HSR
        removeEdge(1, 2);

        System.out.println("\nAfter Road Closure (MGR-HSR blocked):");
        dijkstra(0, 7);
    }
}