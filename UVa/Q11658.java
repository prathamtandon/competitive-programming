import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q11658 {
    private static int[][] dp = new int[5500][105];
    private static int[] parts = new int[105];
    private static int n;
    private static int x;
    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line);
            n = Integer.parseInt(tokenizer.nextToken());
            x = Integer.parseInt(tokenizer.nextToken());
            if(n == 0 && x == 0)
                break;
            for (int i = 0; i < 5500; i++) {
                Arrays.fill(dp[i], -1);
            }
            for (int i = 1; i <= n; i++) {
                tokenizer = new StringTokenizer(reader.readLine(), ".");
                int a = Integer.parseInt(tokenizer.nextToken());
                int b = Integer.parseInt(tokenizer.nextToken());
                parts[i] = a * 100 + b;
            }
            System.out.println(roundUp((double)parts[x] / solve(parts[x], 1)));
        }
    }

    private static int solve(int soFar, int id) {
        if(id > n && soFar <= 5000)
            return 1000000;
        if(soFar > 5000)
            return soFar;
        if(dp[soFar][id] > -1)
            return dp[soFar][id];
        int ans1 = solve(soFar, id + 1);
        if(id != x) {
            int ans2 = solve(soFar + parts[id], id + 1);
            return dp[soFar][id] = Math.min(ans1, ans2);
        }
        return dp[soFar][id] = ans1;
    }

    private static String roundUp(double ans) {
        ans *= 100;
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(ans);
    }
}
