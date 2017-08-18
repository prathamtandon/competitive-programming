import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q11003 {
    private static int N;
    private static int[] dp = new int[3000];
    private static int[] weights = new int[1010];
    private static int[] loads = new int[1010];
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while ((N = Integer.parseInt(reader.readLine())) != 0) {
            for (int i = 0; i < N; i++) {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
                weights[i] = Integer.parseInt(tokenizer.nextToken());
                loads[i] = Integer.parseInt(tokenizer.nextToken());
            }
            Arrays.fill(dp, 0);
            int best = 1;
            for (int i = N - 1; i >= 0; i--) {
                for (int j = loads[i]; j >= 0; j--) {
                    if(dp[j] > 0 && dp[j] + 1 > dp[weights[i] + j]) {
                        dp[weights[i] + j] = dp[j] + 1;
                        best = Math.max(best, dp[j] + 1);
                    }
                }
                if(dp[weights[i]] == 0)
                    dp[weights[i]] = 1;
            }
            System.out.println(best);
        }
    }
}