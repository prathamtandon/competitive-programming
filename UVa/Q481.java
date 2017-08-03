import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

class Q481 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int[] arr = new int[10000000];
        int[] lis = new int[10000000];
        int[] lis_id = new int[10000000];
        int[] prev = new int[10000000];
        int end = 0;
        int best_lis_len = 1;
        int best_lis_last_idx = 0;
        while((line = reader.readLine()) != null && line.length() > 0) {
            arr[end] = Integer.parseInt(line.trim());
            end++;
        }

        lis[0] = arr[0];
        prev[0] = -1;
        lis_id[0] = 0;

        for (int i = 1; i < end; i++) {
            int j = lowerBound(lis, 0, best_lis_len - 1, arr[i]);
            if(j == -1) {
                prev[i] = lis_id[best_lis_len - 1];
                lis_id[best_lis_len] = i;
                lis[best_lis_len] = arr[i];
                best_lis_len++;
                best_lis_last_idx = i;
            } else {
                prev[i] = j == 0 ? -1 : lis_id[j - 1];
                lis_id[j] = i;
                lis[j] = arr[i];
                if(j == best_lis_len - 1)
                    best_lis_last_idx = i;
            }
        }

        System.out.println(best_lis_len);
        System.out.println('-');
        Stack<Integer> S = new Stack<>();
        for (int i = best_lis_last_idx; i != -1; i = prev[i]) {
            S.add(arr[i]);
        }
        while (!S.isEmpty()) {
            System.out.println(S.peek());
            S.pop();
        }
    }

    private static int lowerBound(int[] arr, int low, int high, int key) {
        int ans = -1;
        while(low <= high) {
            int mid = (low + high) >> 1;
            if(arr[mid] >= key) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }
}