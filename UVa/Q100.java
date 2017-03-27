import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Q100 {
    public static void main(String[] args) throws IOException {
        UVa100 object = new UVa100();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line = reader.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());
            System.out.println(a + " " + b + " " + object.maximumCylceLength(a, b));
        }
    }

    private static class UVa100 {
        private final long MEMO_SIZE = 1 << 16;
        private final long[] memo = new long[(int)MEMO_SIZE];
        long cycleLength(long number) {
            long n = number;
            long count = 1;
            while(n > 1) {
                if(((n-1) >> 1) == (n >> 1)) {
                    n = 3 * n + 1;
                } else {
                    n >>= 1;
                }
                if(n < MEMO_SIZE && memo[(int)n] != 0) {
                    return count + memo[(int)n];
                }
                count++;
            }
            return count;
        }
        long maximumCylceLength(long from, long to) {
            final long small = Math.min(from, to);
            final long big = Math.max(from, to);
            long result = 0;
            for (long i = small; i <= big ; i++) {
                long count = cycleLength(i);
                if(i < MEMO_SIZE) {
                    memo[(int)i] = count;
                }
                result = Math.max(result, count);
            }
            return result;
        }
    }
}
