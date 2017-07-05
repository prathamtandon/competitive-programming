public class FenwickTreeDriver {
    public static void main(String[] args) {
        int[] f = {2, 4, 5, 5, 6, 6, 6, 7, 7, 8, 9}; // n = 10 and m (number of entries) = 11.
        // values: 1 2 3 4 5 6 7 8 9 10
        // cf = [0, 1, 1, 2, 4, 7, 9, 10, 11, 11]
        FenwickTree ft = new FenwickTree(10);
        for (int i = 0; i < 11; i++) {
            ft.adjust(f[i], 1);
        }

        // RSQ(1, b) is like asking: What is cumulative frequency of items from 1 through b (inclusive)
        System.out.println(ft.rsq(1, 1));
        System.out.println(ft.rsq(1, 2));
        System.out.println(ft.rsq(1, 6)); // ft[6] + ft[4]
        System.out.println(ft.rsq(1, 10)); // ft[8] + ft[10]
        System.out.println(ft.rsq(3, 6)); // rsq(1, 6) - rsq(1, 2)
        ft.adjust(5, 2); // Increment frequency of 5 by 2.
        System.out.println(ft.rsq(1, 10));
    }
}
