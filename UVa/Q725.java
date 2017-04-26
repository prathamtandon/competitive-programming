import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

class Q725 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(reader.readLine());
        while(true) {
            int flag = 0;
            for (int i = 1234; i <= 98765; i++) {
                if(i < 10000 && String.valueOf(i).contains("0"))
                    continue;
                if(hasUniqueDigits(i)) {
                    int result = N * i;
                    String numerator = i < 10000 ? "0" + i : "" + i;
                    long toCheck = Long.parseLong("" + result + numerator);
                    if(getNumberOfDigits(result) == 5 && hasUniqueDigits(toCheck)) {
                        System.out.println(result + " / " + numerator + " = " + N);
                        flag = 1;
                    }
                }
            }
            if(flag == 0)
                System.out.println("There are no solutions for " + N + ".");
            N = Integer.parseInt(reader.readLine());
            if(N == 0)
                break;
            else
                System.out.println();
        }
    }

    private static boolean hasUniqueDigits(long number) {
        HashSet<Long> digits = new HashSet<>();
        while(number > 0) {
            long digit = number % 10L;
            if(digits.contains(digit))
                return false;
            digits.add(digit);
            number /= 10;
        }
        return true;
    }

    private static int getNumberOfDigits(int number) {
        if(number == 0)
            return 1;
        int count = 0;
        while(number > 0) {
            number /= 10;
            count++;
        }
        return count;
    }
}