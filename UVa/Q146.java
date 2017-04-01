import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Q146 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while(!(line = reader.readLine()).equals("#")) {
            System.out.println(getNextCode(line));
        }
    }

    private static String getNextCode(String currentCode) {
        char[] currentCodeChars = currentCode.toCharArray();
        int i, j;
        int length = currentCodeChars.length;
        for (i = length - 2; i >= 0; i--) {
            if(currentCodeChars[i] < currentCodeChars[i+1])
                break;
        }
        if(i < 0)
            return "No Successor";
        Arrays.sort(currentCodeChars, i+1, length);
        for(j = i+1; j < length; j++)
            if(currentCodeChars[i] < currentCodeChars[j])
                break;
        char temp = currentCodeChars[i];
        currentCodeChars[i] = currentCodeChars[j];
        currentCodeChars[j] = temp;
        return new String(currentCodeChars);
    }
}