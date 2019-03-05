package search;

import java.util.ArrayList;

//∈ ∀ ∃

public class BinarySearch {
    public static void main(String[] args) throws Exception {
        // x ∈ [-2^(31); 2^(31) - 1]
        int x = Integer.parseInt(args[0]);
        ArrayList<Integer> a = new ArrayList<>();
        // ∀i = 0...a.size a[i] ∈ [-2^(31); 2^(31) - 1]

        for (int i = 1; i < args.length; i++) {
            a.add(Integer.parseInt(args[i]));
        } //post: ∀i<j a[i] >= a[j]

        //pre: l' = 0, r' = a.size()
        int i = recursiveBinSearch(a, x, 0, a.size());
        //int i = binSearch(a,x);

        //post: i = min{index | a[index] <= x}
        System.out.println(i);

    }

    static int binSearch(ArrayList<Integer> a, int x) {
        //pre: (∀i<j a[i] >= a[j])
        int l = 0;
        //post:l = 0 ∧  l' = l ∧ (∀i<j a[i] >= a[j])
        int r = a.size();
        //post: l = 0 ∧  l' = l ∧ r = a.size ∧ r' = r ∧ (∀i<j a[i] >= a[j])

        //pre: (l' >= 0) ∧ (r' >= 0) ∧ l' = min(index)
        while (l < r) {
            // pre: (l' >= 0) ∧ (r' >= 0) ∧ (r' > l')
            int m = (l + r) / 2;
            //post: m' >= 0 ∧ (r' > l') ∧ (m' >= l') ∧ (m' < r')

            //pre: ∀i<j a[i] >= a[j] ∧ (m' >= l') ∧ (r' > l')
            if (a.get(m) > x) {
                //pre: ((∀i<j a[i] >= a[j]) ∧ (a[m'] > x) => ∀index ∈ [0;m'] a[index] > x
                l = m + 1;
                //post: (∀i<j a[i] >= a[j] ∧ a[l'] > x  || a[l'] <= x) => min{index | a[index] <= x} ∈ [l';r']
            } else {
                //pre:(∀i<j a[i] >= a[j]) ∧ (a[m'] <= x) ∧ (r' > l')
                r = m;
                //post: ((r' = m') => (a[r'] <= x)) ∧ min{index | a[index] <= x} ∈ [l';r']
            }
        }
        //post: (l >= r) ∧ (a[r'] <= x) => (a[l'] <= x)
        return l;
    }

    //pre: (l' < r') ∧ (∀i<j a[i] >= a[j]) ∧ l', r' ∈ [0...a.size - 1]  => (a[l'] >= x) ∧ a[r'] <= x
    static int recursiveBinSearch(ArrayList<Integer> a, int x, int l, int r) {
        if (l < r) {
            //pre: l' >= 0 ∧ r' >= 0
            int m = (l + r) / 2;
            //m' >= 0
            if (a.get(m) > x) {
                //pre: ((∀i<j a[i] >= a[j]) ∧ (a[m'] > x) => ∀index ∈ [0;m'] a[index] > x
                return recursiveBinSearch(a, x, m + 1, r);
            } else {
                //pre: (∀i<j a[i] >= a[j]) ∧ (a[m'] <= x) ∧ (r' > l')
                return recursiveBinSearch(a, x, l, m);
            }
        }
        //post: (l >= r) ∧ (a[r'] <= x) => (a[l'] <= x)
        return l;
    }
}
