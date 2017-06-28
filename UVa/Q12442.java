import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

class Q12442 {
    private static int size = 0;
    private static int best = 0;
    private static boolean[] visited = new boolean[50005];
    private static int[] dfs_num = new int[50005];
    private static int[] dfs_low = new int[50005];
    private static int[] adjList = new int[50005];
    private static int[] sizes = new int[50005];
    private static int[] scc = new int[50005];
    private static int[] dp = new int[50005];
    private static int sccId = 0;
    private static int time = 0;
    private static Stack<Integer> S;
    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine().trim());
        StringTokenizer tokenizer;
        for (int i = 0; i < T; i++) {
            Arrays.fill(visited, false);
            Arrays.fill(adjList, 0);
            Arrays.fill(sizes, 0);
            Arrays.fill(dfs_low, 0);
            Arrays.fill(dfs_num, 0);
            Arrays.fill(scc, 0);
            Arrays.fill(dp, -1);
            S = new Stack<>();
            size = 0;
            best = 0;
            time = 1;
            sccId = 0;
            String line = reader.readLine().trim();
            if(line.length() == 0)
                continue;
            int N = Integer.parseInt(line);
            for (int j = 0; j < N; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int u = Integer.parseInt(tokenizer.nextToken());
                int v = Integer.parseInt(tokenizer.nextToken());
                adjList[u] = v;
            }
            for (int u = 1; u <= N; u++) {
                if(dfs_num[u] == 0) {
                    tarjanSCC(u);
                }
            }
            for (int u = 1; u <= N; u++) {
                int tmp = dfs(u) + sizes[scc[u]];
                if(size < tmp) {
                    size = tmp;
                    best = u;
                }
            }
            System.out.println("Case " + (i + 1) + ": " + best);
        }
    }

    private static void tarjanSCC(int u) {
        visited[u] = true;
        dfs_low[u] = dfs_num[u] = time;
        time++;
        S.add(u);
        int v = adjList[u];
        if(dfs_num[v] == 0)
            tarjanSCC(v);
        if(visited[v])
            dfs_low[u] = Math.min(dfs_low[u], dfs_low[v]);
        if(dfs_num[u] == dfs_low[u]) {
            sccId++;
            while(true) {
                int i = S.pop();
                visited[i] = false;
                sizes[sccId]++;
                scc[i] = sccId;
                if (i == u)
                    break;
            }
        }
    }

    private static int dfs(int u) {
        if(dp[u] > -1)
            return dp[u];
        int result = 0;
        int v = adjList[u];
        if(scc[u] != scc[v])
            result = dfs(v) + sizes[scc[v]];
        dp[u] = result;
        return result;
    }
}