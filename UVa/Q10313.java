import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Q10313 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        long[][] dp2 = new long[301][1002];
        long[] dp1 = new long[301];
        Arrays.fill(dp1, 0);
        dp1[0] = 1;
        for(int i = 1; i <= 300; i++)
            for(int j = i; j <= 300; j++)
                dp1[j] += dp1[j - i];

        for(int i = 0; i <= 300; i++)
            Arrays.fill(dp2[i], 0);

        dp2[0][0] = 1;

        for(int j = 1; j <= 300; j++)
            for(int k = j; k <= 300; k++)
                for(int i = 1; i <= 1001; i++)
                    dp2[k][i] += dp2[k - j][i - 1];

        for(int i = 0; i <= 300; i++)
            for(int j = 1; j <= 1001; j++)
                dp2[i][j] += dp2[i][j-1];

        while((line = reader.readLine()) != null && line.length() > 0)
        {
            String[] vals = line.split(" ");
            if(vals.length == 1)
                System.out.println(dp1[Integer.parseInt(vals[0])]);
            else if(vals.length == 2) {
                int N = Integer.parseInt(vals[0]);
                int L1 = Integer.parseInt(vals[1]);
                System.out.println(dp2[N][L1]);
            }
            else {
                int N = Integer.parseInt(vals[0]);
                int L1 = Integer.parseInt(vals[1]);
                int L2 = Integer.parseInt(vals[2]);
                if(L1 == 0)
                    System.out.println(dp2[N][L2]);
                else
                    System.out.println(dp2[N][L2] - dp2[N][L1 - 1]);
            }
        }
    }
}