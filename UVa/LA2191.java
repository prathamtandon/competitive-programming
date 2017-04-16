import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class LA2191 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int[] potmeters = new int[200000];
        int N = Integer.parseInt(reader.readLine());
        Potmeter potmeter = new Potmeter();
        int c = 1;
        while(true) {
            potmeter.init(N);
            for (int i = 0; i < N; i++) {
                potmeters[i] = Integer.parseInt(reader.readLine());
            }
            potmeter.buildTree(potmeters);
            String line;
            System.out.println("Case " + c + ":");
            while (!(line = reader.readLine()).equals("END")) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                String id = tokenizer.nextToken();
                int lo = Integer.parseInt(tokenizer.nextToken());
                int hi = Integer.parseInt(tokenizer.nextToken());
                if(id.equals("M"))
                    System.out.println(potmeter.query(lo - 1, hi - 1));
                else {
                    potmeter.update(lo - 1, hi - potmeters[lo - 1]);
                    potmeters[lo - 1] = hi;
                }
            }
            c++;
            N = Integer.parseInt(reader.readLine());
            if(N == 0)
                break;
            System.out.println();
        }
    }

    private static class Potmeter {
        private static int[] tree = new int[4 * 200000];
        private int N;

        void init(int N) {
            Arrays.fill(tree, 0);
            this.N = N;
        }

        void buildTree(int[] arr) {
            buildTree(0, arr, 0, N - 1);
        }

        private void buildTree(int node, int[] arr, int b, int e) {
            if(b == e) {
                tree[node] = arr[b];
                return;
            }
            int mid = (b + e) >> 1;
            int lnode = 2 * node + 1;
            int rnode = 2 * node + 2;
            buildTree(lnode, arr, b, mid);
            buildTree(rnode, arr, mid + 1, e);
            tree[node] = tree[lnode] + tree[rnode];
        }

        void update(int i, int diff) {
            update(0, 0, N - 1, i, diff);
        }

        private void update(int node, int b, int e, int i, int diff) {
            if(b > e || b > i || e < i)
                return;
            if(b == i && e == i) {
                tree[node] += diff;
                return;
            }
            int mid = (b + e) >> 1;
            int lnode = 2 * node + 1;
            int rnode = 2 * node + 2;
            update(lnode, b, mid, i, diff);
            update(rnode, mid + 1, e, i, diff);
            tree[node] = tree[lnode] + tree[rnode];
        }

        int query(int lo, int hi) {
            return query(0, 0, N - 1, lo, hi);
        }

        private int query(int node, int b, int e, int lo, int hi) {
            if(b > e || b > hi || e < lo)
                return 0;
            if(b >= lo && e <= hi) {
                return tree[node];
            }
            int mid = (b + e) >> 1;
            int lnode = 2 * node + 1;
            int rnode = 2 * node + 2;
            int r1 = query(lnode, b, mid, lo, hi);
            int r2 = query(rnode, mid + 1, e, lo, hi);
            return r1 + r2;
        }
    }
}