import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Q10810 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            int sequenceLength = Integer.parseInt(reader.readLine());
            if(sequenceLength == 0)
                break;
            int[] sequence = new int[sequenceLength];
            for (int i = 0; i < sequenceLength; i++) {
                sequence[i] = Integer.parseInt(reader.readLine());
            }
            System.out.println(getInversionCount(sequence, 0, sequenceLength - 1));
        }
    }

    private static long getInversionCount(int[] array, int low, int high) {
        if(low >= high)
            return 0;
        int mid = (low + high) >> 1;
        long inversionCount = 0;
        inversionCount += getInversionCount(array, low, mid);
        inversionCount += getInversionCount(array, mid+1, high);
        inversionCount += getMergeInversionCount(array, low, mid, high);
        return inversionCount;
    }

    private static long getMergeInversionCount(int[] array, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int k = 0;
        int i = low;
        int j = mid+1;
        long inversions = 0;
        while(i <= mid && j <= high) {
            if(array[i] < array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
                inversions += (mid - i + 1);
            }
        }
        while(i <= mid) {
            temp[k++] = array[i++];
        }
        while(j <= high) {
            temp[k++] = array[j++];
        }
        System.arraycopy(array, low, temp, 0, temp.length);
        return inversions;
    }
}