import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

class Q11136 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while(!(line = reader.readLine()).trim().equals("0")) {
            MalWart malWart = new MalWart();
            int numDays = Integer.parseInt(line.trim());
            while(numDays > 0) {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
                int numBills = Integer.parseInt(tokenizer.nextToken());
                while(numBills > 0) {
                    malWart.addBill(Integer.parseInt(tokenizer.nextToken()));
                    numBills--;
                }
                malWart.updatePromotionCost();
                numDays--;
            }
            System.out.println(malWart.getPromotionCost());
        }
    }

    private static class Bill implements Comparable<Bill> {

        private int value;
        private int id;

        Bill(int id, int value) {
            this.id = id;
            this.value = value;
        }

        public int compareTo(Bill other) {
            int ret = value - other.value;
            if(ret == 0)
                ret = id - other.id;
            return ret;
        }
    }

    private static class MalWart {
        private TreeMap<Bill, Integer> urn;
        private long cost;
        private static int id = 0;

        MalWart() {
            urn = new TreeMap<>();
            cost = 0L;
        }

        void addBill(int bill) {
            urn.put(new Bill(id++, bill), bill);
        }

        void updatePromotionCost() {
            int maxBill = urn.lastKey().value;
            int minBill = urn.firstKey().value;
            cost += (maxBill - minBill);
            urn.remove(urn.firstKey());
            urn.remove(urn.lastKey());
        }

        long getPromotionCost() {
            return cost;
        }
    }
}