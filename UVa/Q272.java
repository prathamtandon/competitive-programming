import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        TexTransform tt = new TexTransform();
        tt.transform(reader);
    }

    private static class TexTransform {
        private final String SOURCE_QUOTE = "\"";
        private final String TARGET_OPENING = "``";
        private final String TARGET_CLOSING = "''";

        void transform(BufferedReader in) throws IOException {
            String line;
            boolean isOpening = true;
            while((line = in.readLine()) != null) {
                StringBuilder stringBuilder = new StringBuilder(line);
                int lastIndex = stringBuilder.indexOf(SOURCE_QUOTE);
                while (lastIndex != -1) {
                    String targetString = isOpening ? TARGET_OPENING : TARGET_CLOSING;
                    stringBuilder.replace(lastIndex, lastIndex + SOURCE_QUOTE.length(), targetString);
                    lastIndex += targetString.length();
                    isOpening = !isOpening;
                    lastIndex = stringBuilder.indexOf(SOURCE_QUOTE, lastIndex);
                }
                System.out.println(stringBuilder.toString());
            }
        }
    }
}
