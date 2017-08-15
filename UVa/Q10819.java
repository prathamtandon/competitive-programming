import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Q10819 {
    private static int[] p = new int[110];
    private static int[] f = new int[110];
    private static int[][] dp = new int[110][15000];
    private static int budget;
    private static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line = reader.readLine()) != null && line.length() > 0) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            budget = Integer.parseInt(tokenizer.nextToken());
            N = Integer.parseInt(tokenizer.nextToken());
            for (int i = 0; i < N; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                p[i] = Integer.parseInt(tokenizer.nextToken());
                f[i] = Integer.parseInt(tokenizer.nextToken());
            }
            for (int i = 0; i < 110; i++) {
                for (int j = 0; j < 15000; j++) {
                    dp[i][j] = -1;
                }
            }
            System.out.println(solve(0, 0));
        }
    }

    private static int solve(int id, int spending) {
        if(spending > budget && budget < 1800)
            return -1000;
        if(spending > budget + 200)
            return -1000;
        if(id == N) {
            if(spending > budget && spending <= 2000)
                return -1000;
            return 0;
        }
        if(dp[id][spending] != -1)
            return dp[id][spending];

        return dp[id][spending] = Math.max(solve(id + 1, spending), f[id] + solve(id+1, spending + p[id]));
    }
}