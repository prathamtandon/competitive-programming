import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Q957 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int[] arr = new int[100000];
        while(true) {
            line = reader.readLine();
            if(line == null || line.length() == 0)
                break;
            int Y = Integer.parseInt(line);
            int P = Integer.parseInt(reader.readLine());
            for (int i = 0; i < P; i++) {
                arr[i] = Integer.parseInt(reader.readLine());
            }
            int max = 0;
            int start = 0, end = 0;
            for (int i = 0; i < P; i++) {
                int temp = arr[i] + Y - 1;
                int last = floor(arr, i, P - 1, temp);
                if(last == -1)
                    continue;
                int count = last - i + 1;
                if(count > max) {
                    max = count;
                    start = arr[i];
                    end = arr[last];
                }
            }
            System.out.println(max + " " + start + " " + end);
            line = reader.readLine();
            if(line == null)
                break;
        }
    }

    private static int floor(int[] arr, int lo, int hi, int key) {
        if(arr[lo] > key) return -1;
        if(arr[hi] <= key) return hi;
        if(lo < hi) {
            int mid = (lo + hi) / 2;
            if(arr[mid] <= key && arr[mid + 1] > key)
                return mid;
            if(arr[mid] > key)
                return floor(arr, lo, mid - 1, key);
            return floor(arr, mid + 1, hi, key);
        }
        return -1;
    }
}