import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.StringTokenizer;

class Uva216 {
    private static int[] xcords = new int[9];
    private static int[] ycords = new int[9];
    private static double[][] dist = new double[9][9];
    private static double[][] dp = new double[9][512];
    private static int[][] next = new int[9][512];
    private static int best_s;
    private static double best_dist;
    private static int n;
    private static int tc = 0;
    private static DecimalFormat dc = new DecimalFormat("0.00");
    private static int done;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer;
        while((n = Integer.parseInt(reader.readLine())) != 0)
        {
            tc++;
            for (int i = 0; i < n; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                xcords[i] = Integer.parseInt(tokenizer.nextToken());
                ycords[i] = Integer.parseInt(tokenizer.nextToken());
            }
            computeDistanceMatrix();
            int mask = 0;
            done = (int)Math.pow(2, n) - 1;
            best_dist = Double.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                Arrays.fill(dp[i], -1);
                Arrays.fill(next[i], -1);
            }
            for (int i = 0; i < n; i++) {
                double ans = solve(i, mask | (1 << i));
                if(best_dist > ans)
                {
                    best_dist = ans;
                    best_s = i;
                }
            }
            printSolution();
        }
    }

    private static void computeDistanceMatrix()
    {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double d = Math.sqrt(Math.pow(xcords[i] - xcords[j], 2.0) + Math.pow(ycords[i] - ycords[j], 2.0));
                dist[i][j] = d;
                dist[j][i] = d;
            }
        }
    }

    private static void printSolution()
    {
        System.out.println("**********************************************************");
        System.out.println("Network #" + tc);
        int mask = 1 << best_s;
        int next_s;
        while(mask != done)
        {
            next_s = next[best_s][mask];
            System.out.println("Cable requirement to connect (" + xcords[best_s] + "," + ycords[best_s] + ") to (" +
                    xcords[next_s] + "," + ycords[next_s] + ") is " + dc.format
                    (dist[best_s][next_s] + 16.0) + " feet.");
            mask |= (1 << next_s);
            best_s = next_s;
        }
        System.out.println("Number of feet of cable required is " + dc.format(best_dist + (n - 1) * 16.0) + ".");
    }

    private static double solve(int id, int mask)
    {
        if(mask == done) return 0;
        if(dp[id][mask] > -1) return dp[id][mask];
        double ans = Double.MAX_VALUE;
        int next_id = -1;
        for (int i = 0; i < n; i++) {
            if(i != id && (mask & (1 << i)) == 0)
            {
                double t = dist[id][i] + solve(i, mask | (1 << i));
                if(ans > t)
                {
                    next_id = i;
                    ans = t;
                }
            }
        }
        next[id][mask] = next_id;
        return dp[id][mask] = ans;
    }
}