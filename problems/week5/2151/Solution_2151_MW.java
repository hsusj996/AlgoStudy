import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

import static java.util.Collections.fill;

public class Solution_2151_MW {

    static class Pair implements Comparable<Pair> {
        int y, x;
        int dir;
        int mirrorCnt;

        public Pair(int y, int x, int dir, int mirrorCnt) {
            this.y = y;
            this.x = x;
            this.dir = dir;
            this.mirrorCnt = mirrorCnt;
        }

        @Override
        public int compareTo(Pair o) {
            return this.mirrorCnt - o.mirrorCnt;
        }
    }

    static int n;
    static char[][] board;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};
    static int[][][] vis;
    static Pair dest;

    public static void main(String[] args) throws Exception {

        // 1. 처음 거울 시작 점에서 4방 탐색 시작
        // 2. 진행 방향에 거울이 있으면, 2가지 방향 고려해서 큐에 넣기
        // - 큐에는 위치 설치한 거울 개수, 빛 방향
        // 3. 설치한 거울의 개수로 다익스트라 돌면서 반대편 거울 발견하면 바로 종료!

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        PriorityQueue<Pair> pq = new PriorityQueue<>();

        board = new char[n][n];

        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < n; j++) {
                board[i][j] = s.charAt(j);
            }
        }

        vis = new int[n][n][4];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                Arrays.fill(vis[i][j], -1);
            }
        }
        boolean isFirst = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == '#') {
                    if (isFirst) {
                        for (int dir = 0; dir < 4; dir++) {
                            int ny = i + dy[dir];
                            int nx = j + dx[dir];

                            if (ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
                            if (board[ny][nx] == '*') continue;

                            if (board[ny][nx] == '!') {

                                pq.offer(new Pair(ny, nx, dir, 0));
                                vis[ny][nx][dir] = 0;

                                int newDir = (dir + 1) % 4;
                                pq.offer(new Pair(ny, nx, newDir, 1));
                                vis[ny][nx][newDir] = 1;
                                newDir = (dir + 4 - 1) % 4;
                                pq.offer(new Pair(ny, nx, newDir, 1));
                                vis[ny][nx][newDir] = 1;
                            } else {
                                pq.offer(new Pair(ny, nx, dir, 0));
                            }
                        }

                        isFirst = false;
                    } else {
                        dest = new Pair(i, j, -1, -1);
                    }
                }
            }
        }

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();
            int y = cur.y;
            int x = cur.x;
            int dir = cur.dir;
            int mirrorCnt = cur.mirrorCnt;

//            System.out.println(y + " " + x + " " + dir);

            if (y == dest.y && x == dest.x) {
                System.out.println(mirrorCnt);
                return;
            }

            int ny = y + dy[dir];
            int nx = x + dx[dir];

            if (ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
            if (board[ny][nx] == '*') continue;

            if (board[ny][nx] == '!') {

                if(vis[ny][nx][dir] == -1 || vis[ny][nx][dir] > mirrorCnt) { // (ny, nx)에 거울 설치하지 않는 경우
                    pq.offer(new Pair(ny, nx, dir, mirrorCnt));
                }

                int newDir = (dir + 1) % 4;
                if (vis[ny][nx][newDir] == -1 || vis[ny][nx][newDir] > mirrorCnt + 1) { // (ny, nx)에 거울을 설치하는 경우
                    // (ny, nx)에 처음 방문 하거나, 같은 좌표에 더 적은 거울 개수로 도착할 수 있을 때
                    pq.offer(new Pair(ny, nx, newDir, mirrorCnt + 1));
                    vis[ny][nx][newDir] = mirrorCnt + 1;
                }

                newDir = (dir + 4 - 1) % 4;
                if (vis[ny][nx][newDir] == -1 || vis[ny][nx][newDir] > mirrorCnt + 1) { // (ny, nx)에 거울을 설치하는 경우
                    // (ny, nx)에 처음 방문 하거나, 같은 좌표에 더 적은 거울 개수로 도착할 수 있을 때
                    pq.offer(new Pair(ny, nx, newDir, mirrorCnt + 1));
                    vis[ny][nx][newDir] = mirrorCnt + 1;
                }

            } else { // !가 아닌 경우
                if (vis[ny][nx][dir] != -1 && vis[ny][nx][dir] <= mirrorCnt) continue; // 같은 좌표에 더 적은 거울 개수로 방문한 적 있으면 pass

                pq.offer(new Pair(ny, nx, dir, mirrorCnt));
                vis[ny][nx][dir] = mirrorCnt;
            }
        }
    }
}
