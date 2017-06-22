import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q10382 {

    private static class Interval implements Comparable<Interval> {
        double left;
        double right;

        Interval(double left, double right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Interval o) {
            return new Double(left).compareTo(o.left);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        Interval[] intervals = new Interval[10005];
        while((line = reader.readLine()) != null && line.length() != 0) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            int totalSprinklers = Integer.parseInt(tokenizer.nextToken());
            long length = Long.parseLong(tokenizer.nextToken());
            long width = Long.parseLong(tokenizer.nextToken());
            int k = 0;
            for (int i = 0; i < totalSprinklers; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                long position = Long.parseLong(tokenizer.nextToken());
                long radius = Long.parseLong(tokenizer.nextToken());
                if(radius > width / 2.0) {
                    double left = position - Math.sqrt(radius * radius - width * width / 4.0);
                    double right = position + Math.sqrt(radius * radius - width * width / 4.0);
                    intervals[k++] = new Interval(left, right);
                }
            }
            totalSprinklers = k;
            if(totalSprinklers == 0) {
                System.out.println(-1);
                continue;
            }
            Arrays.sort(intervals, 0, totalSprinklers);

            double totalSpan = 0.0, curMax = 0.0, nextMax = 0.0;
            int result = 0;
            int i = 0;
            while (totalSpan < length) {
                int farthest = -1;
                for(;i < totalSprinklers; i++) {
                    if(intervals[i].left > curMax)
                        break;
                    if(intervals[i].right >= nextMax) {
                        nextMax = intervals[i].right;
                        farthest = i;
                    }
                }
                if(farthest == -1)
                    break;
                result++;
                totalSpan = nextMax;
                curMax = nextMax;
            }

            if(totalSpan < length)
                System.out.println(-1);
            else
                System.out.println(result);
        }
    }
}