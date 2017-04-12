import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Q10158 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        War war = new War(n);
        String line;
        while(true) {
            line = reader.readLine().trim();
            StringTokenizer tokenizer = new StringTokenizer(line);
            int code = Integer.parseInt(tokenizer.nextToken());
            int id1 = Integer.parseInt(tokenizer.nextToken());
            int id2 = Integer.parseInt(tokenizer.nextToken());
            if(code == 0 && id1 == 0 && id2 == 0)
                break;
            int result = war.processRequest(code, id1, id2);
            if(result == 0 || result == 1 || result == -1)
                System.out.println(String.valueOf(result));
        }
    }

    private static class War {
        private int[] fset;
        private int[] eset;
        private final int UNUSED = 2;

        War(int n) {
            fset = new int[n];
            eset = new int[n];
            initFSet();
            initEset();
        }

        private void initFSet() {
            for (int i = 0; i < fset.length; i++) {
                fset[i] = i;
            }
        }

        private void initEset() {
            for (int i = 0; i < eset.length; i++) {
                eset[i] = -1;
            }
        }

        int processRequest(int c, int id1, int id2) {
            switch (c) {
                case 1:
                    return setFriends(id1, id2);
                case 2:
                    return setEnemies(id1, id2);
                case 3:
                    return areFriends(id1, id2);
                case 4:
                    return areEnemies(id1, id2);
                default:
                    return UNUSED;
            }
        }

        private int find(int i) {
            if(fset[i] == i)
                return i;
            fset[i] = find(fset[i]);
            return fset[i];
        }

        private int areFriends(int u, int v) {
            if(u == v || find(u) == find(v))
                return 1;
            return 0;
        }

        private int areEnemies(int u, int v) {
            if(u == v)
                return 0;
            if(find(u) == u && eset[u] == -1)
                return 0;
            if(find(v) == v && eset[v] == -1)
                return 0;
            int fu = find(u);
            int fv = find(v);
            if(eset[fu] == fv || eset[fv] == fu)
                return 1;
            return 0;
        }

        private int setFriends(int u, int v) {
            if(u == v || areFriends(u, v) == 1)
                return UNUSED;
            if(areEnemies(u, v) == 1)
                return -1;
            int fu = find(u);
            int fv = find(v);
            if(eset[fu] != -1 && eset[fv] != -1) {
                int eu = find(eset[fu]);
                int ev = find(eset[fv]);
                fset[eu] = ev;
                fset[fu] = fv;
            }
            else if(eset[fu] != -1) {
                fset[fv] = fu;
            }
            else if(eset[v] != -1) {
                fset[fu] = fv;
            }
            else {
                fset[fu] = fv;
            }

            return UNUSED;
        }

        private int setEnemies(int u, int v) {
            if(u == v || areFriends(u, v) == 1)
                return -1;
            if(areEnemies(u, v) == 1)
                return UNUSED;
            int fu = find(u);
            int fv = find(v);
            if(eset[fu] != -1 && eset[fv] != -1) {
                fset[find(eset[fv])] = fu;
                fset[find(eset[fu])] = fv;
            }
            else if(eset[fu] != -1) {
                fset[find(eset[fu])] = fv;
            }
            else if(eset[fv] != -1) {
                fset[find(eset[fv])] = fu;
            }
            eset[fu] = fv;
            eset[fv] = fu;

            return UNUSED;
        }
    }
}