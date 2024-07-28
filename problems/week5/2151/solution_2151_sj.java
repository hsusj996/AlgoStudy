package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class prob2151 {
    static final int[][] d = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };

    static class xy {
        int x;
        int y;
        int prevDirection;
        int depth;

        public xy(int x, int y, int prevDirection, int depth) {
            this.x = x;
            this.y = y;
            this.prevDirection = prevDirection;
            this.depth = depth;
        }

    }

    static int N;
    static char[][] board;
    static xy start;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        board = new char[N][N];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < N; j++) {
                board[i][j] = s.charAt(j);
                if (board[i][j] == '#' && start == null) {
                    start = new xy(i, j, 0, 0);
                    board[i][j] = '.';
                }
            }
        }

        System.out.println(bfs());

    }

    private static int bfs() {
        boolean[][][] visited = new boolean[4][N][N];
        Deque<xy> dq = new ArrayDeque<>();
        for (int i = 0; i < 4; i++) {
            dq.add(new xy(start.x, start.y, i, 0));
            visited[i][start.x][start.y] = true;
        }

        while (!dq.isEmpty()) {
            xy cur = dq.poll();

            int nx = cur.x + d[0][cur.prevDirection];
            int ny = cur.y + d[1][cur.prevDirection];

            if (IsOutBound(nx, ny) || visited[cur.prevDirection][nx][ny] || board[nx][ny] == '*') {
                continue;
            }

            if (board[nx][ny] == '.') {
                visited[cur.prevDirection][nx][ny] = true;
                dq.addFirst(new xy(nx, ny, cur.prevDirection, cur.depth));
            } else if (board[nx][ny] == '!') {
                int d1 = (cur.prevDirection + 1) % 4;
                int d2 = (cur.prevDirection + 3) % 4;
                int d3 = cur.prevDirection;

                visited[d1][nx][ny] = true;
                dq.addLast(new xy(nx, ny, d1, cur.depth + 1));
                visited[d2][nx][ny] = true;
                dq.addLast(new xy(nx, ny, d2, cur.depth + 1));
                visited[d3][nx][ny] = true;
                dq.addFirst(new xy(nx, ny, d3, cur.depth));
            } else {
                return cur.depth;
            }
        }

        return -1;
    }

    private static boolean IsOutBound(int nx, int ny) {
        return nx < 0 || ny < 0 || nx >= N || ny >= N;
    }
}
