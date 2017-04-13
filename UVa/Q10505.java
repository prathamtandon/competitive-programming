import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q10505 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(reader.readLine());
        InvitationManager im;
        reader.readLine();
        while(TC-- > 0) {
            int numPeople = Integer.parseInt(reader.readLine());
            im = new InvitationManager(numPeople);
            int id = 0;
            while (id < numPeople) {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
                tokenizer.nextToken();
                while(tokenizer.hasMoreTokens())
                    im.addEnemies(id, Integer.parseInt(tokenizer.nextToken()));
                id++;
            }
            System.out.println(im.getMaxInvites());
            if(TC > 0)
                reader.readLine();
        }
    }

    private static class InvitationManager {
        private final int MAX_PERSONS = 256;
        private int N;
        private int[] co;
        private ArrayList<Integer>[] enemies;
        private int fcount;
        private int ecount;
        private boolean bad;

        InvitationManager(int N) {
            this.N = N;
            co = new int[MAX_PERSONS];
            enemies = new ArrayList[MAX_PERSONS];
            Arrays.fill(co, -1);
            for (int i = 0; i < N; i++) {
                enemies[i] = new ArrayList<>();
            }
            fcount = 0;
            ecount = 0;
            bad = false;
        }

        void addEnemies(int u, int v) {
            if(v >= N) return;
            enemies[u].add(v);
        }

        int getMaxInvites() {
            int ans = 0;
            for (int i = 0; i < N; i++) {
                fcount = 0;
                ecount = 0;
                bad = false;
                if(co[i] == -1) {
                    dfs(i, 0);
                    if(bad) continue;
                    ans += Math.max(fcount, ecount);
                }
            }
            return ans;
        }

        private void dfs(int u, int c) {
            if(bad) return;
            co[u] = c;
            if(c == 0)
                ecount++;
            else
                fcount++;
            //noinspection Convert2streamapi
            for(int v: enemies[u]) {
                if(co[v] == -1)
                    dfs(v, 1-c);
                else if(co[v] == c)
                    bad = true;
            }
        }
    }
}