import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Q11297 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(reader.readLine());
        Census census = new Census();
        StringTokenizer tokenizer;
        census.initTree(N);
        int[][] arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }
        census.buildTree(arr);
        int q = Integer.parseInt(reader.readLine());
        while(q-- > 0) {
            tokenizer = new StringTokenizer(reader.readLine());
            String type = tokenizer.nextToken();
            if(type.equals("q")) {
                int x1 = Integer.parseInt(tokenizer.nextToken());
                int y1 = Integer.parseInt(tokenizer.nextToken());
                int x2 = Integer.parseInt(tokenizer.nextToken());
                int y2 = Integer.parseInt(tokenizer.nextToken());
                x1--; y1--; x2--; y2--;
                Pair result = census.query(arr, x1, y1, x2, y2);
                System.out.println(result.max + " " + result.min);
            } else {
                int x1 = Integer.parseInt(tokenizer.nextToken());
                int y1 = Integer.parseInt(tokenizer.nextToken());
                int v = Integer.parseInt(tokenizer.nextToken());
                x1--; y1--;
                census.update(arr, x1, y1, v);
            }
        }
    }

    private static class Pair {
        int max;
        int min;

        Pair(int max, int min) {
            this.max = max;
            this.min = min;
        }
    }

    private static class Census {
        private int[][] tree;

        void initTree(int N) {
            int tsize = 1000000;
            tree = new int[tsize][2];
            for (int i = 0; i < tsize; i++) {
                tree[i][0] = Integer.MIN_VALUE;
                tree[i][1] = Integer.MAX_VALUE;
            }
        }

        void buildTree(int[][] arr) {
            buildTree(0, arr, 0, 0, arr.length - 1, arr.length - 1);
        }

        private void buildTree(int node, int[][] arr, int x1, int y1, int x2, int y2) {
            if(x1 > x2 || y1 > y2)
                return;
            if(x1 == x2 && y1 == y2) {
                tree[node][0] = arr[x1][y1];
                tree[node][1] = arr[x1][y1];
                return;
            }
            int mx = (x1 + x2) >> 1;
            int my = (y1 + y2) >> 1;
            int c1 = 4 * node + 1;
            buildTree(c1, arr, x1, y1, mx, my);
            int c2 = 4 * node + 2;
            buildTree(c2, arr, mx + 1, y1, x2, my);
            int c3 = 4 * node + 3;
            buildTree(c3, arr, x1, my + 1, mx, y2);
            int c4 = 4 * node + 4;
            buildTree(c4, arr, mx + 1, my + 1, x2, y2);
            tree[node][0] = Math.max(tree[c1][0], Math.max(tree[c2][0], Math.max(tree[c3][0], tree[c4][0])));
            tree[node][1] = Math.min(tree[c1][1], Math.min(tree[c2][1], Math.min(tree[c3][1], tree[c4][1])));
        }

        Pair query(int[][] arr, int x1, int y1, int x2, int y2) {
            return query(0, 0, 0, arr.length - 1, arr.length - 1, x1, y1, x2, y2);
        }

        private Pair query(int node, int b1, int e1, int b2, int e2, int x1, int y1, int x2, int y2) {
            if(b1 > x2 || b2 < x1 || e1 > y2 || e2 < y1)
                return new Pair(Integer.MIN_VALUE, Integer.MAX_VALUE);
            if(b1 >= x1 && e1 >= y1 && b2 <= x2 && e2 <= y2)
                return new Pair(tree[node][0], tree[node][1]);
            int mb = (b1 + b2) >> 1;
            int me = (e1 + e2) >> 1;
            int c1 = 4 * node + 1;
            int c2 = 4 * node + 2;
            int c3 = 4 * node + 3;
            int c4 = 4 * node + 4;
            Pair r1 = query(c1, b1, e1, mb, me, x1, y1, x2, y2);
            Pair r2 = query(c2, mb + 1, e1, b2, me, x1, y1, x2, y2);
            Pair r3 = query(c3, b1, me + 1, mb, e2, x1, y1, x2, y2);
            Pair r4 = query(c4, mb + 1, me + 1, b2, e2, x1, y1, x2, y2);
            int max = Math.max(r1.max, Math.max(r2.max, Math.max(r3.max, r4.max)));
            int min = Math.min(r1.min, Math.min(r2.min, Math.min(r3.min, r4.min)));
            return new Pair(max, min);
        }

        void update(int[][] arr, int x, int y, int v) {
            update(0, 0, 0, arr.length - 1, arr.length - 1, x, y, x, y, v);
        }

        private void update(int node, int b1, int e1, int b2, int e2, int x1, int y1, int x2, int y2, int v) {
            if(b1 == x1 && e1 == y1 && b2 == x2 && e2 == y2) {
                tree[node][0] = v;
                tree[node][1] = v;
                updateParent(node);
            } else if(!(b1 > x2 || b2 < x1 || e1 > y2 || e2 < y1)) {
                int mb = (b1 + b2) >> 1;
                int me = (e1 + e2) >> 1;
                int c1 = 4 * node + 1;
                int c2 = 4 * node + 2;
                int c3 = 4 * node + 3;
                int c4 = 4 * node + 4;
                update(c1, b1, e1, mb, me, x1, y1, x2, y2, v);
                update(c2, mb + 1, e1, b2, me, x1, y1, x2, y2, v);
                update(c3, b1, me + 1, mb, e2, x1, y1, x2, y2, v);
                update(c4, mb + 1, me + 1, b2, e2, x1, y1, x2, y2, v);
            }
        }

        private void updateParent(int node) {
            if(node == 0)
                return;
            int parent = (node - 1) / 4;
            int c1 = 4 * parent + 1;
            int c2 = 4 * parent + 2;
            int c3 = 4 * parent + 3;
            int c4 = 4 * parent + 4;
            tree[parent][0] = Math.max(tree[c1][0], Math.max(tree[c2][0], Math.max(tree[c3][0], tree[c4][0])));
            tree[parent][1] = Math.min(tree[c1][1], Math.min(tree[c2][1], Math.min(tree[c3][1], tree[c4][1])));
            updateParent(parent);
        }
    }
}