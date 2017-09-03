import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q166 {
    private static final int[] coins = {0, 5, 10, 20, 50, 100, 200};
    private static int[] many = new int[7];
    private static long[][] dp = new long[1000000][7];
    private static long[] dp2 = new long[1000000];
    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer;
        int toPay;
        int maxAmt;
        while (true)
        {
            tokenizer = new StringTokenizer(reader.readLine());
            boolean allZero = true;
            for (int i = 1; i <= 6; i++) {
                many[i] = Integer.parseInt(tokenizer.nextToken());
                if(many[i] != 0)
                    allZero = false;
            }
            if(allZero)
                break;
            maxAmt = 0;
            for (int i = 1; i <= 6; i++) {
                maxAmt += coins[i] * many[i];
            }
            tokenizer = new StringTokenizer(tokenizer.nextToken(), ".");
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            toPay = a * 100 + b;

            for (int i = 0; i < maxAmt + 10; i++) {
                Arrays.fill(dp[i], Integer.MAX_VALUE);
            }
            for (int i = 0; i <= 6; i++) {
                dp[0][i] = 0;
            }
            Arrays.fill(dp2, 0, maxAmt + 10, Integer.MAX_VALUE);

            dp2[0] = 0;
            for (int i = 1; i <= 6; i++) {
                for (int j = coins[i]; j <= maxAmt; j++) {
                    dp2[j] = Math.min(dp2[j], 1 + dp2[j - coins[i]]);
                }
            }

            for (int i = 1; i <= 6; i++) {
                for (int j = 0; j <= many[i]; j++) {
                    for (int k = j * coins[i]; k <= maxAmt; k++) {
                        dp[k][i] = Math.min(dp[k][i], j + dp[k - j * coins[i]][i - 1]);
                    }
                }
            }

            long ans = 1000000L;
            for(int i = toPay; i <= maxAmt; i++)
                ans = Math.min(ans, dp[i][6] + dp2[i - toPay]);

            System.out.println(String.format("%1$3s", ans));
        }
    }
}