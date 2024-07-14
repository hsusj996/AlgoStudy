package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Solution_1981_MW {

    public static class Pair {
        int y, x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static int n;
    static int[][] board;
    static boolean[][] vis;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};
    static int ans = Integer.MAX_VALUE;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        board = new int[n][n];
        int mx = 0;
        int mn = Integer.MAX_VALUE;
        for(int i=0; i<n; i++) {
            String[] s = br.readLine().split(" ");
            for(int j=0; j<n; j++) {
                board[i][j] = Integer.parseInt(s[j]);
                mx = Math.max(mx, board[i][j]);
                mn = Math.min(mn, board[i][j]);
            }
        }

        int st = 0;
        int en = mx-mn;
        while(st <= en) {
            boolean isFinish = false;
            int offset = (st + en) / 2;
            for(int i=mn; i+offset<=mx; i++) {

                if(board[0][0] < i || board[0][0] > i+offset) continue;

                Queue<Pair> q = new ArrayDeque<>();
                q.offer(new Pair(0, 0));
                vis = new boolean[n][n];
                vis[0][0] = true;

                while(!q.isEmpty()) {
                    Pair cur = q.poll();

//                    System.out.println(cur.y + " " + cur.x);

                    if(cur.y == n-1 && cur.x == n-1) {
                        isFinish = true;
                        break;
                    }

                    for(int dir=0; dir<4; dir++) {
                        int ny = cur.y + dy[dir];
                        int nx = cur.x + dx[dir];
                        if(ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
                        if(vis[ny][nx] || board[ny][nx] > i+offset || board[ny][nx] < i) continue;
                        q.offer(new Pair(ny, nx));
                        vis[ny][nx] = true;
                    }
                }

                if(isFinish) {
                    break;
                }
            }
            if(isFinish) {
                en = offset - 1;
                ans = Math.min(ans, offset);
            } else {
                st = offset + 1;
            }
        }

        System.out.println(ans);
    }
}