public class SegmentTree {
    private int[] tree;
    private enum Codes {
        RANGE_SUM,
        RANGE_MIN,
        RANGE_MAX
    }
    public void initSegmentTree(int N) {
        int tsize = (int) (2 * Math.pow(2.0, Math.log(Math.floor((double)N)) + 1));
        tree = new int[tsize];
    }

    public void buildTree(int code, int node, int[] A,int b, int e) {
        if(b == e) {
            if(code == Codes.RANGE_SUM.ordinal())
                this.tree[node] = A[b];
            else
                this.tree[node] = b;
        } else {
            int leftIndex = 2 * node;
            int rightIndex = 2 * node + 1;
            buildTree(code, leftIndex, A, b, (b + e) / 2);
            buildTree(code, rightIndex, A, (b + e) / 2 + 1, e);
            if(code == Codes.RANGE_SUM.ordinal()) {
                this.tree[node] = this.tree[leftIndex] + this.tree[rightIndex];
            } else if(code == Codes.RANGE_MAX.ordinal()) {
                this.tree[node] = A[this.tree[leftIndex]] >= A[this.tree[rightIndex]] ? this.tree[leftIndex] : this
                        .tree[rightIndex];
            } else {
                this.tree[node] = A[this.tree[leftIndex]] <= A[this.tree[rightIndex]] ? this.tree[leftIndex] : this
                        .tree[rightIndex];
            }
        }
    }

    public int query(int code, int node, int[] A, int b, int e, int i, int j) {
        if(i > e || j < b)
            return -1;
        if(i <= b && j >= e)
            return this.tree[node];
        int p1 = query(code, 2 * node, A, b, (b + e) / 2, i , j);
        int p2 = query(code, 2 * node + 1, A, (b + e) / 2 + 1, e, i , j);
        if(p1 == -1) return p2;
        if(p2 == -1) return p1;
        if(code == Codes.RANGE_SUM.ordinal())
            return p1 + p2;
        else if(code == Codes.RANGE_MIN.ordinal())
            return A[p1] <= A[p2] ? p1 : p2;
        else
            return A[p1] >= A[p2] ? p1 : p2;
    }

    public void update(int code, int node, int i, int A[], int b, int e) {
        if(b == e && b == i) {
            if(code == Codes.RANGE_SUM.ordinal())
                this.tree[node] = A[i];
            updateParent(code, node, A);
        } else {
            if(i < b || i > e)
                return;
            update(code, 2 * node, i, A, b, (b + e) / 2);
            update(code, 2 * node + 1, i, A, (b + e) / 2 + 1, e);
        }
    }

    private void updateParent(int code, int node, int[] A) {
        if(node == 1)
            return;
        int parent = node / 2;
        int leftIndex = 2 * parent;
        int rightIndex = 2 * parent + 1;
        if(code == Codes.RANGE_SUM.ordinal()) {
            this.tree[parent] = this.tree[leftIndex] + this.tree[rightIndex];
        } else {
            int leftContent = A[this.tree[leftIndex]];
            int rightContent = A[this.tree[rightIndex]];
            if(code == Codes.RANGE_MIN.ordinal())
                this.tree[parent] = leftContent <= rightContent ? this.tree[leftIndex] : this.tree[rightIndex];
            else
                this.tree[parent] = leftContent >= rightContent ? this.tree[leftIndex] : this.tree[rightIndex];
        }
        updateParent(code, parent, A);
    }

}