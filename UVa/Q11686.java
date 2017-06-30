import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Q11686 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        StringTokenizer tokenizer;
        ArrayList<Integer>[] adjList = new ArrayList[1000005];
        int[] indegree = new int[1000005];
        while(true) {
            line = reader.readLine().trim();
            if(line.length() == 0)
                continue;
            tokenizer = new StringTokenizer(line);
            int n = Integer.parseInt(tokenizer.nextToken());
            int m = Integer.parseInt(tokenizer.nextToken());
            if(n == 0 && m == 0)
                break;
            Arrays.fill(indegree, 0);
            for (int i = 0; i < 1000005; i++) {
                adjList[i] = new ArrayList<>();
            }
            for (int i = 0; i < m; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int u = Integer.parseInt(tokenizer.nextToken());
                int v = Integer.parseInt(tokenizer.nextToken());
                adjList[u].add(v);
            }
            for (int u = 1; u <= n; u++) {
                for(int v: adjList[u])
                    indegree[v] += 1;
            }
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for(int u = 1; u <= n; u++) {
                if(indegree[u] == 0)
                    pq.add(u);
            }
            ArrayList<Integer> result = new ArrayList<>();
            while(!pq.isEmpty()) {
                int u = pq.remove();
                result.add(u);
                for(int v: adjList[u]) {
                    indegree[v] -= 1;
                    if(indegree[v] == 0)
                        pq.add(v);
                }
            }
            if(result.size() < n)
                System.out.println("IMPOSSIBLE");
            else {
                for(int u: result)
                    System.out.println(u);
            }
        }
    }
}