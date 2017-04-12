import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q10369 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(reader.readLine());
        Network network;
        while (TC-- > 0) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int satellites = Integer.parseInt(tokenizer.nextToken());
            int posts = Integer.parseInt(tokenizer.nextToken());
            network = new Network(satellites, posts);
            while (posts-- > 0) {
                tokenizer = new StringTokenizer(reader.readLine());
                int x = Integer.parseInt(tokenizer.nextToken());
                int y = Integer.parseInt(tokenizer.nextToken());
                network.addPost(x, y);
            }
            System.out.println(String.format("%.2f", network.getMinCost()));
        }
    }

    private static class Network {
        private static int id;
        private int ns;
        private int np;
        private Post[] posts;
        private Edge[] edges;

        Network(int ns, int np) {
            id = 0;
            this.ns = ns;
            this.np = np;
            posts = new Post[np];
            edges = new Edge[np * np];
        }

        private static class UnionFind {
            private int[] pset;
            private int numSets;
            private int[] ssize;

            void initSet(int size) {
                numSets = size;
                pset = new int[size];
                ssize = new int[size];
                for (int i = 0; i < size; i++) {
                    pset[i] = i;
                    ssize[i] = 1;
                }
            }

            void unionSet(int i, int j) {
                int ri = findSet(i);
                int rj = findSet(j);
                if(ri == rj) return;
                if(ssize[ri] < ssize[rj]) {
                    pset[ri] = rj;
                    ssize[rj] += ssize[ri];
                } else {
                    pset[rj] = ri;
                    ssize[ri] += ssize[rj];
                }
                numSets--;
            }

            @SuppressWarnings("WeakerAccess")
            int findSet(int i) {
                if(pset[i] == i)
                    return i;
                pset[i] = findSet(pset[i]);
                return pset[i];
            }

            boolean isSameSet(int i, int j) {
                return findSet(i) == findSet(j);
            }

            int numberOfSets() {
                return numSets;
            }

            int sizeOfSet(int i) {
                return ssize[findSet(i)];
            }
        }


        private static class Post {
            int id;
            int x;
            int y;

            Post(int id, int x, int y) {
                this.id = id;
                this.x = x;
                this.y = y;
            }
        }

        private static class Edge implements Comparable<Edge> {
            int from;
            int to;
            double weight;

            Edge(int from, int to, double weight) {
                this.from = from;
                this.to = to;
                this.weight = weight;
            }

            @Override
            public int compareTo(Edge o) {
                return (int) Math.floor(weight - o.weight);
            }
        }

        void addPost(int x, int y) {
            int pid = id++;
            posts[pid] = new Post(pid, x, y);
        }

        double getMinCost() {
            createEdges();
            return createMST();
        }

        private void createEdges() {
            int k = 0;
            for (int i = 0; i < posts.length; i++) {
                int from = posts[i].id;
                for (int j = i+1; j < posts.length ; j++) {
                    int to = posts[j].id;
                    double weight = getWeight(posts[i].x, posts[j].x, posts[i].y, posts[j].y);
                    edges[k++] = new Edge(from, to, weight);
                }
            }
        }

        private double getWeight(int x1, int x2, int y1, int y2) {
            return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        }

        private double createMST() {
            int numEdges = (np - 1) * np / 2;
            Arrays.sort(edges, 0, numEdges);
            UnionFind uf = new UnionFind();
            /**
             * We start with 'np' disjoint sets, when we have 'ns' disjoint sets, we are done,
             * as they can be connected for free using the ns satellite channels. When disjoint
             * sets becomes 'ns', the last added edge weight is the value of D.
             */
            uf.initSet(np);
            for (int i = 0; i < numEdges; i++) {
                Edge e = edges[i];
                int u = e.from;
                int v = e.to;
                if(uf.findSet(u) == uf.findSet(v))
                    continue;
                uf.unionSet(u, v);
                if(uf.numberOfSets() == ns)
                    return e.weight;
            }
            return 0;
        }
    }
}