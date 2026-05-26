class Node {
    int sum;
    Node left, right;

    Node(int sum) {
        this.sum = sum;
        left = right = null;
    }
}

public class PersistentSegmentTree {

    static int[] arr = {12, 7, 25, 18, 9, 14, 6, 30};

    static Node build(int start, int end) {

        if (start == end) {
            return new Node(arr[start]);
        }

        int mid = (start + end) / 2;

        Node left = build(start, mid);
        Node right = build(mid + 1, end);

        Node node = new Node(left.sum + right.sum);
        node.left = left;
        node.right = right;

        return node;
    }

    // Persistent Update
    static Node update(Node prev, int start, int end, int index, int value) {

        if (start == end) {
            return new Node(prev.sum + value);
        }

        int mid = (start + end) / 2;

        Node node = new Node(0);

        if (index <= mid) {
            node.left = update(prev.left, start, mid, index, value);
            node.right = prev.right;
        } else {
            node.left = prev.left;
            node.right = update(prev.right, mid + 1, end, index, value);
        }

        node.sum = node.left.sum + node.right.sum;

        return node;
    }

    // Range Sum Query
    static int query(Node node, int start, int end, int l, int r) {

        if (r < start || end < l)
            return 0;

        if (l <= start && end <= r)
            return node.sum;

        int mid = (start + end) / 2;

        return query(node.left, start, mid, l, r)
                + query(node.right, mid + 1, end, l, r);
    }

    public static void main(String[] args) {

        int n = arr.length;

        // Version v0
        Node v0 = build(0, n - 1);

        // Update 1: stock[3] += 50
        Node v1 = update(v0, 0, n - 1, 2, 50);

        // Update 2: stock[6] -= 4
        Node v2 = update(v1, 0, n - 1, 5, -4);

        // Update 3: stock[3] -= 12
        Node v3 = update(v2, 0, n - 1, 2, -12);

        System.out.println("Version v0 Sum (3..6): "
                + query(v0, 0, n - 1, 2, 5));

        System.out.println("Version v1 Sum (3..6): "
                + query(v1, 0, n - 1, 2, 5));

        System.out.println("Version v2 Sum (3..6): "
                + query(v2, 0, n - 1, 2, 5));

        System.out.println("Version v3 Sum (3..6): "
                + query(v3, 0, n - 1, 2, 5));
    }
}