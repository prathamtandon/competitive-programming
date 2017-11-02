import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Q10911 {
    private static int N, target;
    private static double[][] dist = new double[20][20];
    private static double[] memo = new double[1 << 16];
    public static void main(String[] args) throws IOException {
        int[] x = new int[20];
        int[] y = new int[20];
        int caseNo = 1;
        StringTokenizer tokenizer;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true)
        {
            N = Integer.parseInt(reader.readLine());
            if(N == 0)
                break;
            for (int i = 0; i < 2 * N; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                tokenizer.nextToken();
                x[i] = Integer.parseInt(tokenizer.nextToken());
                y[i] = Integer.parseInt(tokenizer.nextToken());
            }
            for (int i = 0; i < 2 * N - 1; i++) {
                for (int j = i + 1; j < 2 * N; j++) {
                    dist[i][j] = dist[j][i] = Math.sqrt((x[i] - x[j]) * (x[i] - x[j])
                            + (y[i] - y[j]) * (y[i] - y[j]));
                }
            }
            Arrays.fill(memo, -1.0);
            target = (1 << 2 * N) - 1;
            System.out.println(String.format("Case %d: %.2f", caseNo++, matching(0)));
        }
    }

    private static double matching(int bitmask)
    {
        if(memo[bitmask] > -0.5)
            return memo[bitmask];

        if(bitmask == target)
            return memo[bitmask] = 0;

        double ans = 2000000000.0;
        int p1, p2;

        for(p1 = 0; p1 < 2 * N; p1++)
            if((bitmask & (1 << p1)) == 0)
                break;
        for(p2 = p1 + 1; p2 < 2 * N; p2++)
            if((bitmask & (1 << p2)) == 0)
                ans = Math.min(ans, dist[p1][p2] + matching(bitmask | (1 << p1) | (1 << p2)));

        return memo[bitmask] = ans;
    }
}
