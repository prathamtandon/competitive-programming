import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q11951 {
    private static class Result {
        int area;
        long cost;
    }

    private static class KadaneResult {
        int len;
        long sum;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine());
        int[][] strips = new int[110][110];
        StringTokenizer tokenizer;
        for (int i = 0; i < T; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int R = Integer.parseInt(tokenizer.nextToken());
            int C = Integer.parseInt(tokenizer.nextToken());
            long budget = Integer.parseInt(tokenizer.nextToken());
            for (int j = 0; j < R; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                for (int k = 0; k < C; k++) {
                    strips[j][k] = Integer.parseInt(tokenizer.nextToken());
                }
            }
            Result r = getMaxAreaWithinBudget(strips, R, C, budget);
            System.out.println("Case #" + (i + 1) + ": " + r.area + " " + r.cost);
        }
    }

    private static Result getMaxAreaWithinBudget(int[][] strips, int R, int C, long budget) {
        int[] temp = new int[R];
        Result r = new Result();
        r.area = 0;

        for (int left = 0; left < C; left++) {
            Arrays.fill(temp, 0);
            boolean flag = true;
            for(int right = left; right < C; right++) {
                for (int i = 0; i < R; i++) {
                    temp[i] += strips[i][right];
                    if(temp[i] <= budget) {
                        flag = false;
                    }
                }
                if(flag)
                    break;
                KadaneResult kr = getMaxSumSubarrayLimits(temp, budget);
                int area = (right - left + 1) * kr.len;
                if(area > r.area) {
                    r.area = area;
                    r.cost = kr.sum;
                }
                else if(area == r.area && r.cost > kr.sum) {
                    r.cost = kr.sum;
                }
            }
        }

        return r;
    }

    private static KadaneResult getMaxSumSubarrayLimits(int[] arr, long budget) {
        long ans = 0;
        long sum = arr[0];
        int start = 0;
        int bestLength = 0;

        for (int end = 1; end <= arr.length; end++) {
            while(sum > budget && start + 1 <= end) {
                sum -= arr[start];
                start++;
            }
            if(end - start > bestLength) {
                ans = sum;
                bestLength = end - start;
            }
            else if(end - start == bestLength) {
                ans = Math.min(ans, sum);
            }
            if(end < arr.length)
                sum += arr[end];
        }

        KadaneResult kr = new KadaneResult();
        kr.len = bestLength;
        kr.sum = ans;
        return kr;
    }
}