package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class prob1981_2 {
    static final int[][] d = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };

    static class xy {
        int x;
        int y;

        public xy(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    static int N;
    static int[][] board;
    static int[] arr;
    static StringTokenizer st = null;
    static int min = 200, max = 0;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                min = Math.min(min, board[i][j]);
                max = Math.max(max, board[i][j]);
            }
        }

        System.out.println(BinarySearch());
    }

    private static int BinarySearch() {
        int start = 0;
        int end = max - min;
        int ret = 1_000_000_000;

        while (start <= end) {
            int mid = (start + end) / 2;
            boolean flag = false;
            for (int i = min; i <= max; i++) {
                if (i <= board[0][0] && board[0][0] <= i + mid) {
                    if (bfs(i, i + mid)) {
                        flag = true;
                        break;
                    }
                }
            }

            if (flag) {
                end = mid - 1;
                ret = Math.min(ret, mid);
            } else {
                start = mid + 1;
            }
        }

        return ret;
    }

    private static boolean bfs(int lower, int upper) {
        boolean[][] visited = new boolean[N][N];
        Queue<xy> q = new ArrayDeque<>();
        q.add(new xy(0, 0));
        visited[0][0] = true;

        while (!q.isEmpty()) {
            xy cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + d[0][i];
                int ny = cur.y + d[1][i];

                if (IsOutBound(nx, ny) || visited[nx][ny]) {
                    continue;
                }

                if (board[nx][ny] < lower || board[nx][ny] > upper) {
                    continue;
                }

                if (nx == N - 1 && ny == N - 1) {
                    return true;
                }

                q.add(new xy(nx, ny));
                visited[nx][ny] = true;
            }
        }

        return false;
    }

    private static boolean IsOutBound(int nx, int ny) {
        return nx < 0 || nx >= N || ny < 0 || ny >= N;
    }
}
