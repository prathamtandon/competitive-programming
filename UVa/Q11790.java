import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Q11790 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine());
        int[] heights = new int[10000];
        int[] widths = new int[10000];
        int[] LIS = new int[10000];
        int[] LDS = new int[10000];
        StringTokenizer tokenizer;
        for (int i = 0; i < T; i++) {
            int N = Integer.parseInt(reader.readLine());
            int k = 0;
            tokenizer = new StringTokenizer(reader.readLine());
            while(tokenizer.hasMoreTokens())
                heights[k++] = Integer.parseInt(tokenizer.nextToken());
            k = 0;
            tokenizer = new StringTokenizer(reader.readLine());
            int maxLIS = 0, maxLDS = 0;
            while(tokenizer.hasMoreTokens()) {
                int w = Integer.parseInt(tokenizer.nextToken());
                widths[k] = w;
                LIS[k] = w;
                LDS[k++] = w;
                maxLIS = Math.max(maxLIS, w);
                maxLDS = Math.max(maxLDS, w);
            }
            for (int j = 1; j < N; j++) {
                for (int l = 0; l < j ; l++) {
                    if(heights[l] < heights[j] && LIS[l] + widths[j] > LIS[j]) {
                        LIS[j] = LIS[l] + widths[j];
                        maxLIS = Math.max(maxLIS, LIS[j]);
                    }
                    if(heights[l] > heights[j] && LDS[l] + widths[j] > LDS[j]) {
                        LDS[j] = LDS[l] + widths[j];
                        maxLDS = Math.max(maxLDS, LDS[j]);
                    }
                }
            }
            if(maxLDS > maxLIS)
                System.out.println("Case " + (i+1) + ". Decreasing (" + maxLDS + "). Increasing (" + maxLIS + ").");
            else
                System.out.println("Case " + (i+1) + ". Increasing (" + maxLIS + "). Decreasing (" + maxLDS + ").");
        }
    }
}