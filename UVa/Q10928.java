import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Q10928 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int cases = Integer.parseInt(reader.readLine().trim());
        Graph graph;
        while(cases-- > 0) {
            int numPlaces = Integer.parseInt(reader.readLine().trim());
            graph = new Graph(numPlaces);
            int i = 0;
            while(++i <= numPlaces) {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
                while(tokenizer.hasMoreTokens()) {
                    graph.addEdge(i, Integer.parseInt(tokenizer.nextToken()));
                }
            }
            graph.printNeighbours();
            if(cases > 0)
                reader.readLine();
        }
    }

    private static class Graph {
        private int V;
        private ArrayList<Integer>[] adj;

        Graph(int V) {
            this.V = V;
            this.adj = new ArrayList[V+1];
            for (int i = 1; i < V+1; i++) {
                this.adj[i] = new ArrayList<>();
            }
        }

        void addEdge(int from, int to) {
            this.adj[from].add(to);
        }

        void printNeighbours() {
            int minNeighbours = getMinNeighbours();
            ArrayList<String> result = new ArrayList<>();
            for (int i = 1; i < V + 1; i++) {
                if(adj[i].size() == minNeighbours)
                    result.add(String.valueOf(i));
            }
            System.out.println(String.join(" ", result));
        }

        private int getMinNeighbours() {
            int min = Integer.MAX_VALUE;
            for (int i = 1; i < V + 1; i++) {
                min = Math.min(min, adj[i].size());
            }
            return min;
        }
    }
}