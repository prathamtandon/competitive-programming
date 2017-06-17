import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Q628 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        PasswordGenerator pw;
        while((line = reader.readLine()) != null && line.length() != 0) {
            int numWords = Integer.parseInt(line);
            String[] words = new String[numWords];
            for (int i = 0; i < numWords; i++) {
                words[i] = reader.readLine();
            }
            int numRules = Integer.parseInt(reader.readLine());
            String[] rules = new String[numRules];
            for (int i = 0; i < numRules; i++) {
                rules[i] = reader.readLine();
            }
            pw = new PasswordGenerator(words);
            System.out.println("--");
            for(String rule: rules) {
                pw.printPasswordsForRule(rule, 0, "");
            }
        }
    }

    private static class PasswordGenerator{
        private String[] words;
        private int[] digits = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        PasswordGenerator(String[] words) {
            this.words = words;
        }

        void printPasswordsForRule(String rule, int ruleIndex, String password) {
            if(ruleIndex >= rule.length())
                System.out.println(password);
            else if(rule.charAt(ruleIndex) == '#')
                for(String word: words)
                    printPasswordsForRule(rule, ruleIndex + 1, password + word);
            else
                for(int digit: digits)
                    printPasswordsForRule(rule, ruleIndex + 1, password + digit);
        }
    }
}