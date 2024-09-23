import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solution_14002_MW {

    static int n;
    static int[] a;
    static int[] pre;
    static int[] d;
    static List<Integer> subSequence = new ArrayList<>();
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        a = new int[n];
        String[] s = br.readLine().split(" ");
        for(int i=0; i<n; i++) {
            a[i] = Integer.parseInt(s[i]);
        }
        pre = new int[n];
        Arrays.fill(pre, -1);
        d = new int[n];

        for(int i=0; i<n; i++) {
            d[i] = 1;
            for(int j=0; j<i; j++) {
                if(a[i] > a[j] && d[i] < d[j]+1) {
                    pre[i] = j;
                    d[i] = d[j]+1;
//                    System.out.println(i + " " + pre[i]);
                }
            }
        }

        int lastIdx = n-1;
        for(int i=n-1; i>=0; i--) {
            if(pre[i] != -1 && d[i] > d[lastIdx]) {
                lastIdx = i;
            }
        }

        System.out.println(d[lastIdx]);
        sub_sequence(lastIdx);

        Collections.sort(subSequence);

        for (Integer num : subSequence) {
            System.out.print(num + " ");
        }
    }

    public static void sub_sequence(int n) {

        if(n == -1) return;

        subSequence.add(a[n]);
        sub_sequence(pre[n]);
    }
}
