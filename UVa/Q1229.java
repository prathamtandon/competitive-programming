import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Q1229 {
    private static int[] dfs_low = new int[110];
    private static int[] dfs_num = new int[110];
    private static boolean[] visited = new boolean[110];
    private static boolean[] visited2 = new boolean[110];
    private static ArrayList<Integer>[] adjList = new ArrayList[110];
    private static ArrayList<Integer>[] scc = new ArrayList[110];
    private static int sccId = 0;
    private static int time = 1;
    private static Stack<Integer> S = new Stack<>();
    private static ArrayList<String> words = new ArrayList<>();
    private static HashMap<Integer, String> rMap = new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer;
        while(true) {
            int n = Integer.parseInt(reader.readLine().trim());
            if(n == 0)
                break;
            Arrays.fill(dfs_num, 0);
            Arrays.fill(dfs_low, 0);
            Arrays.fill(visited, false);
            Arrays.fill(visited2, false);
            for (int i = 0; i < 110; i++) {
                adjList[i] = new ArrayList<>();
                scc[i] = new ArrayList<>();
            }
            S.clear();
            rMap.clear();
            words.clear();
            sccId = 0;
            time = 1;
            int id = 0;
            HashMap<String, Integer> map = new HashMap<>();
            for (int i = 0; i < n; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                String from = tokenizer.nextToken();
                if(!map.containsKey(from)) {
                    map.put(from, id);
                    rMap.put(id++, from);
                }
                int u = map.get(from);
                while (tokenizer.hasMoreTokens()) {
                    String to = tokenizer.nextToken();
                    if(!map.containsKey(to)) {
                        map.put(to, id);
                        rMap.put(id++, to);
                    }
                    int v = map.get(to);
                    adjList[u].add(v);
                }
            }

            for(int u = 0; u < n; u++) {
                if(dfs_num[u] == 0)
                    tarjanSCC(u);
            }
            for (int i = 0; i < sccId; i++) {
                if(scc[i].size() > 1)
                    dfs(scc[i].get(0));
            }
            Collections.sort(words);
            System.out.println(words.size());
            if(words.size() > 1)
                System.out.print(words.get(0));
            for (int i = 1; i < words.size(); i++) {
                System.out.print(" " + words.get(i));
            }
            System.out.println();
        }
    }

    private static void tarjanSCC(int u) {
        dfs_low[u] = dfs_num[u] = time++;
        visited[u] = true;
        S.add(u);
        for(int v: adjList[u]) {
            if(dfs_num[v] == 0)
                tarjanSCC(v);
            if(visited[v])
                dfs_low[u] = Math.min(dfs_low[u], dfs_low[v]);
        }
        if(dfs_low[u] == dfs_num[u]) {
            while (true) {
                int v = S.pop();
                visited[v] = false;
                scc[sccId].add(v);
                if(u == v) break;
            }
            sccId++;
        }
    }

    private static void dfs(int u){
        visited2[u] = true;
        words.add(rMap.get(u));
        for(int v: adjList[u])
            if(!visited2[v])
                dfs(v);
    }
}