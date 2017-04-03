import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Q11462 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int sequenceLength = Integer.parseInt(reader.readLine());
            if(sequenceLength == 0)
                break;
            int[] ages = new int[sequenceLength];
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            for (int i = 0; i < sequenceLength; i++) {
                ages[i] = Integer.parseInt(tokenizer.nextToken());
            }
            sortAges(ages);
            for (int i = 0; i < sequenceLength - 1; i++) {
                System.out.print(ages[i] + " ");
            }
            System.out.println(ages[sequenceLength - 1]);
        }
    }

    private static void sortAges(int[] ages) {
        int[] count = new int[100];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for(int age: ages)
            count[age]++;
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        int[] sortedAges = new int[ages.length];
        for(int age: ages) {
            sortedAges[count[age] - 1] = age;
            count[age]--;
        }
        System.arraycopy(sortedAges, 0, ages, 0, ages.length);
    }
}