import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q147 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer;
        long[] dp = new long[30005];
        int[] denoms = {5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000};
        DecimalFormat df = new DecimalFormat("0.00");
        while(true) {
            tokenizer = new StringTokenizer(reader.readLine(), ".");
            int dollars = Integer.parseInt(tokenizer.nextToken());
            int cents = Integer.parseInt(tokenizer.nextToken());
            int total = dollars * 100 + cents;
            if(total == 0)
                break;
            Arrays.fill(dp, 0);
            dp[0] = 1;
            for(int denom : denoms) {
                for(int i = denom; i <= total; i++)
                    dp[i] += dp[i - denom];
            }
            double temp = (double) total / 100;
            System.out.print(String.format("%1$6s", df.format(temp)));
            System.out.println(String.format("%1$17s", dp[total]));
        }
    }
}