import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

class Q787 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int[] arr = new int[105];
        int i;
        while((line = reader.readLine()) != null && line.length() > 0) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            i = 0;
            while(true) {
                int num = Integer.parseInt(tokenizer.nextToken());
                if(num == -999999)
                    break;
                arr[i++] = num;
            }
            System.out.println(getMaxSubarrayProduct(arr, i));
        }
    }

    private static String getMaxSubarrayProduct(int[] arr, int n) {
        BigInteger ans = BigInteger.valueOf(arr[0]);
        BigInteger prevMaxProd = BigInteger.valueOf(arr[0]);
        BigInteger prevMinProd = BigInteger.valueOf(arr[0]);

        for (int i = 1; i < n; i++) {
            BigInteger temp = BigInteger.valueOf(arr[i]);
            BigInteger curMaxProd = prevMaxProd.multiply(temp).max(prevMinProd.multiply(temp)).max(temp);
            BigInteger curMinProd = prevMaxProd.multiply(temp).min(prevMinProd.multiply(temp)).min(temp);
            ans = ans.max(curMaxProd);
            prevMaxProd = curMaxProd;
            prevMinProd = curMinProd;
        }

        return ans.toString();
    }
}