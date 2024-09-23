import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int N;
    static int K;
    static char[][] arr;
    static long[][] dp;
    static int[][] dpRemain;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new char[N][];
        for (int i = 0; i < N; i++) {
            arr[i] = br.readLine().toCharArray();
        }
        K = Integer.parseInt(br.readLine());
        dp = new long[K][1 << N];
        dpRemain = new int[K][N];

        for (int i = 0; i < K; i++) {
            Arrays.fill(dp[i], -1);
            Arrays.fill(dpRemain[i], -1);
        }

        long p = dfs(0, 0, 0);
        long q = fact();

        if (p == 0) {
            q = 1;
        } else {
            long gcd = GCD(q, p);
            p /= gcd;
            q /= gcd;
        }

        System.out.println(p + "/" + q);
    }

    private static long dfs(int bitState, int remain, int cnt) {
        if (dp[remain][bitState] != -1) {
            return dp[remain][bitState];
        }

        if (cnt == N) {
            return dp[remain][bitState] = remain == 0 ? 1L : 0;
        }

        long sum = 0;
        for (int i = 0; i < N; i++) {
            if ((bitState & (1 << i)) != 1 << i) {
                sum += dfs(bitState | (1 << i), getMod(remain, i), cnt + 1);
            }
        }

        return dp[remain][bitState] = sum;
    }

    private static int getMod(int remain, int n) {
        if (dpRemain[remain][n] != -1) {
            return dpRemain[remain][n];
        }

        int now = remain;
        for (int i = 0; i < arr[n].length; i++) {
            now *= 10;
            now = (now + arr[n][i] - '0') % K;
        }

        return dpRemain[remain][n] = now;
    }

    private static long GCD(long m, long n) {
        while (m % n != 0) {
            long temp = m % n;
            m = n;
            n = temp;
        }
        return n;
    }

    private static long fact() {
        long ret = 1;
        for (int i = 2; i <= N; i++) {
            ret *= i;
        }
        return ret;
    }
}