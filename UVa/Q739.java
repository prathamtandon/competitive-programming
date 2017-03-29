import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

class Q739 {
    private final static String REGEX_WHITESPACE = "\\s+";
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        SoundexCode soundexCode = new SoundexCode();
        printHeader();
        while((line = reader.readLine()) != null) {
            String cleanLine = line.trim().replaceAll(REGEX_WHITESPACE, "");
            printNameAndCode(cleanLine, soundexCode.getEncoding(cleanLine));
        }
        printTail();
    }

    private static void printHeader() {
        printNameAndCode("NAME", "SOUNDEX CODE");
    }

    private static void printTail() {
        for (int i = 0; i < 19; i++) {
            System.out.print(" ");
        }
        System.out.println("END OF OUTPUT");
    }

    private static void printNameAndCode(String name, String encoding) {
        for (int i = 0; i < 9; i++) {
            System.out.print(" ");
        }
        System.out.print(name);
        for (int i = 0; i < 25 - name.length(); i++) {
            System.out.print(" ");
        }
        System.out.println(encoding);
    }

    private static class SoundexCode {
        private static HashMap<String, Integer> codeMap;

        SoundexCode() {
            codeMap = new HashMap<>();
            initializeCodeMap();
        }

        private void initializeCodeMap() {
            codeMap.put("B", 1);
            codeMap.put("P", 1);
            codeMap.put("F", 1);
            codeMap.put("V", 1);
            codeMap.put("C", 2);
            codeMap.put("S", 2);
            codeMap.put("K", 2);
            codeMap.put("G", 2);
            codeMap.put("J", 2);
            codeMap.put("Q", 2);
            codeMap.put("X", 2);
            codeMap.put("Z", 2);
            codeMap.put("D", 3);
            codeMap.put("T", 3);
            codeMap.put("L", 4);
            codeMap.put("M", 5);
            codeMap.put("N", 5);
            codeMap.put("R", 6);
        }

        String getEncoding(String name) {
            StringBuilder builder = new StringBuilder();
            builder.append(name.charAt(0));
            char[] nameAsChars = name.toCharArray();
            for (int i = 1; i < nameAsChars.length; i++) {
                if(builder.length() == 4)
                    break;
                if(encodingExists(nameAsChars[i])) {
                    if(!encodingExists(nameAsChars[i-1]) || getCharEncoding(nameAsChars[i]) != getCharEncoding
                            (nameAsChars[i-1]))
                        builder.append(getCharEncoding(nameAsChars[i]));
                }
            }
            builder = padEncoding(builder);
            return builder.toString();
        }

        private StringBuilder padEncoding(StringBuilder builder) {
            while(builder.length() < 4) {
                builder.append(0);
            }
            return builder;
        }

        private int getCharEncoding(char c) {
            return codeMap.get("" + c);
        }

        private boolean encodingExists(char c) {
            return codeMap.containsKey("" + c);
        }
    }
}