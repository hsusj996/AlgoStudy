package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class prob11049_2 {
  static final int INF = 1_000_000_000;
  static StringTokenizer st = null;
  static int[][] data;
  static int N;
  static int[][] dp;

  public static void main(String[] args) throws NumberFormatException, IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    N = Integer.parseInt(br.readLine());

    dp = new int[N + 1][N + 1];
    data = new int[N + 1][2];
    for (int i = 1; i <= N; i++) {
      st = new StringTokenizer(br.readLine());
      int r = Integer.parseInt(st.nextToken());
      int c = Integer.parseInt(st.nextToken());

      data[i][0] = r;
      data[i][1] = c;
    }

    for (int i = 1; i <= N; i++) {
      for (int j = i - 1; j >= 1; j--) {
        dp[j][i] = INF;
        for (int k = j; k < i; k++) {
          int value = dp[j][k] + dp[k + 1][i] + (data[j][0] * data[k][1] * data[i][1]);
          dp[j][i] = Math.min(dp[j][i], value);
        }
      }
    }

    System.out.println(dp[1][N]);
  }
}
