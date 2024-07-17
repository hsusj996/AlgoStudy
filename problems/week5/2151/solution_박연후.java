import java.io.*;
import java.util.*;

public class Main {

    static StringTokenizer st;
    static StringBuilder sb;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static class Pos {

        int x;
        int y;
        int d;

        public Pos(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        char[][] arr = new char[n][n];
        Pos[] door = new Pos[2];
        int idx = 0;
        int ans = Integer.MAX_VALUE;

        int[][][] mirrors = new int[4][n][n];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(mirrors[i][j], n * n);
            }
        }

        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < n; j++) {
                arr[i][j] = s.charAt(j);
                if (arr[i][j] == '#') {
                    door[idx++] = new Pos(i, j, 0);
                }
            }
        }

        Queue<Pos> q = new ArrayDeque<>();
        for (int i = 0; i < 4; i++) {
            q.add(new Pos(door[0].x, door[0].y, i));
            mirrors[i][door[0].x][door[0].y] = 0;
        }

        while (!q.isEmpty()) {
            Pos p = q.poll();
            int x = p.x;
            int y = p.y;
            int d = p.d;

            int nx = x + dx[d];
            int ny = y + dy[d];
            if (nx < 0 || ny < 0 || nx >= n || ny >= n || arr[nx][ny] == '*') {
                continue;
            }
            if (mirrors[d][x][y] > mirrors[d][nx][ny]) {
                continue;
            }
            if (nx == door[1].x && ny == door[1].y) {
                mirrors[d][nx][ny] = Math.min(mirrors[d][nx][ny], mirrors[d][x][y]);
                ans = Math.min(ans, mirrors[d][nx][ny]);
            }
            mirrors[d][nx][ny] = Math.min(mirrors[d][nx][ny], mirrors[d][x][y]);
            q.offer(new Pos(nx, ny, d));
            if (arr[nx][ny] == '!') {
                if (d == 0 || d == 1) {
                    turn(mirrors, x, y, d, nx, ny, 2, q);
                    turn(mirrors, x, y, d, nx, ny, 3, q);
                }
                if (d == 2 || d == 3) {
                    turn(mirrors, x, y, d, nx, ny, 0, q);
                    turn(mirrors, x, y, d, nx, ny, 1, q);
                }
            }

        }

        System.out.println(ans);
    }

    private static void turn(int[][][] mirrors, int x, int y, int d, int nx, int ny, int nd,
        Queue<Pos> q) {
        if (mirrors[d][x][y] + 1 <= mirrors[nd][nx][ny]) {
            q.offer(new Pos(nx, ny, nd));
            mirrors[nd][nx][ny] = mirrors[d][x][y] + 1;
        }
    }

}