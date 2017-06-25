import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Q318 {

    private static class Pair {
        int first;
        int second;
    }

    private static class Result implements Comparable<Result> {
        int u;
        int v;
        double t;

        Result(int u, int v, double t) {
            this.u = u;
            this.v = v;
            this.t = t;
        }

        @Override
        public int compareTo(Result o) {
            return new Double(t).compareTo(o.t);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean[] hasKeyDominoFallen = new boolean[502];
        double[] keyDominoFallTime = new double[502];
        ArrayList<Pair>[] AdjList = new ArrayList[502];
        PriorityQueue<Result> pq;
        int testCase = 0;
        while(true) {
            String line = reader.readLine().trim();
            if(line.length() == 0)
                continue;
            StringTokenizer tokenizer = new StringTokenizer(line);
            int N = Integer.parseInt(tokenizer.nextToken());
            int M = Integer.parseInt(tokenizer.nextToken());
            if(N == 0 && M == 0)
                break;
            Arrays.fill(hasKeyDominoFallen, false);
            Arrays.fill(keyDominoFallTime, 0.0);
            for (int i = 0; i < 502; i++) {
                AdjList[i] = new ArrayList<>();
            }
            for (int i = 0; i < M; i++) {
                tokenizer = new StringTokenizer(reader.readLine().trim());
                int u = Integer.parseInt(tokenizer.nextToken());
                int v = Integer.parseInt(tokenizer.nextToken());
                int l = Integer.parseInt(tokenizer.nextToken());
                Pair p = new Pair();
                p.first = v;
                p.second = l;
                AdjList[u].add(p);
                p = new Pair();
                p.first = u;
                p.second = l;
                AdjList[v].add(p);
            }
            pq = new PriorityQueue<>();
            Result r = new Result(1, -1, 0);
            pq.add(r);
            while(!pq.isEmpty()) {
                r = pq.remove();
                if(r.v > 0 || hasKeyDominoFallen[r.u])
                    continue;
                hasKeyDominoFallen[r.u] = true;
                keyDominoFallTime[r.u] = r.t;
                for (int i = 0; i < AdjList[r.u].size(); i++) {
                    Pair p = AdjList[r.u].get(i);
                    if(hasKeyDominoFallen[p.first] && keyDominoFallTime[p.first] + p.second > r.t) {
                        pq.add(new Result(r.u, p.first, r.t + (keyDominoFallTime[p.first] + p.second - r.t) / 2.0));
                    } else if(!hasKeyDominoFallen[p.first]) {
                        boolean found = false;
                        for (int j = 0; j < pq.toArray().length; j++) {
                            Result temp = (Result)pq.toArray()[j];
                            if(temp.u == p.first && temp.v == -1) {
                                found = true;
                                if(temp.t > r.t + p.second) {
                                    pq.remove(temp);
                                    pq.add(new Result(p.first, -1, r.t + p.second));
                                }
                                break;
                            }
                        }
                        if(!found)
                            pq.add(new Result(p.first, -1, r.t + p.second));
                    }
                }
            }
            testCase++;
            System.out.println("System #" + testCase);
            String fallTime = String.format("%.1f", r.t);
            if(r.v == -1) {
                System.out.println("The last domino falls after " + fallTime + (fallTime.equals("1.0") ? " " +
                        "second, " : " " +
                        "seconds, ") +
                        "at key domino " + r.u + ".");
            } else {
                int minNode = Math.min(r.u, r.v);
                int maxNode = Math.max(r.u, r.v);
                System.out.println("The last domino falls after " + fallTime + (fallTime.equals("1.0") ? " second, "
                        : " seconds, ") +
                        "between key dominoes " + minNode + " and " + maxNode + ".");
            }
            System.out.println();
        }

    }
}