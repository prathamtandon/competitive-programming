import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Q594 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line = reader.readLine()) != null) {
            int signedInt = Integer.parseInt(line);
            String binaryString = signed32IntToBinary(signedInt);
            binaryString = convertToOppositeEndian(binaryString);
            printOutput(signedInt, binaryTosigned32Int(binaryString));
        }
    }

    private static String signed32IntToBinary(int value) {
        String binaryString = Integer.toBinaryString(value);
        char pad = value >= 0 ? '0' : '1';
        return String.format("%32s", binaryString).replace(' ', pad);
    }

    private static String convertToOppositeEndian(String binaryString) {
        StringBuilder result = new StringBuilder();
        int startIndex = 24;
        while(startIndex >= 0) {
            result.append(binaryString.substring(startIndex, startIndex + 8));
            startIndex -= 8;
        }
        return result.toString();
    }

    private static int binaryTosigned32Int(String binaryString) {
        return Integer.parseUnsignedInt(binaryString, 2);
    }

    private static void printOutput(int original, int converted) {
        System.out.println(original + " converts to " + converted);
    }
}