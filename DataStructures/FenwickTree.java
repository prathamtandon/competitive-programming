
public class FenwickTree {
    int[] ft;

    FenwickTree(int n) {
        ft = new int[n+1]; // For values in range 1...n ie [0, 1, 2, 3, ..., n]
    }

    /**
     * Returns sum of values in ft from index 1 through b inclusive.
     * ft[b] stores sum of values from ft[b-LSOne[b]+1 .. b]. So, to find Range Sum Query from 1...b,
     * we have to add values ft[b], ft[b'], ft[b''] until b becomes 0. Here, b' = b - LSOne(b),
     * b'' = b' - LSOne(b') and so on.
     * @param b
     * @return
     */
    int rsq(int b) {
        int sum = 0;
        for(; b > 0; b -= LSOne(b))
            sum += ft[b];
        return sum;
    }

    /**
     * Returns the sum of values in ft from index a through b inclusive.
     * @param a
     * @param b
     * @return
     */
    int rsq(int a, int b) {
        return rsq(b) - (a == 1 ? 0 : rsq(a - 1));
    }

    /**
     * Returns the first Least Significant One-bit in k.
     * @param k
     * @return
     */
    private int LSOne(int k) {
        return k & -k;
    }

    /**
     * Modifies(INC or DEC based on sign of v) the value at ft[k] by v. Since, ft[k] is used to compute
     * values to its right as well, we must modify those values too. So, in essence, we modify ft[k], ft[k'], ft[k'']
     * and so on where k' = k + LSOne(k), k'' = k' + LSOne(k') and so on until k becomes n+1.
     * @param k
     * @param v
     */
    void adjust(int k, int v) {
        for(; k < ft.length; k += LSOne(k))
            ft[k] += v;
    }

}