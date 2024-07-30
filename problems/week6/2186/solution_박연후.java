import java.io.*;
import java.util.*;

public class Main {

    static StringBuilder sb;
    static StringTokenizer st;

    static int n, m, k, ans;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static char[] s;

    static char[][] arr;

    static int[][][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        arr = new char[n][m];

        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            arr[i] = s.toCharArray();
        }

        s = br.readLine().toCharArray();
        dp = new int[n][m][s.length + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (s[0] == arr[i][j]) {
                    ans += dfs(i, j, 1);
                }
            }
        }

        System.out.println(ans);
    }

    static int dfs(int x, int y, int idx) {
        if (dp[x][y][idx] != -1) {
            return dp[x][y][idx];
        }
        if (idx == s.length) {
            dp[x][y][idx] = 1;
            return dp[x][y][idx];
        }
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= k; j++) {
                int nx = x + dx[i] * j;
                int ny = y + dy[i] * j;
                if (nx < 0 || ny < 0 || nx >= n || ny >= m) {
                    continue;
                }
                if (arr[nx][ny] == s[idx]) {
                    cnt += dfs(nx, ny, idx + 1);
                }
            }
        }
        dp[x][y][idx] = cnt;

        return cnt;
    }
}