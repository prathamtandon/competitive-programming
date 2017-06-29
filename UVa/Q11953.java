import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Q11953 {
    private static int N = 0;
    private static char[][] grid = new char[105][105];
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine());
        int i = 1;
        String line;
        while(i <= T) {
            line = reader.readLine().trim();
            if(line.length() == 0)
                continue;
            int ships = 0;
            N = Integer.parseInt(line);
            for (int j = 0; j < N; j++) {
                line = reader.readLine();
                for (int k = 0; k < N; k++) {
                    grid[j][k] = line.charAt(k);
                }
            }
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    if(grid[j][k] == 'x') {
                        if(j > 0 && (grid[j-1][k] == 'x' || grid[j-1][k] == '@')) {
                            int ans = floodFill(j, k, true);
                            if(ans > N/2)
                                ships += ans;
                            else
                                ships += 1;
                        }
                        else if(j < N - 1 && (grid[j+1][k] == 'x' || grid[j+1][k] == '@')) {
                            int ans = floodFill(j, k, true);
                            if(ans > N/2)
                                ships += ans;
                            else
                                ships += 1;
                        }
                        else if(k > 0 && (grid[j][k-1] == 'x' || grid[j][k-1] == '@')) {
                            int ans = floodFill(j, k, false);
                            if(ans > N/2)
                                ships += ans;
                            else
                                ships += 1;
                        }
                        else if(k < N-1 && (grid[j][k+1] == 'x' || grid[j][k+1] == '@')) {
                            int ans = floodFill(j, k, false);
                            if(ans > N/2)
                                ships += ans;
                            else
                                ships += 1;
                        }
                        else
                            ships += 1;
                    }
                }
            }
            System.out.println("Case " + i + ": " + ships);
            i++;
        }
    }

    private static int floodFill(int i, int j, boolean isHorizontal) {
        if(i < 0 || i >= N || j < 0 || j >= N)
            return 0;
        if(grid[i][j] == '.')
            return 0;
        grid[i][j] = '.';
        int ans = 1;
        if(isHorizontal) {
            ans += floodFill(i + 1, j, isHorizontal);
            ans += floodFill(i - 1, j, isHorizontal);
        } else {
            ans += floodFill(i, j + 1, isHorizontal);
            ans += floodFill(i, j - 1, isHorizontal);
        }
        return ans;
    }
}