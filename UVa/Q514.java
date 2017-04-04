import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

class Q514 {
    private final static String EOF = "0";
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while(!(line = reader.readLine()).equals(EOF)) {
            int numCoaches = Integer.parseInt(line);
            while(!(line = reader.readLine()).equals(EOF)) {
                if(isValidPermutation(line, numCoaches))
                    System.out.println("Yes");
                else
                    System.out.println("No");
            }
            System.out.println();
        }
    }

    private static boolean isValidPermutation(String permutation, int numCoaches) {
        Stack<Integer> stack = new Stack<>();
        int sourceCoach = 1;
        String[] coaches = permutation.trim().split("\\s+");
        int i = 0;
        while(i < numCoaches && sourceCoach <= numCoaches) {
            int destinationCoach = Integer.parseInt(coaches[i]);
            if(sourceCoach == destinationCoach) {
                sourceCoach++;
                i++;
            } else if(!stack.isEmpty() && stack.peek() == destinationCoach) {
                stack.pop();
                i++;
            } else {
                stack.push(sourceCoach);
                sourceCoach++;
            }
        }
        while(i < numCoaches && !stack.isEmpty()) {
            if(Integer.parseInt(coaches[i]) != stack.peek())
                return false;
            stack.pop();
            i++;
        }
        return stack.isEmpty() && i == numCoaches;
    }
}