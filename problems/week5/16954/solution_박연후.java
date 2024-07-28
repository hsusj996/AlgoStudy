import java.io.*;
import java.util.*;

public class Main {

    static final int SIZE = 8;

    static StringBuilder sb;
    static StringTokenizer st;

    static int ans;

    static int[] dx = {0, 1, -1, 0, 0, 1, 1, -1, -1};
    static int[] dy = {0, 0, 0, 1, -1, 1, -1, 1, -1};


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[][] arr = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            String s = br.readLine();
            arr[i] = s.toCharArray();
        }

        dfs(7, 0, arr, new boolean[SIZE][SIZE]);

        System.out.println(ans);
    }

    static void dfs(int x, int y, char[][] arr, boolean[][] visited) {
        if (ans == 1) {
            return;
        }
        if (x == 0 && y == 7) {
            ans = 1;
            return;
        }

        char[][] downArr = down(arr);

        boolean[][] nowVisit = new boolean[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            nowVisit[i] = Arrays.copyOf(visited[i], visited[i].length);
        }

        for (int i = 0; i < 9; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx < 0 || ny < 0 || nx >= SIZE || ny >= SIZE || visited[nx][ny]) {
                continue;
            }
            if (arr[nx][ny] == '.' && downArr[nx][ny] != '#') {
                nowVisit[nx][ny] = true;
                dfs(nx, ny, downArr, nowVisit);
                nowVisit[nx][ny] = false;
            }
        }

    }

    static char[][] down(char[][] arr) {
        char[][] downArr = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            downArr[i] = Arrays.copyOf(arr[i], arr[i].length);
        }

        for (int i = 0; i < 8; i++) {
            Queue<Character> q = new ArrayDeque<>();
            for (int j = SIZE - 2; j >= 0; j--) {
                q.add(downArr[j][i]);
            }
            for (int j = SIZE - 1; j >= 1; j--) {
                downArr[j][i] = q.poll();
            }
            downArr[0][i] = '.';
        }

        return downArr;
    }


}