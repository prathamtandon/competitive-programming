import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q10616 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int[] numbers = new int[201];
        // dp[i][j][k] denotes number of groups formed by selecting j items from i items having sum k%D.
        // Final result is stored in dp[N][M][0]
        int[][][] dp = new int[201][11][21];
        int test = 1;
        while(true) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int N = Integer.parseInt(tokenizer.nextToken());
            int Q = Integer.parseInt(tokenizer.nextToken());
            if(N == 0 && Q == 0)
                break;
            for (int i = 0; i < N; i++) {
                numbers[i] = Integer.parseInt(reader.readLine());
            }
            System.out.println("SET " + test++ + ":");
            for (int i = 0; i < Q; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int D = Integer.parseInt(tokenizer.nextToken());
                int M = Integer.parseInt(tokenizer.nextToken());
                for (int j = 0; j < 201; j++) {
                    for (int k = 0; k < 11  ; k++) {
                        Arrays.fill(dp[j][k], -1);
                    }
                }
                System.out.println("QUERY " + (i + 1) + ": " + solve(numbers, 0, 0, M, D, dp, N));
            }
        }
    }

    private static int solve(int[] numbers, int id, int sum, int remaining, int divisor, int[][][] dp, int N) {
        if(remaining == 0) {
            if(sum == 0) return 1;
            return 0;
        }
        if(id == N)
            return 0;
        if(dp[id][remaining][sum] != -1)
            return dp[id][remaining][sum];
        int x = (sum + numbers[id])% divisor;
        if(x < 0)
            x+=divisor;
        int numWays = solve(numbers, id+1, sum, remaining, divisor, dp, N) +
                solve(numbers, id+1, x, remaining-1, divisor, dp, N);
        dp[id][remaining][sum] = numWays;
        return numWays;
    }
}