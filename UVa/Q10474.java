import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q10474 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer;
        int[] count = new int[10001];
        int[] position = new int[10001];
        int testCase = 1;
        while(true) {
            String line;
            while((line = reader.readLine()) == null || line.length() == 0);
            tokenizer = new StringTokenizer(line);
            int N = Integer.parseInt(tokenizer.nextToken());
            int Q = Integer.parseInt(tokenizer.nextToken());
            if(N == 0 && Q == 0)
                break;
            int[] arr = new int[N];
            int[] queries = new int[Q];
            for (int i = 0; i < N; i++) {
                while((line = reader.readLine()) == null || line.length() == 0);
                arr[i] = Integer.parseInt(line.trim());
            }
            for (int i = 0; i < Q; i++) {
                while((line = reader.readLine()) == null || line.length() == 0);
                queries[i] = Integer.parseInt(line.trim());
            }

            Arrays.fill(count, 0);
            Arrays.fill(position, -1);

            for (int i = 0; i < N; i++) {
                count[arr[i]]++;
            }

            int pos = 1;
            for (int i = 0; i < count.length; i++) {
                if(count[i] > 0) {
                    position[i] = pos;
                    pos += count[i];
                }
            }
            System.out.println("CASE# " + testCase + ":");
            for (int i = 0; i < Q; i++) {
                if(position[queries[i]] == -1)
                    System.out.println(queries[i] + " not found");
                else
                    System.out.println(queries[i] + " found at " + position[queries[i]]);
            }
            testCase++;
        }
    }
}