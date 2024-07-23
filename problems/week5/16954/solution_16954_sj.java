package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class prob16954 {

    static final int[][] d = { { 0, -1, -1, -1, 0, 1, 1, 1, 0 }, { 0, -1, 0, 1, 1, 1, 0, -1, -1 } };
    static final int WALL = 1;
    static final int GND = 0;

    static class xy {
        int x;
        int y;

        public xy(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    static int[][] board = new int[8][8];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 8; i++) {
            String s = br.readLine();
            for (int j = 0; j < 8; j++) {
                char c = s.charAt(j);
                switch (c) {
                    case '.':
                        board[i][j] = GND;
                        break;
                    case '#':
                        board[i][j] = WALL;
                        break;
                    default:
                        break;
                }
            }
        }

        System.out.println(bfs() ? 1 : 0);
    }

    private static boolean bfs() {
        Queue<xy> q = new ArrayDeque<>();
        boolean[][][] visited = new boolean[1001][8][8];
        q.add(new xy(7, 0));
        visited[0][7][0] = true;
        int depth = 0;

        while (q.size() > 0) {
            // PrintForDebug();

            depth++;
            int qsize = q.size();

            while (qsize-- > 0) {
                xy cur = q.poll();

                if (board[cur.x][cur.y] == WALL) {
                    continue;
                }

                for (int i = 0; i < 9; i++) {
                    int nx = cur.x + d[0][i];
                    int ny = cur.y + d[1][i];

                    if (IsOutBound(nx, ny) || visited[depth][nx][ny] || board[nx][ny] == WALL) {
                        continue;
                    }

                    if (nx == 0 && ny == 7) {
                        return true;
                    }

                    q.add(new xy(nx, ny));
                    visited[depth][nx][ny] = true;
                }
            }

            boardUpdate();
        }

        return false;
    }

    private static void PrintForDebug() {
        System.out.println("================");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == WALL) {
                    System.out.print('#');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
    }

    private static void boardUpdate() {
        int[][] newBoard = new int[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == WALL && i < 7) {
                    newBoard[i + 1][j] = WALL;
                }
            }
        }

        board = newBoard;
    }

    private static boolean IsOutBound(int nx, int ny) {
        return nx < 0 || ny < 0 || nx >= 8 || ny >= 8;
    }

}
