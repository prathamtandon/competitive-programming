import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Q11517 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine());
        int[] coins = new int[102];
        int[][] dp = new int[20000][102];
        for (int i = 0; i < T; i++) {
            int p = Integer.parseInt(reader.readLine());
            int n = Integer.parseInt(reader.readLine());
            for (int j = 1; j <= n; j++) {
                coins[j] = Integer.parseInt(reader.readLine());
            }
            for (int j = 1; j < 20000; j++) {
                Arrays.fill(dp[j], 100000);
            }
            Arrays.fill(dp[0], 0);

            for (int k = 1; k < 20000; k++) {
                for (int j = 1; j <= n; j++) {
                    dp[k][j] = dp[k][j - 1];
                    if(k >= coins[j])
                        dp[k][j] = Math.min(dp[k][j], 1 + dp[k - coins[j]][j - 1]);
                }
            }
            while(dp[p][n] == 100000) {
                p++;
            }
            System.out.println(p + " " + dp[p][n]);
        }
    }
}
