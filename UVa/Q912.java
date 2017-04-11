import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Q912 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        boolean flag = true;
        while(((line = reader.readLine()) != null)) {
            if(line.length() == 0)
                continue;
            if(flag)
                flag = false;
            else
                System.out.println();
            int m = Integer.parseInt(line);
            int count = 0;
            DnaSequence dna = new DnaSequence();
            StringBuilder seq = new StringBuilder();
            while(++count <= m) {
                seq.append(reader.readLine());
            }
            dna.addFirst(seq.toString());
            count = 0;
            seq = new StringBuilder();
            while(++count <= m) {
                seq.append(reader.readLine());
            }
            dna.addSecond(seq.toString());
            dna.printMutation();
        }
    }

    private static class DnaSequence {
        private int[] pset = new int[20];
        private char[] first;
        private char[] second;
        DnaSequence() {
            for (int i = 0; i < 20; i++) {
                pset[i] = i;
            }
        }

        void addFirst(String first) {
            this.first = first.toCharArray();
        }

        void addSecond(String second) {
            this.second = second.toCharArray();
        }

        void printMutation() {
            if(!hasMutation()) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
                for (int i = 4; i < 20 ; i++) {
                    if(pset[i] != i) {
                        int u = findSet(i);
                        if(0 <= u && u <= 3)
                            System.out.println((i - 3) + " " + itoc(u));
                    }
                }
            }
        }

        private String itoc(int i) {
            switch (i) {
                case 0:
                    return "A";
                case 1:
                    return "B";
                case 2:
                    return "C";
                case 3:
                    return "D";
                default:
                    return "";
            }
        }

        private boolean hasMutation() {
            for (int i = 0; i < first.length; i++) {
                int u = ctoi(first[i]);
                int v = ctoi(second[i]);
                if(findSet(u) != findSet(v) && !union(u, v))
                    return false;

            }
            return true;
        }

        private int ctoi(char c) {
            switch (c) {
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                    return c - 'A';
            }
            return c - '0' + 3;
        }

        private int findSet(int i) {
            if(i == pset[i])
                return i;
            pset[i] = findSet(pset[i]);
            return pset[i];
        }

        private boolean union(int u, int v) {
            if(u <= 3 && v <= 3)
                return false;
            if(u <= 3) {
                int pv = findSet(v);
                if(pv <= 3)
                    return false;
                if(pv != v) {
                    pset[v] = u;
                    pset[pv] = u;
                    return true;
                } else {
                    pset[v] = u;
                    return true;
                }
            }
            else if(v <= 3) {
                int pu = findSet(u);
                if(pu <= 3)
                    return false;
                if(pu != u) {
                    pset[u] = v;
                    pset[pu] = v;
                    return true;
                } else {
                    pset[u] = v;
                    return true;
                }
            }
            else if(u == findSet(u)) {
                pset[u] = findSet(v);
                return true;
            } else if(v == findSet(v)) {
                pset[v] = findSet(u);
                return true;
            }
            return false;
        }
    }
}