import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Q12032 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine().trim());
        int[] steps = new int[100002];
        steps[0] = 0;
        for (int i = 0; i < T; i++) {
            int N = Integer.parseInt(reader.readLine().trim());
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 1; j <= N; j++) {
                steps[j] = Integer.parseInt(tokenizer.nextToken());
            }

            int lo = 1, hi = 10000000;
            int ans = -1;
            for (int j = 0; j < 100; j++) {
                if(lo > hi) break;
                int k = (lo + hi) / 2;
                if(!isEnoughStrength(steps, N, k))
                    lo = k + 1;
                else {
                    ans = k;
                    hi = k - 1;
                }
            }
            System.out.println("Case " + (i + 1) + ": " + ans);
        }
    }

    private static boolean isEnoughStrength(int[] steps, int N, int k) {
        for (int i = 0; i < N; i++) {
            if(steps[i+1] - steps[i] > k) {
                return false;
            } else if(steps[i+1] - steps[i] == k) {
                k -= 1;
            }
        }

        return true;
    }
}