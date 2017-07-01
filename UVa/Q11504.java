import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

class Q11504 {
    private static int[] dfs_low = new int[100005];
    private static int[] dfs_num = new int[100005];
    private static int[] scc = new int[100005];
    private static int[] indegree = new int[100005];
    private static ArrayList<Integer>[] adjList = new ArrayList[100005];
    private static boolean[] visited = new boolean[100005];
    private static Stack<Integer> S = new Stack<>();
    private static int time;
    private static int sccId;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(reader.readLine().trim());
        StringTokenizer tokenizer;
        for (int i = 0; i < tc; i++) {
            clear();
            tokenizer = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(tokenizer.nextToken());
            int m = Integer.parseInt(tokenizer.nextToken());
            for (int j = 0; j < m; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int u = Integer.parseInt(tokenizer.nextToken());
                int v = Integer.parseInt(tokenizer.nextToken());
                adjList[u - 1].add(v - 1);
            }
            for(int u = 0; u < n; u++)
                if(dfs_num[u] == 0)
                    tarjanSCC(u);

            for (int u = 0; u < n; u++) {
                for(int v: adjList[u])
                    if(scc[u] != scc[v])
                        indegree[scc[v]]++;
            }

            int ans = 0;
            for (int j = 0; j < sccId; j++) {
                if(indegree[j] == 0)
                    ans++;
            }

            System.out.println(ans);
        }
    }

    private static void tarjanSCC(int u) {
        dfs_low[u] = dfs_num[u] = time++;
        S.add(u);
        visited[u] = true;

        for(int v: adjList[u]) {
            if(dfs_num[v] == 0)
                tarjanSCC(v);
            if(visited[v])
                dfs_low[u] = Math.min(dfs_low[u], dfs_low[v]);
        }

        if(dfs_low[u] == dfs_num[u]) {
            while(true) {
                int v = S.pop();
                visited[v] = false;
                scc[v] = sccId;
                if(u == v) break;
            }
            sccId++;
        }
    }

    private static void clear() {
        Arrays.fill(dfs_low, 0);
        Arrays.fill(dfs_num, 0);
        Arrays.fill(scc, 0);
        Arrays.fill(indegree, 0);
        Arrays.fill(visited, false);
        S.clear();
        for (int i = 0; i < 100005; i++) {
            adjList[i] = new ArrayList<>();
        }
        time = 1;
        sccId = 0;
    }
}