class Edge {
    String src, dest;
    int cost;

    Edge(String s, String d, int c) {
        src = s;
        dest = d;
        cost = c;
    }
}

public class MetroMST {

    static int find(int parent[], int i) {
        if (parent[i] == i)
            return i;

        return find(parent, parent[i]);
    }

    static void union(int parent[], int x, int y) {
        int xset = find(parent, x);
        int yset = find(parent, y);

        parent[xset] = yset;
    }

    public static void main(String[] args) {

        String[] stations = {
                "M", "K", "W", "S", "E", "Y", "H"
        };

        Edge[] edges = {

                new Edge("M", "K", 8),
                new Edge("M", "W", 12),
                new Edge("M", "S", 10),
                new Edge("M", "E", 7),
                new Edge("M", "Y", 9),
                new Edge("M", "H", 11),

                new Edge("K", "W", 5),
                new Edge("W", "S", 6),
                new Edge("S", "E", 4),
                new Edge("E", "Y", 8),
                new Edge("Y", "H", 9),
                new Edge("K", "H", 14)
        };

        // Bubble Sort edges by cost
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - i - 1; j++) {

                if (edges[j].cost > edges[j + 1].cost) {

                    Edge temp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = temp;
                }
            }
        }

        int[] parent = new int[7];

        for (int i = 0; i < 7; i++) {
            parent[i] = i;
        }

        int totalCost = 0;

        System.out.println("Minimum Cost Metro Connections:\n");

        int edgeCount = 0;

        for (Edge edge : edges) {

            int u = -1;
            int v = -1;

            for (int i = 0; i < stations.length; i++) {

                if (stations[i].equals(edge.src))
                    u = i;

                if (stations[i].equals(edge.dest))
                    v = i;
            }

            int setU = find(parent, u);
            int setV = find(parent, v);

            if (setU != setV) {

                System.out.println(
                        edge.src + " - " +
                        edge.dest + " : " +
                        edge.cost + " cr"
                );

                totalCost += edge.cost;

                union(parent, setU, setV);

                edgeCount++;
            }

            if (edgeCount == stations.length - 1)
                break;
        }

        System.out.println("\nTotal Minimum Cost: " + totalCost + " cr");

        System.out.println(
                "\nRedundancy Requirement:\n" +
                "Additional paths between M and W can be added\n" +
                "to provide two edge-disjoint routes."
        );
    }
}