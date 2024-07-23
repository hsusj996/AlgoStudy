package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_11404 {
    public static void main(String[] args) throws IOException {
        int N, M;
        int[][] DP;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(in.readLine());
        M = Integer.parseInt(in.readLine());
        DP = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == j)
                    DP[i][j] = 0;
                else
                    DP[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int low, high, val;
            low = Integer.parseInt(st.nextToken());
            high = Integer.parseInt(st.nextToken());
            val = Integer.parseInt(st.nextToken());
            if (DP[low][high] > val) {
                DP[low][high] = val;
            }
        }
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (DP[i][k] != Integer.MAX_VALUE && DP[k][j] != Integer.MAX_VALUE
                            && DP[i][j] > DP[i][k] + DP[k][j])
                        DP[i][j] = DP[i][k] + DP[k][j];
                }
            }
        }
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (DP[i][j] == Integer.MAX_VALUE)
                    System.out.print("0 ");
                else
                    System.out.print(DP[i][j] + " ");
            }
            System.out.println();
        }
    }
}
