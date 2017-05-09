import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q11262 {

    private static class Point {
        int X;
        int Y;

        Point(int X, int Y) {
            this.X = X;
            this.Y = Y;
        }

        double cartesianDistance(Point other) {
            return Math.sqrt(Math.pow((this.X - other.X), 2.0) + Math.pow((this.Y - other.Y), 2.0));
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(reader.readLine());
        for (int i = 0; i < TC; i++) {
            reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int P = Integer.parseInt(tokenizer.nextToken());
            int k = Integer.parseInt(tokenizer.nextToken());
            Point[] red = new Point[105];
            Point[] blue = new Point[105];
            int r = 0, b = 0;
            for (int j = 0; j < P; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                Point p = new Point(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()));
                if(tokenizer.nextToken().equals("red"))
                    red[r++] = p;
                else
                    blue[b++] = p;
            }
            int[][] dist = new int[r][b];
            int minDist = Integer.MAX_VALUE, maxDist = Integer.MIN_VALUE;
            for (int j = 0; j < r; j++) {
                for (int l = 0; l < b; l++) {
                    dist[j][l] = (int)Math.ceil(red[j].cartesianDistance(blue[l]));
                    if(minDist > dist[j][l])
                        minDist = dist[j][l];
                    if(maxDist < dist[j][l])
                        maxDist = dist[j][l];
                }
            }
            int lo = minDist, hi = maxDist;
            int bestLength = 0;
            while(lo <= hi) {
                int mid = (lo + hi) >> 1;
                int[] assigned = new int[b];
                Arrays.fill(assigned, -1);
                int result = 0;
                for (int j = 0; j < r; j++) {
                    boolean[] visited = new boolean[b];
                    if(bipartiteMatching(dist, mid, j, visited, assigned))
                        result++;
                }
                if(result >= k) {
                    bestLength = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            System.out.println(bestLength == 0 ? "Impossible" : bestLength);
        }
    }

    private static boolean bipartiteMatching(int[][] dist, int currentLength, int red, boolean[] visited, int[]
            assigned) {
        int b = visited.length;
        for (int i = 0; i < b; i++) {
            if(dist[red][i] <= currentLength && !visited[i]) {
                visited[i] = true;
                if(assigned[i] < 0 || bipartiteMatching(dist, currentLength, assigned[i], visited, assigned)) {
                    assigned[i] = red;
                    return true;
                }
            }
        }
        return false;
    }
}