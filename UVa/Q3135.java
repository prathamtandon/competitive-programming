import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Q3135 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        Argus argus = new Argus();
        while(!(line = reader.readLine()).equals("#")) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            tokenizer.nextToken();
            int qId = Integer.parseInt(tokenizer.nextToken());
            int period = Integer.parseInt(tokenizer.nextToken());
            argus.registerQuery(qId, period);
        }
        int K = Integer.parseInt(reader.readLine());
        argus.printTopK(K);
    }

    private static class Argus {
        private static class Query implements Comparable<Query> {
            int id;
            int period;
            int current;

            Query(int id, int period, int current) {
                this.id = id;
                this.period = period;
                this.current = current;
            }

            @Override
            public int compareTo(Query o) {
                if(current == o.current)
                    return id - o.id;
                return current - o.current;
            }
        }
        private PriorityQueue<Query> priorityQueue;
        Argus() {
            priorityQueue = new PriorityQueue<>();
        }
        void registerQuery(int qId, int period) {
            Query q = new Query(qId, period, period);
            priorityQueue.add(q);
        }
        void printTopK(int K) {
            Set<Query> result = new TreeSet<>();
            for (int i = 0; i < K; i++) {
                Query t = priorityQueue.remove();
                result.add(t);
                priorityQueue.add(new Query(t.id, t.period, t.current + t.period));
            }
            for(Query q: result)
                System.out.println(q.id);
        }
    }
}