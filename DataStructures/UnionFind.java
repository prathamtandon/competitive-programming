public class UnionFind {
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
