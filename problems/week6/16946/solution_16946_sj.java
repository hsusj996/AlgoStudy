import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static final int[][] d = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };

    static class xy {
        int x;
        int y;

        public xy(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof xy)) {
                return false;
            }

            xy o = (xy) obj;
            return this.x == o.x && this.y == o.y;
        }

    }

    static int N, M;
    static int[][] board;
    static StringTokenizer st = null;
    static xy[][] parents;
    static boolean[][] visited;
    static HashMap<Integer, Integer> map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new HashMap<>();
        board = new int[N][M];
        parents = new xy[N][M];
        visited = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                parents[i][j] = new xy(i, j);
            }
        }

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = s.charAt(j) - '0';
            }
        }

        FloodFill();

        Print(Solve());
    }

    private static void Print(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    private static int[][] Solve() {
        int[][] ret = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] != 1) {
                    continue;
                }

                int sum = 0;
                HashSet<Integer> set = new HashSet<>();
                for (int k = 0; k < 4; k++) {
                    int nx = i + d[0][k];
                    int ny = j + d[1][k];

                    if (IsOutBound(nx, ny) || board[nx][ny] == 1) {
                        continue;
                    }

                    int idx = board[nx][ny];
                    if (!set.contains(idx)) {
                        set.add(idx);
                        sum += map.get(idx);
                    }
                }

                ret[i][j] = (sum + 1) % 10;
            }
        }

        return ret;
    }

    private static void FloodFill() {
        int idx = 2;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (visited[i][j] || board[i][j] == 1) {
                    continue;
                }
                bfs(i, j, idx++);
            }
        }
    }

    private static void bfs(int r, int c, int idx) {
        int cnt = 0;
        Queue<xy> q = new ArrayDeque<>();
        q.add(new xy(r, c));
        visited[r][c] = true;
        board[r][c] = idx;
        cnt++;

        while (!q.isEmpty()) {
            xy cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + d[0][i];
                int ny = cur.y + d[1][i];

                if (IsOutBound(nx, ny) || visited[nx][ny] || board[nx][ny] != 0) {
                    continue;
                }

                xy next = new xy(nx, ny);
                if (Union(next, cur)) {
                    cnt++;
                    q.add(next);
                    board[nx][ny] = idx;
                    visited[nx][ny] = true;
                }
            }
        }

        map.put(idx, cnt);
    }

    private static boolean Union(xy a, xy b) {
        xy aRoot = Find(a);
        xy bRoot = Find(b);

        if (aRoot.equals(bRoot)) {
            return false;
        }

        parents[b.x][b.y] = aRoot;
        return true;
    }

    private static xy Find(xy a) {
        if (a.equals(parents[a.x][a.y])) {
            return a;
        }

        return parents[a.x][a.y] = Find(parents[a.x][a.y]);
    }

    private static boolean IsOutBound(int nx, int ny) {
        return nx < 0 || ny < 0 || nx >= N || ny >= M;
    }
}