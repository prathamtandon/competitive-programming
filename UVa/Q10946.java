import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

class Q10946 {
    private static class Result implements Comparable<Result> {
        char c;
        int size;

        Result(char c, int size) {
            this.c = c;
            this.size = size;
        }

        @Override
        public int compareTo(Result o) {
            if(size != o.size)
                return o.size - size;
            else
                return c - o.c;
        }
    }

    private static int[] dx = {1, -1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        StringTokenizer tokenizer;
        char[][] grid = new char[55][55];
        int testCase = 1;
        while(true) {
            line = reader.readLine().trim();
            if(line.length() == 0)
                continue;
            tokenizer = new StringTokenizer(line);
            int X = Integer.parseInt(tokenizer.nextToken());
            int Y = Integer.parseInt(tokenizer.nextToken());
            if(X == 0 && Y == 0)
                break;
            for (int i = 0; i < X; i++) {
                line = reader.readLine().trim();
                for (int j = 0; j < Y; j++) {
                    grid[i][j] = line.charAt(j);
                }
            }
            ArrayList<Result> results = new ArrayList<>();
            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    if(grid[i][j] >= 'A' && grid[i][j] <= 'Z') {
                        char c = grid[i][j];
                        int size = floodFill(grid, i, j, c, '.', X, Y);
                        Result r = new Result(c, size);
                        results.add(r);
                    }
                }
            }
            Collections.sort(results);
            System.out.println("Problem " + testCase + ":");
            testCase++;
            for(Result r: results) {
                System.out.println(r.c + " " + r.size);
            }
        }
    }

    private static int floodFill(char[][] grid, int i, int j, char oldC, char newC, int R, int C) {
        if(i < 0 || i >= R || j < 0 || j >= C)
            return 0;
        if(grid[i][j] != oldC)
            return 0;
        grid[i][j] = newC;
        int ans = 1;
        for (int k = 0; k < dx.length; k++) {
            ans += floodFill(grid, i + dx[k], j + dy[k], oldC, newC, R, C);
        }
        return ans;
    }
}