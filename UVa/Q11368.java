import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q11368 {
    private static class Doll implements Comparable<Doll> {
        int height;
        int width;

        @Override
        /**
         * Sort in decreasing order of width, for same width, sort in increasing order of height.
         */
        public int compareTo(Doll o) {
            if(width == o.width)
                return height - o.height;
            return o.width - width;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine());
        Doll[] dolls = new Doll[20050];
        int[] LDS = new int[20050];
        for (int i = 0; i < T; i++) {
            int m = Integer.parseInt(reader.readLine());
            int k = 0;
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            while(tokenizer.hasMoreTokens()) {
                int w = Integer.parseInt(tokenizer.nextToken());
                int h = Integer.parseInt(tokenizer.nextToken());
                Doll d = new Doll();
                d.width = w;
                d.height = h;
                dolls[k++] = d;
            }
            Arrays.sort(dolls, 0, m);
            int size = 1;
            LDS[0] = dolls[0].height;
            for (int j = 1; j < m; j++) {
                int pos = firstElementGreaterThan(LDS, 0, size-1, dolls[j].height);
                if(LDS[pos] <= dolls[j].height) {
                    LDS[size] = dolls[j].height;
                    size++;
                } else {
                    LDS[pos] = dolls[j].height;
                }
            }
            System.out.println(size);
        }

    }

    private static int firstElementGreaterThan(int[] arr, int low, int high, int key) {
        // At convergence, low and high become equal.
        while(low < high) {
            int mid = (low + high)  >> 1;
            if(arr[mid] <= key)
                low = mid + 1;
            else
                high = mid;
        }
        return low;
    }
}