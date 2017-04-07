import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Q11492 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        WordSequence sequence;
        while(!(line = reader.readLine()).trim().equals("0")) {
            int n = Integer.parseInt(line);
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            sequence = new WordSequence(tokenizer.nextToken(), tokenizer.nextToken());
            while(n > 0) {
                tokenizer = new StringTokenizer(reader.readLine());
                sequence.addTriple(tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                n--;
            }
            int result = sequence.getSequenceLength();
            System.out.println(result != Integer.MAX_VALUE ? result : "impossivel");
        }
    }

    private static class WordSequence {
        private String origin;
        private String destination;
        private HashMap<String, Integer> languageToId;
        private HashMap<Integer, ArrayList<Edge>> adjacencyList;

        private static class Edge implements Comparable<Edge> {
            int to;
            int cost;
            int initial;

            Edge(int to, int cost, int initial) {
                this.to = to;
                this.cost = cost;
                this.initial = initial;
            }

            @Override
            public int compareTo(Edge o) {
                return cost - o.cost;
            }
        }

        WordSequence(String origin, String destination) {
            this.origin = origin;
            this.destination = destination;
            this.languageToId = new HashMap<>();
            this.adjacencyList = new HashMap<>();
        }

        int getId(String lang) {
            if(languageToId.containsKey(lang))
                return languageToId.get(lang);
            int id = languageToId.size();
            languageToId.put(lang, id);
            adjacencyList.put(id, new ArrayList<>());
            return id;
        }

        void addTriple(String lang1, String lang2, String word) {
            int u = getId(lang1);
            int v = getId(lang2);
            int c = Character.toLowerCase(word.charAt(0)) - 'a';
            Edge e = new Edge(v, word.length(), c);
            adjacencyList.get(u).add(e);
            e = new Edge(u, word.length(), c);
            adjacencyList.get(v).add(e);
        }

        int getSequenceLength() {
            int len = Integer.MAX_VALUE;
            int oId = getId(origin);
            int dId = getId(destination);
            if(!adjacencyList.containsKey(oId) || !adjacencyList.containsKey(dId))
                return len;
            int[][] dist = new int[4002][27];
            for (int i = 0; i < dist.length; i++) {
                for (int j = 0; j <= 26; j++) {
                    dist[i][j] = Integer.MAX_VALUE;
                }
            }
            for (int i = 0; i <= 26; i++) {
                dist[oId][i] = 0;
            }
            PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
            priorityQueue.add(new Edge(oId, 0, 26));
            while(!priorityQueue.isEmpty()) {
                Edge e = priorityQueue.remove();
                int u = e.to;
                int du = e.cost;
                int iu = e.initial;
                if(dist[u][iu] < du)
                    continue;
                for(Edge o: adjacencyList.get(u)) {
                    int v = o.to;
                    int dv = o.cost;
                    int iv = o.initial;
                    if(iu == iv)
                        continue;
                    if(dist[v][iv] > dist[u][iu] + dv) {
                        dist[v][iv] = dist[u][iu] + dv;
                        priorityQueue.add(new Edge(v, dist[v][iv], iv));
                    }
                }
            }

            for (int i = 0; i < 26; i++) {
                len = Math.min(len, dist[dId][i]);
            }
            return len;
        }

    }
}