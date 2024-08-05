package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Solution_16946_MW {

    public static class Pair {
        public int y, x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static int n, m;
    static int[][] board;
    static boolean[] vis;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};
    static int num = 1;
    static int[] map;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        n = Integer.parseInt(s[0]);
        m = Integer.parseInt(s[1]);

        board = new int[n][m];
        map = new int[n*m+1];
        for(int i=0; i<n; i++) {
            String line = br.readLine();
            for(int j=0; j<m; j++) {
                if(line.charAt(j) == '0') {
                    board[i][j] = 0; // 빈 칸
                } else {
                    board[i][j] = -1; // 벽
                }
            }
        }

        // 1. 인접한 0의 지점에 번호 매기기
        // 2. board 순회하면서 각 지점 번호의 개수 세기 map[num]++
        // 3. 벽인 자리 인접한 섬이 있으면 개수 더해주기

        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(board[i][j] == 0) {
                    bfs(i, j);
                    num++;
                }
            }
        }

        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(board[i][j] != -1) {
                    sb.append(0);
                    continue;
                }

                vis = new boolean[num + 1];
                int movableCount = 1;
                for(int dir=0; dir<4; dir++) {
                    int ny = i + dy[dir];
                    int nx = j + dx[dir];

                    if(ny < 0 || ny >= n || nx < 0 || nx >= m)  continue;
                    if(board[ny][nx] == -1) continue;
                    int landNum = board[ny][nx];
                    if(vis[landNum])    continue;
                    vis[landNum] = true;
                    movableCount += map[landNum];
                }
                sb.append(movableCount % 10);
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    private static void bfs(int i, int j) {

        Queue<Pair> q = new ArrayDeque<>();
        q.offer(new Pair(i, j));
        board[i][j] = num;
        int cnt = 1;

        while(!q.isEmpty()) {
            Pair cur = q.poll();

            for(int dir=0; dir<4; dir++) {
                int ny = cur.y + dy[dir];
                int nx = cur.x + dx[dir];

                if(ny < 0 || ny >= n || nx < 0 || nx >= m)  continue;
                if(board[ny][nx] != 0) continue;

                q.offer(new Pair(ny, nx));
                board[ny][nx] = num;
                cnt++;
            }
        }

        map[num] = cnt;
    }
}