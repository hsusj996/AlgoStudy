import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution_2186_MW {

    public static class Pair {
        int y, x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static int n, m, k;
    static char[][] board;
    static String word;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};
    static int[][][] d;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] s = br.readLine().split(" ");

        n = Integer.parseInt(s[0]);
        m = Integer.parseInt(s[1]);
        k = Integer.parseInt(s[2]);
        board = new char[n][m];

        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                board[i][j] = line.charAt(j);
            }
        }

        word = br.readLine();
        d = new int[n][m][word.length()]; // d[i][j]: (i, j)에서 만들 수 있는 경로의 개수
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                Arrays.fill(d[i][j], -1);
            }
        }

        int ans = 0;

        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(board[i][j] == word.charAt(0)) {
                    ans += dfs(i, j, 1);
                }
            }
        }
        System.out.println(ans);
    }

    private static int dfs(int i, int j, int idx) {

        if(idx == word.length()) {
            return 1;
        }

        if(d[i][j][idx] != -1) {
            return d[i][j][idx];
        }

        d[i][j][idx] = 0;

        for(int dist=1; dist<=k; dist++) {
            for(int dir=0; dir<4; dir++) {
                int ny = i + dy[dir] * dist;
                int nx = j + dx[dir] * dist;

                if(ny < 0 || ny >= n || nx < 0 || nx >= m)  continue;
                if(board[ny][nx] != word.charAt(idx))   continue;

                d[i][j][idx] += dfs(ny, nx, idx+1);
            }
        }
        return d[i][j][idx];
    }
}
