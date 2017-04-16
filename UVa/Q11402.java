import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Q11402 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(reader.readLine());
        int c = 1;
        PirateWorld pw = new PirateWorld();
        while (c++ <= TC) {
            StringBuilder sb = new StringBuilder();
            int M = Integer.parseInt(reader.readLine());
            while (M-- > 0) {
                int rep = Integer.parseInt(reader.readLine());
                String pirates = reader.readLine();
                for (int i = 0; i < rep; i++) {
                    sb = sb.append(pirates);
                }
            }
            pw.initTree(sb.length());
            pw.buildTree(sb.toString());
            int Q = Integer.parseInt(reader.readLine());
            System.out.println("Case " + (c - 1) + ":");
            int id = 1;
            while (Q-- > 0) {
                String query = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(query);
                String qid = tokenizer.nextToken();
                int lo = Integer.parseInt(tokenizer.nextToken());
                int hi = Integer.parseInt(tokenizer.nextToken());
                switch (qid) {
                    case "F":
                        pw.mutate(1, lo, hi);
                        break;
                    case "E":
                        pw.mutate(0, lo, hi);
                        break;
                    case "I":
                        pw.invert(lo, hi);
                        break;
                    case "S":
                        System.out.println("Q" + id + ": " + pw.query(lo, hi));
                        id++;
                }
            }
        }
    }

    private static class PirateWorld {
        private static int[] tree = new int[4 * 1024000];
        private static State[] states = new State[4 * 1024000];
        private int N;

        private enum State {
            Updated,
            PendingSet,
            PendingClear,
            PendingFlip
        }

        void initTree(int N) {
            this.N = N;
            for (int i = 0; i < tree.length; i++) {
                tree[i] = -1;
                states[i] = State.Updated;
            }
        }

        void buildTree(String s) {
            buildTree(0, s, 0, N - 1);
        }

        private void buildTree(int node, String s, int b, int e) {
            if(b == e) {
                tree[node] = s.charAt(b) == '1' ? 1 : 0;
                return;
            }
            int leftIndex = 2 * node + 1;
            int rightIndex = 2 * node + 2;
            int mid = (b + e) >> 1;
            buildTree(leftIndex, s, b , mid);
            buildTree(rightIndex, s, mid + 1 , e);
            tree[node] = tree[leftIndex] + tree[rightIndex];
        }

        void mutate(int code, int lo, int hi) {
            mutate(code, 0, 0, N - 1, lo, hi);
        }

        void invert(int lo, int hi) {
            mutate(2, lo, hi);
        }

        private void mutate(int code, int node, int b, int e, int lo, int hi) {
            propagate(node, b, e);
            if(b > hi || e < lo)
                return;
            if(b >= lo && e <= hi) {
                performOp(operationToState(code), node, b, e);
                updateChildren(operationToState(code), node, b, e);
            }
            else {
                int mid = (b + e) >> 1;
                int leftIndex = 2 * node + 1;
                int rightIndex = 2 * node + 2;
                mutate(code, leftIndex, b, mid, lo, hi);
                mutate(code, rightIndex, mid + 1, e, lo, hi);
                tree[node] = tree[leftIndex] + tree[rightIndex];
            }
        }

        private void performOp(State s, int n, int b, int e) {
            if(s == State.Updated)
                return;
            if(s == State.PendingSet)
                tree[n] = e - b + 1;
            else if(s == State.PendingClear)
                tree[n] = 0;
            else
                tree[n] = e - b + 1 - tree[n];
        }

        private void updateChildren(State s, int node, int b, int e) {
            if(b == e)
                return;
            int leftIndex = 2 * node + 1;
            int rightIndex = 2 * node + 2;
            updateState(leftIndex, states[leftIndex], s);
            updateState(rightIndex, states[rightIndex], s);
        }

        private State operationToState(int code) {
            if(code == 1)
                return State.PendingSet;
            if(code == 0)
                return State.PendingClear;
            return State.PendingFlip;
        }

        private void updateState(int n, State from, State to) {
            if(to == State.Updated)
                return;
            if(to == State.PendingSet || to == State.PendingClear)
                states[n] = to;
            else {
                if(from == State.Updated)
                    states[n] = to;
                else if(from == State.PendingSet)
                    states[n] = State.PendingClear;
                else if(from == State.PendingClear)
                    states[n] = State.PendingSet;
                else
                    states[n] = State.Updated;
            }
        }

        int query(int lo, int hi) {
            return query(0, 0, N - 1, lo, hi);
        }

        private int query(int node, int b, int e, int lo, int hi) {
            if(b > hi || e < lo)
                return -1;
            propagate(node, b, e);
            if(b >= lo && e <= hi) {
                return tree[node];
            }
            int mid = (b + e) >> 1;
            int leftIndex = 2 * node + 1;
            int rightIndex = 2 * node + 2;
            int r1 = query(leftIndex, b, mid, lo, hi);
            int r2 = query(rightIndex, mid + 1, e, lo, hi);
            if(r1 == -1)
                return r2;
            else if(r2 == -1)
                return r1;
            return r1 + r2;
        }

        private void propagate(int node, int b, int e) {
            performOp(states[node], node, b ,e);
            updateChildren(states[node], node, b, e);
            states[node] = State.Updated;
        }
    }
}