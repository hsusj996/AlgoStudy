import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solution_16954_MW {

    static class Pair implements Comparable<Pair> {
        int y, x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public int compareTo(Pair o) {
            return o.y - this.y;
        }
    }

    static char[][] board = new char[8][8];
    static boolean[][][] vis = new boolean[8][8][200];
    static int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
    public static void main(String[] args) throws Exception {

        // 욱제 이동 큐, 벽 이동 큐 따로 두기
        // 욱제 큐가 빌 때까지 반복
        // 욱제 움직임 -> 벽 이동
        // 욱제 큐에서 꺼낼 때 해당 칸이 벽이면 continue

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Queue<Pair> uq = new ArrayDeque<>();
        PriorityQueue<Pair> bq = new PriorityQueue<>();
        for(int i=0; i<8; i++) {
            String s = br.readLine();
            for(int j=0; j<8; j++) {
                board[i][j] = s.charAt(j);

                if(board[i][j] == '#') {
                    bq.offer(new Pair(i, j));
                }
            }
        }

        uq.offer(new Pair(7, 0));
        vis[7][0][0] = true; // 벽 상태에 따라 방문 처리를 해줌. 시간에 따라 벽 상태가 달라지므로, 시간에 따른 방문 처리.

        int t = 0;
        while(!uq.isEmpty()) {

            int size = uq.size();

            // 욱제 이동
            while(size-- > 0) {
                Pair cur = uq.poll();

//                System.out.println(cur.y + " " + cur.x);

                if(board[cur.y][cur.x] == '#')  continue; // 현재 위치가 벽이면 깔린거임

                if(!vis[cur.y][cur.x][t+1]) { // 현재 위치 큐에 넣는 경우
                    uq.offer(new Pair(cur.y, cur.x));
                    vis[cur.y][cur.x][t+1] = true;
                }

                for(int dir=0; dir<8; dir++) { // 8방향 이동
                    int ny = cur.y + dy[dir];
                    int nx = cur.x + dx[dir];

                    if(ny < 0 || ny >= 8 || nx < 0 || nx >= 8)  continue;
                    if(board[ny][nx] == '#' || vis[ny][nx][t+1])    continue; // 벽이거나 이미 방문했으면 넘어감

                    if(ny == 0) { // 목적지에 도달하면 종료
                        System.out.println(1);
                        return;
                    }

                    vis[ny][nx][t+1] = true; // 방문 처리
                    uq.offer(new Pair(ny, nx)); // 큐에 넣음
                }
            }

            // 벽 이동 -> 주의! 아래에 있는 벽을 먼저 이동시켜야 함
            Queue<Pair> tmp = new ArrayDeque<>();
            size = bq.size();
            while(size-- > 0) {
                Pair b = bq.poll();

//                System.out.println(b.y);

                board[b.y][b.x] = '.';

                int ny = b.y + 1;
                int nx = b.x;
                if(ny >= 8) {
                    continue;
                }
                board[ny][nx] = '#';

                tmp.offer(new Pair(ny, nx));
            }

            while(!tmp.isEmpty()) {
                Pair b = tmp.poll();
                bq.offer(new Pair(b.y, b.x));
            }

//            System.out.println();
            t++;
        }

        System.out.println(0);
    }
}
