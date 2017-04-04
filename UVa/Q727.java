import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

class Q727 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tests = Integer.parseInt(reader.readLine());
        reader.readLine();
        while(tests > 0) {
            StringBuilder infix = new StringBuilder();
            String line;
            while(!(line = reader.readLine()).equals("")) {
                infix.append(line);
            }
            System.out.println(infixToPostfix(infix.toString()));
            tests--;
            if(tests > 0)
                System.out.println();
        }
    }

    private static String infixToPostfix(String infix) {
        char[] infixChars = infix.toCharArray();
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (char c: infixChars) {
            if(c == '(')
                stack.push(c);
            else if(Character.isDigit(c))
                postfix.append(c);
            else if(c == ')') {
                while(!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                if(!stack.isEmpty())
                    stack.pop();
            }
            else {
                while(!stack.isEmpty() && getPriority(c) <= getPriority(stack.peek())) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }
        while(!stack.isEmpty())
            postfix.append(stack.pop());

        return postfix.toString();
    }

    private static int getPriority(char c) {
        switch (c) {
            case '*':
            case '/':
                return 1;
            case '+':
            case '-':
                return 0;
            default:
                return -1;
        }
    }
}