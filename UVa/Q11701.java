import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Q11701 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        final double EPSILON = 10e-15;
        while(!(line = reader.readLine()).equals("END")) {
            double m = Double.parseDouble(line.trim());

            double lo = 0.0;
            double hi = 1.0;
            boolean isMember = true;
            while(true) {
                if(Math.abs(lo - m) < EPSILON || Math.abs(hi - m) < EPSILON)
                    break;
                double x = (hi - lo) / 3;
                if(m > lo + x && m < lo + 2 * x) {
                    isMember = false;
                    break;
                }
                if(m <= lo + x) {
                    hi = lo + x;
                } else {
                    lo = lo + 2 * x;
                }
            }

            if (isMember)
                System.out.println("MEMBER");
            else
                System.out.println("NON-MEMBER");
        }
    }
}