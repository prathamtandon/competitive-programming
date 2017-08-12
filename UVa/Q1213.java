import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q1213 {
    private static ArrayList<Integer> primes = new ArrayList<>();
    private static void generatePrimes() {
        boolean[] visited = new boolean[1121];
        Arrays.fill(visited, true);
        for (int i = 2; i*i <= 1120 ; i++) {
            if(!visited[i]) continue;
            for (int j = 2*i; j <= 1120 ; j+=i) {
                visited[j] = false;
            }
        }
        primes.add(0);
        for (int i = 2; i < 1121; i++) {
            if(visited[i])
                primes.add(i);
        }

    }
    public static void main(String[] args) throws IOException {
        generatePrimes();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int[][][] table = new int[1125][20][primes.size()+10];
        while(true) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(tokenizer.nextToken());
            int k = Integer.parseInt(tokenizer.nextToken());
            if(n == 0 && k == 0) break;
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= k; j++) {
                    for (int l = 1; l < primes.size(); l++) {
                        table[i][j][l] = 0;
                    }
                }
            }

            for (int i = 0; i < primes.size(); i++) {
                table[0][0][i] = 1;
            }

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= k; j++) {
                    for (int l = 1; l < primes.size(); l++) {
                        table[i][j][l] = table[i][j][l-1];
                        if(primes.get(l) <= i)
                            table[i][j][l] += table[i-primes.get(l)][j-1][l-1];
                    }
                }
            }

            System.out.println(table[n][k][primes.size()-1]);
            
        }
    }
}