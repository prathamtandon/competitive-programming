import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

class Uva10400
{
    private static int[][] dp = new int[101][64001];
    private static int[] seq = new int[101];
    private static boolean done;
    private static final int MOD = 32000;
    private static ArrayList<Character> operators;
    private static int target;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(reader.readLine());
        StringTokenizer tokenizer;
        for (int i = 0; i < tc; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int p = Integer.parseInt(tokenizer.nextToken());
            for (int j = 0; j < p; j++) {
                seq[j] = Integer.parseInt(tokenizer.nextToken());
            }
            target = Integer.parseInt(tokenizer.nextToken());
            initDp();
            if(solve(p - 1, target) == 1)
            {
                // print solution
                Collections.reverse(operators);
                for (int j = 0; j < p; j++) {
                    if(j == p - 1)
                        System.out.print(seq[j]);
                    else
                        System.out.print(seq[j] + String.valueOf(operators.get(j)));
                }
                System.out.println("=" + target);
            }
            else
            {
                System.out.println("NO EXPRESSION");
            }
        }
    }

    private static void initDp()
    {
        done = false;
        operators = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            Arrays.fill(dp[i], -1);
        }
    }

    private static int solve(int id, int target)
    {
        if(!(target >= -32000 && target <= 32000))
            return 0;
        if(id == 0 && seq[id] != target)
            return 0;
        if(id == 0)
        {
            done = true;
            return 1;
        }
        if(done)
            return 1;
        if(dp[id][target + MOD] != -1)
            return dp[id][target + MOD];
        operators.add('+');
        if(solve(id - 1, target - seq[id]) == 0)
            operators.remove(operators.size() - 1);
        else
            return dp[id][target + MOD] = 1;
        operators.add('-');
        if(solve(id - 1, target + seq[id]) == 0)
            operators.remove(operators.size() - 1);
        else
            return dp[id][target + MOD] = 1;
        if(target % seq[id] == 0)
        {
            operators.add('*');
            if(solve(id - 1, target / seq[id]) == 0)
                operators.remove(operators.size() - 1);
            else
                return dp[id][target + MOD] = 1;
        }
        operators.add('/');
        if(solve(id - 1, target * seq[id]) == 0)
            operators.remove(operators.size() - 1);
        else
            return dp[id][target + MOD] = 1;
        return dp[id][target + MOD] = 0;
    }
}