package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class prob7579 {
    static StringTokenizer st = null;
    static int N, M;
    static int[] cost;
    static int[] memory;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        memory = new int[N + 1];
        cost = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            memory[i] = Integer.parseInt(st.nextToken());

        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            cost[i] = Integer.parseInt(st.nextToken());
        }

        dp = new int[N + 1][10001];
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= 10000; j++) {
                if (j < cost[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - cost[i]] + memory[i]);
                }
            }
        }

        for (int i = 0; i <= 10000; i++) {
            if (dp[N][i] >= M) {
                System.out.println(i);
                break;
            }
        }
    }
}