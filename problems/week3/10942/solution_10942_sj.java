package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class prob10942_2 {
    static StringBuilder sb = new StringBuilder();
    static int[][] dp;
    static int N, M;
    static int[] arr;
    static StringTokenizer st = null;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        dp = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dp[i], -1);
        }
        arr = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            String[] s = br.readLine().split(" ");
            int a = Integer.parseInt(s[0]);
            int b = Integer.parseInt(s[1]);

            sb.append(solve(a, b)).append("\n");
        }

        System.out.println(sb.toString());
    }

    private static int solve(int a, int b) {
        if (dp[a][b] != -1) {
            return dp[a][b];
        }

        if (a == b) {
            return dp[a][b] = 1;
        }

        if (a + 1 == b) {
            if (arr[a] == arr[b]) {
                return dp[a][b] = 1;
            }
        }

        if (arr[a] != arr[b]) {
            return dp[a][b] = 0;
        } else {
            return dp[a][b] = solve(a + 1, b - 1);
        }
    }
}
