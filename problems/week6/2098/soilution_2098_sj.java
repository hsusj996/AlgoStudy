import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class soilution_2098_sj {
    static final int INF = 1_000_000_000;

    static int N;
    static int[][] graph;
    static StringTokenizer st = null;
    static int[][] dp;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        graph = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new int[N + 1][1 << N];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dp[i], -1);
        }

        System.out.println(Solve(1, 1));
    }

    private static int Solve(int cur, int bitState) {
        if (bitState == (1 << N) - 1) {
            if (graph[cur][1] == 0) {
                return INF;
            } else {
                return graph[cur][1];
            }
        }

        if (dp[cur][bitState] != -1) {
            return dp[cur][bitState];
        }
        dp[cur][bitState] = INF;

        for (int i = 1; i <= N; i++) {
            if ((bitState & (1 << (i - 1))) == 0 && graph[cur][i] != 0) {
                dp[cur][bitState] = Math.min(Solve(i, bitState | (1 << (i - 1))) + graph[cur][i], dp[cur][bitState]);
            }
        }
        return dp[cur][bitState];
    }

}