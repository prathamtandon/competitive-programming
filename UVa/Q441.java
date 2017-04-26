import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Q441 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int[] arr = new int[20];
        int k = Integer.parseInt(tokenizer.nextToken());
        while(true) {
            for (int i = 0; i < k; i++) {
                arr[i] = Integer.parseInt(tokenizer.nextToken());
            }
            for(int a = 0; a <= k - 6; a++) {
                for(int b = a + 1; b <= k - 5; b++)
                    for(int c = b + 1; c <= k - 4; c++)
                        for(int d = c + 1; d <= k - 3; d++)
                            for(int e = d + 1; e <= k - 2; e++)
                                for(int f = e + 1; f <= k - 1; f++)
                                    System.out.println(arr[a] + " " + arr[b] + " " + arr[c] + " " + arr[d] + " " +
                                            arr[e] + " " + arr[f]);
            }
            tokenizer = new StringTokenizer(reader.readLine());
            k = Integer.parseInt(tokenizer.nextToken());
            if(k == 0)
                break;
            System.out.println();
        }
    }
}