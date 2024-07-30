import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
    static int[] dx = {-1, 1, 0, 0}; // 아래 위 좌 우
    static int[] dy = {0, 0, -1, 1}; // 아래 위 좌 우
    static int N;
    static char[][] map;
    static int[][] dist;
    static int ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new char[N][N];
        dist = new int[N][N];
        ans = Integer.MAX_VALUE;
        int startX = -1, startY = -1;
        boolean flag = false;
        for (int i = 0; i < N; i++) {
            String temp = br.readLine();
            for (int j = 0; j < N; j++) {
                map[i][j] = temp.charAt(j);
                if (map[i][j] == '#') {
                    if (!flag) {
                        startX = i;
                        startY = j;
                        flag = true;
                    } else {
                        map[i][j] = '$';
                    }
                }
                dist[i][j] = Integer.MAX_VALUE; // 거리 배열 초기화
            }
        }
        dijkstra(startX, startY);
        System.out.println(ans);
    }

    static void dijkstra(int startX, int startY) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[][][] visit = new boolean[N][N][4]; // 각 위치와 방향에 대한 방문 여부 추적
        dist[startX][startY] = 0;

        for (int i = 0; i < 4; i++) {
            int x = startX + dx[i];
            int y = startY + dy[i];
            if (x < 0 || y < 0 || x >= N || y >= N || map[x][y] == '*') continue;
            pq.offer(new Node(startX, startY, 0, i));
            visit[startX][startY][i] = true;
        }

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int x = cur.x;
            int y = cur.y;
            int cost = cur.cost;
            int dir = cur.dir;

            if (map[x][y] == '$') {
                ans = Math.min(ans, cost);
                return;
            }

            int nx = x + dx[dir];
            int ny = y + dy[dir];
            if (nx >= 0 && ny >= 0 && nx < N && ny < N && !visit[nx][ny][dir] && map[nx][ny] != '*') {
                visit[nx][ny][dir] = true;
                pq.offer(new Node(nx, ny, cost, dir));
            }

            if (map[x][y] == '!') {
                for (int i = 0; i < 4; i++) {
                    if (i != dir && !visit[x][y][i]) {
                        visit[x][y][i] = true;
                        pq.offer(new Node(x, y, cost + 1, i));
                    }
                }
            }
        }
    }

    static class Node implements Comparable<Node> {
        int x, y, cost, dir;

        Node(int x, int y, int cost, int dir) {
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.dir = dir;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
}