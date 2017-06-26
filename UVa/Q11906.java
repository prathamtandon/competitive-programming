import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Q11906 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine().trim());
        StringTokenizer tokenizer;
        boolean[][] water = new boolean[105][105];
        boolean[][] visited = new boolean[105][105];
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < 105; j++) {
                Arrays.fill(water[j], false);
                Arrays.fill(visited[j], false);
            }
            tokenizer = new StringTokenizer(reader.readLine());
            int R = Integer.parseInt(tokenizer.nextToken());
            int C = Integer.parseInt(tokenizer.nextToken());
            int M = Integer.parseInt(tokenizer.nextToken());
            int N = Integer.parseInt(tokenizer.nextToken());
            int W = Integer.parseInt(reader.readLine().trim());
            for (int j = 0; j < W; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int x = Integer.parseInt(tokenizer.nextToken());
                int y = Integer.parseInt(tokenizer.nextToken());
                water[x][y] = true;
            }
            if(water[0][0]) {
                System.out.println("Case " + (i + 1) + ": 1 0");
                continue;
            }
            Queue<Point> queue = new LinkedList<>();
            queue.add(new Point(0, 0));
            visited[0][0] = true;
            int even = 0, odd = 0;
            while(!queue.isEmpty()) {
                Point p = queue.remove();
                int count = 0;
                ArrayList<Point> temp = new ArrayList<>();
                for (int j = 0; j < 2; j++) {
                    int m = j == 0 ? M : N;
                    int n = j == 0 ? N : M;
                    if (p.x + m >= 0 && p.x + m < R && p.y + n >= 0 && p.y + n < C && !water[p.x + m][p.y + n]) {
                        Point p1 = new Point(p.x + m, p.y + n);
                        if(!temp.contains(p1)) {
                            temp.add(p1);
                            count++;
                        }
                        if (!visited[p.x + m][p.y + n]) {
                            visited[p.x + m][p.y + n] = true;
                            queue.add(p1);
                        }
                    }
                    if (p.x + m >= 0 && p.x + m < R && p.y - n >= 0 && p.y - n < C && !water[p.x + m][p.y - n]) {
                        Point p1 = new Point(p.x + m, p.y - n);
                        if(!temp.contains(p1)) {
                            temp.add(p1);
                            count++;
                        }
                        if (!visited[p.x + m][p.y - n]) {
                            visited[p.x + m][p.y - n] = true;
                            queue.add(p1);
                        }
                    }
                    if (p.x - m >= 0 && p.x - m < R && p.y + n >= 0 && p.y + n < C && !water[p.x - m][p.y + n]) {
                        Point p1 = new Point(p.x - m, p.y + n);
                        if(!temp.contains(p1)) {
                            temp.add(p1);
                            count++;
                        }
                        if (!visited[p.x - m][p.y + n]) {
                            visited[p.x - m][p.y + n] = true;
                            queue.add(p1);
                        }
                    }
                    if (p.x - m >= 0 && p.x - m < R && p.y - n >= 0 && p.y - n < C && !water[p.x - m][p.y - n]) {
                        Point p1 = new Point(p.x - m, p.y - n);
                        if(!temp.contains(p1)) {
                            temp.add(p1);
                            count++;
                        }
                        if (!visited[p.x - m][p.y - n]) {
                            visited[p.x - m][p.y - n] = true;
                            queue.add(p1);
                        }
                    }
                }
                if(count % 2 == 0)
                    even++;
                else
                    odd++;
            }
            System.out.println("Case " + (i + 1) + ": " + even + " " + odd);
        }
    }
}