package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import java.awt.Point;

public class prob2931_2 {
    static final int[][] d = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };
    static char[][] board;
    static int N, M;
    static StringTokenizer st = null;
    static Point start;
    static Point ans;
    static char ansPipe;
    static int initDir;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new char[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            String s = br.readLine();
            for (int j = 1; j <= M; j++) {
                board[i][j] = s.charAt(j - 1);
                if (board[i][j] == 'M') {
                    start = new Point(i, j);
                }
            }
        }

        initDir = -1;
        for (int i = 0; i < 4; i++) {
            int nx = start.x + d[0][i];
            int ny = start.y + d[1][i];

            if (IsOutBound(nx, ny)) {
                continue;
            }
            if (board[nx][ny] != '.') {
                initDir = i;
            }
        }

        CheckBoard();
        System.out.println(ans.x + " " + ans.y + " " + ansPipe);
    }

    private static boolean CheckBoard() {
        Point cur = new Point(start.x, start.y);
        int dir = initDir;

        while (true) {
            int nx = cur.x + d[0][dir];
            int ny = cur.y + d[1][dir];
            if (IsOutBound(nx, ny)) {
                return false;
            }

            if (board[nx][ny] == '.') {
                ans = new Point(nx, ny);
                ansPipe = CheckPipe(nx, ny);
                return false;
            } else if (board[nx][ny] == 'Z') {
                return true;
            } else {
                cur.x = nx;
                cur.y = ny;
                int newDir = GetDirByPipe(nx, ny, dir);
                if (newDir == -1) {
                    return false;
                } else {
                    dir = newDir;
                }
            }
        }
    }

    private static boolean CheckBoard2() {
        Point cur = new Point(start.x, start.y);
        int dir = initDir;

        while (true) {
            int nx = cur.x + d[0][dir];
            int ny = cur.y + d[1][dir];
            if (IsOutBound(nx, ny)) {
                return false;
            }

            if (board[nx][ny] == 'Z') {
                return true;
            } else {
                cur.x = nx;
                cur.y = ny;
                int newDir = GetDirByPipe(nx, ny, dir);
                if (newDir == -1) {
                    return false;
                } else {
                    dir = newDir;
                }
            }
        }
    }

    private static char CheckPipe(int x, int y) {
        board[x][y] = '|';
        if (CheckBoard2())
            return '|';
        board[x][y] = '-';
        if (CheckBoard2())
            return '-';
        board[x][y] = '+';
        if (CheckBoard2())
            return '+';
        board[x][y] = '1';
        if (CheckBoard2())
            return '1';
        board[x][y] = '2';
        if (CheckBoard2())
            return '2';
        board[x][y] = '3';
        if (CheckBoard2())
            return '3';
        board[x][y] = '4';
        if (CheckBoard2())
            return '4';

        return '.';
    }

    private static int GetDirByPipe(int x, int y, int dir) {
        char pipe = board[x][y];

        if (pipe == '+') {
            return dir;
        }

        if (dir == 0) {
            if (pipe == '1')
                dir = 1;
            else if (pipe == '4')
                dir = 3;
            else if (pipe == '-' || pipe == '2' || pipe == '3')
                dir = -1;
        } else if (dir == 1) {
            if (pipe == '3')
                dir = 0;
            else if (pipe == '4')
                dir = 2;
            else if (pipe == '|' || pipe == '1' || pipe == '2')
                dir = -1;

        } else if (dir == 2) {
            if (pipe == '2')
                dir = 1;
            else if (pipe == '3')
                dir = 3;
            else if (pipe == '-' || pipe == '1' || pipe == '4')
                dir = -1;

        } else if (dir == 3) {
            if (pipe == '1')
                dir = 2;
            else if (pipe == '2')
                dir = 0;
            else if (pipe == '|' || pipe == '3' || pipe == '4')
                dir = -1;

        }

        return dir;
    }

    private static boolean IsOutBound(int nx, int ny) {
        return nx <= 0 || ny <= 0 || nx > N || ny > M;
    }
}
