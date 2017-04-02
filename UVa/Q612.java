import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class Q612 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int numCases = Integer.parseInt(reader.readLine());
        reader.readLine();
        while(numCases > 0) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int length = Integer.parseInt(tokenizer.nextToken());
            int numStrings = Integer.parseInt(tokenizer.nextToken());
            DnaString[] dnaStrings = new DnaString[numStrings];
            int index = 0;
            while(index < numStrings)
                dnaStrings[index++] = new DnaString(reader.readLine(), length);
            Arrays.sort(dnaStrings, new DnaStringComparator());
            for (int i = 0; i < numStrings; i++) {
                System.out.println(dnaStrings[i].getString());
            }
            numCases--;
            if(numCases > 0)
                System.out.println();
            reader.readLine();
        }
    }

    private static class DnaStringComparator implements Comparator<DnaString> {
        @Override
        public int compare(DnaString str1, DnaString str2) {
            int ic1 = str1.getInversionCount();
            int ic2 = str2.getInversionCount();
            return ic1 < ic2 ? -1 : ic1 == ic2 ? 0 : 1;
        }
    }

    private static class DnaString {
        private String string;
        private int length;
        private int inversionCount;

        DnaString(String string, int stringLen) {
            this.string = string;
            this.length = stringLen;
            setInversionCount();
        }

        private void setInversionCount() {
            char[] stringAsChars = this.string.toCharArray();
            for (int i = 0; i < length - 1; i++) {
                for (int j = i + 1; j < length; j++) {
                    if(stringAsChars[i] > stringAsChars[j])
                        this.inversionCount++;
                }
            }
        }

        String getString() {
            return this.string;
        }

        int getInversionCount() {
            return this.inversionCount;
        }
    }
}