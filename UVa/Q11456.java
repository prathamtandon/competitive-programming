import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Q11456 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine());
        int[] LIS = new int[2500];
        int[] LDS = new int[2500];
        int[] x = new int[2500];
        for (int i = 0; i < T; i++) {
            int n = Integer.parseInt(reader.readLine());
            for (int j = 0; j < n; j++) {
                x[j] = Integer.parseInt(reader.readLine());
            }

            for (int j = n-1; j >= 0; j--) {
                LIS[j] = LDS[j] = 1;
                for (int k = j+1; k < n; k++) {
                    if(x[j] < x[k] && LIS[j] < 1 + LIS[k])
                        LIS[j] = 1 + LIS[k];
                    if(x[j] > x[k] && LDS[j] < 1 + LDS[k])
                        LDS[j] = 1 + LDS[k];
                }
            }

            int ans = 0;
            for (int j = 0; j < n; j++) {
                ans = Math.max(ans, LIS[j] + LDS[j] - 1);
            }
            System.out.println(ans);
        }
    }
}