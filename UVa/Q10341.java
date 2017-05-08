import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

class Q10341 {
    private static final double EPSILON = 1e-9;
    private static final int MAX_ITERS = 100;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        DecimalFormat df = new DecimalFormat("0.0000");
        while((line = reader.readLine()) != null && line.length() > 0) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            int p = Integer.parseInt(tokenizer.nextToken());
            int q = Integer.parseInt(tokenizer.nextToken());
            int r = Integer.parseInt(tokenizer.nextToken());
            int s = Integer.parseInt(tokenizer.nextToken());
            int t = Integer.parseInt(tokenizer.nextToken());
            int u = Integer.parseInt(tokenizer.nextToken());
            double lowVal = solve(p, q, r, s, t, u, 0);
            double higVal = solve(p, q, r, s, t, u, 1);
            double result = solveHelper(p, q, r, s, t, u, 0, 1, higVal > lowVal, 0);
            if(result == -1)
                System.out.println("No solution");
            else
                System.out.println(df.format(result));
        }
    }

    private static double solve(int p, int q, int r, int s, int t, int u, double x) {
        return p * Math.exp(-x) + q * Math.sin(x) + r * Math.cos(x) + s * Math.tan(x) + t * x * x + u;
    }

    private static double solveHelper(int p, int q, int r, int s, int t, int u, double lo, double hi, boolean flag,
                                      int iter) {
        if(iter > MAX_ITERS)
            return -1;
        double mid = (lo + hi) / 2;
        double midVal = solve(p, q, r, s, t, u, mid);
        if(Math.abs(midVal - 0) <= EPSILON)
            return mid;
        if(midVal > 0) {
            if(flag)
                return solveHelper(p, q, r, s, t, u, lo, mid, flag, iter + 1);
            else
                return solveHelper(p, q, r, s, t, u, mid, hi, flag, iter + 1);

        } else {
            if(flag)
                return solveHelper(p, q, r, s, t, u, mid, hi, flag, iter + 1);
            else
                return solveHelper(p, q, r, s, t, u, lo, mid, flag, iter + 1);
        }
    }
 }