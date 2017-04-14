import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

class Q11235 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        SegmentTree it;
        while(!(line = reader.readLine()).trim().equals("0")) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            int n = Integer.parseInt(tokenizer.nextToken());
            int q = Integer.parseInt(tokenizer.nextToken());
            it = new SegmentTree(n);
            int[] arr = new int[n];
            int i = 0;
            tokenizer = new StringTokenizer(reader.readLine());
            while(tokenizer.hasMoreTokens())
                arr[i++] = Integer.parseInt(tokenizer.nextToken());
            it.buildTree(arr);
            while(q-- > 0) {
                tokenizer = new StringTokenizer(reader.readLine());
                int lo = Integer.parseInt(tokenizer.nextToken());
                int hi = Integer.parseInt(tokenizer.nextToken());
                System.out.println(it.query(arr, lo, hi));
            }
        }
    }

    private static class SegmentTree {
        private int[] tree;
        private HashMap<Integer, Integer> counts;
        private HashMap<Integer, Integer> starts;

        SegmentTree(int N) {
            int maxSize = 1000000;
            tree = new int[maxSize];
            counts = new HashMap<>();
            starts = new HashMap<>();
        }

        void buildTree(int[] arr) {
            buildCount(arr);
            buildStart(arr);
            buildTree(arr, 0, arr.length - 1, 0);
        }

        private void buildCount(int[] arr) {
            for (int i = 0; i < arr.length; i++) {
                if(counts.containsKey(arr[i]))
                    counts.put(arr[i], counts.get(arr[i]) + 1);
                else
                    counts.put(arr[i], 1);
            }
        }

        private void buildStart(int[] arr) {
            for (int i = 0; i < arr.length; i++) {
                if(!starts.containsKey(arr[i]))
                    starts.put(arr[i], i);
            }
        }

        private void buildTree(int[] arr, int lo, int hi, int node) {
            if(lo == hi) {
                tree[node] = counts.get(arr[lo]);
                return;
            }
            int leftIndex = 2 * node + 1;
            int rightIndex = 2 * node + 2;
            int mid = (lo + hi) >> 1;
            buildTree(arr, lo, mid, leftIndex);
            buildTree(arr, mid + 1, hi, rightIndex);
            tree[node] = tree[leftIndex] >= tree[rightIndex] ? tree[leftIndex] : tree[rightIndex];
        }

        private int query(int node, int lo, int hi, int r1, int r2) {
            if(lo > r2 || hi < r1)
                return -1;
            if(lo >= r1 && hi <= r2)
                return tree[node];
            int mid = (lo + hi) >> 1;
            int leftIndex = 2 * node + 1;
            int rightIndex = 2 * node + 2;
            int lr = query(leftIndex, lo, mid, r1, r2);
            int rr = query(rightIndex, mid + 1, hi, r1, r2);
            if(lr == -1)
                return rr;
            if(rr == -1)
                return lr;
            return Math.max(lr, rr);
        }

        int query(int[] arr, int lo, int hi) {
            lo--;
            hi--;
            if(arr[lo] == arr[hi])
                return hi - lo + 1;
            int cnt1 = counts.get(arr[lo]) + starts.get(arr[lo]) - lo;
            int cnt2 = hi - starts.get(arr[hi]) + 1;
            int ans = Math.max(cnt1, cnt2);
            int lo1 = starts.get(arr[lo]) + counts.get(arr[lo]);
            int hi1 = starts.get(arr[hi]) - 1;
            if(lo1 <= hi1)
                ans = Math.max(ans, query(0, 0, arr.length - 1, lo1, hi1));
            return ans;
        }
    }
}