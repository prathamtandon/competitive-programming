import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.StringTokenizer;

class Uva11284 {
    private static int numStores;
    private static long[][] dist = new long[55][55];
    private static long[][] dp = new long[13][8192];
    private static long[] tempSavings = new long[55];
    private static long[] savings = new long[55];
    private static int[] storeId = new int[55];
    private static int done;
    private static int numToBuy;
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static final long INF = 0xfffffffffL;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(reader.readLine());
        StringTokenizer tokenizer;
        for (int i = 0; i < tc; i++) {
            reader.readLine();
            tokenizer = new StringTokenizer(reader.readLine());
            numStores = Integer.parseInt(tokenizer.nextToken());
            initDataStructures();
            int numRoads = Integer.parseInt(tokenizer.nextToken());
            for (int j = 0; j < numRoads; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int x = Integer.parseInt(tokenizer.nextToken());
                int y = Integer.parseInt(tokenizer.nextToken());
                tokenizer = new StringTokenizer(tokenizer.nextToken(), ".");
                int a = Integer.parseInt(tokenizer.nextToken());
                int b = Integer.parseInt(tokenizer.nextToken());
                dist[x][y] = Math.min(dist[x][y], 100 * a + b);
                dist[y][x] = Math.min(dist[y][x], 100 * a + b);
            }
            numToBuy = Integer.parseInt(reader.readLine());
            for (int j = 1; j <= numToBuy; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int id = Integer.parseInt(tokenizer.nextToken());
                tokenizer = new StringTokenizer(tokenizer.nextToken(), ".");
                int a = Integer.parseInt(tokenizer.nextToken());
                int b = Integer.parseInt(tokenizer.nextToken());
                tempSavings[id] += 100 * a + b;
            }
            int temp = 0;
            storeId[0] = 0;
            for (int j = 1; j <= numStores; j++) {
                if(tempSavings[j] > 0)
                {
                    temp++;
                    storeId[temp] = j;
                    savings[temp] = tempSavings[j];
                }
            }
            numToBuy = temp;
            floydWarshall();
            initDp();
            done = (int)Math.pow(2.0, numToBuy + 1) - 1;
            long ans = solve(0, 1);
            if(ans > 0)
                System.out.println("Daniel can save $" + df.format(ans / 100.0));
            else
                System.out.println("Don't leave the house");
        }
    }

    private static void initDataStructures() {
        for (int i = 0; i <= numStores; i++) {
            for (int j = 0; j <= numStores; j++) {
                dist[i][j] = INF;
            }
        }
        Arrays.fill(tempSavings, 0);
    }
    
    private static void initDp()
    {
        for (int i = 0; i < 13; i++) {
            Arrays.fill(dp[i], -INF);
        }
    }

    private static void floydWarshall()
    {
        for (int k = 0; k <= numStores; k++) {
            for (int i = 0; i <= numStores; i++) {
                for (int j = 0; j <= numStores; j++) {
                    if(dist[i][j] > dist[i][k] + dist[k][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
    }

    private static long solve(int id, int mask)
    {
        int from = storeId[id];
        if(mask == done) return -dist[from][0];
        if(dp[id][mask] != -INF) return dp[id][mask];
        long ans = -INF;
        for (int next = 1; next <= numToBuy; next++) {
            int to = storeId[next];
            if((mask & (1 << next)) == 0)
            {
                long ans1 = solve(id, mask | (1 << next));
                long ans2 = savings[next] - dist[from][to] + solve(next, mask | (1 << next));
                ans = Math.max(ans, Math.max(ans1, ans2));
            }
        }
        return dp[id][mask] = ans;
    }
}