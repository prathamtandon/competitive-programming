import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Q11094 {
    private static char[][] region = new char[25][25];
    private static int mX = 0;
    private static int mY = 0;
    private static int mR = 0;
    private static int rId = 1;
    private static int M = -1;
    private static int N = -1;
    private static int[] dx = {1 , -1, 0, 0};
    private static int[] dy = {0 , 0, 1, -1};
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        StringTokenizer tokenizer;
        while((line = reader.readLine()) != null && line.trim().length() != 0) {
            tokenizer = new StringTokenizer(line);
            M = Integer.parseInt(tokenizer.nextToken());
            N = Integer.parseInt(tokenizer.nextToken());
            for (int i = 0; i < M; i++) {
                line = reader.readLine().trim();
                for (int j = 0; j < N; j++) {
                    region[i][j] = line.charAt(j);
                }
            }
            tokenizer = new StringTokenizer(reader.readLine());
            mX = Integer.parseInt(tokenizer.nextToken());
            mY = Integer.parseInt(tokenizer.nextToken());
            rId = 1;
            int best = 0;
            mR = 0;
            char c = region[mX][mY];
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    if(region[i][j] == c) {
                        int size = floodFill(i, j, c, ' ');
                        if(best < size && mR != rId) {
                            best = size;
                        }
                        rId++;
                    }
                }
            }
            System.out.println(best);
            reader.readLine();
        }
    }

    private static int floodFill(int i, int j, char oldC, char newC) {
        if(i < 0 || i >= M || j < 0 || j >= N)
            return 0;
        if(region[i][j] != oldC)
            return 0;
        region[i][j] = newC;
        int ans = 1;
        if(i == mX && j == mY)
            mR = rId;
        for (int k = 0; k < dx.length; k++) {
            ans += floodFill(i + dx[k], j + dy[k], oldC, newC);
        }
        if(j == 0)
            ans += floodFill(i, N - 1, oldC, newC);
        if(j == N - 1)
            ans += floodFill(i, 0, oldC, newC);
        return ans;
    }
}