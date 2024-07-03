package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Solution_4991_MW {

    static class Pair {
        int y, x;
        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static int h, w;
    static char[][] board;
    static int[][][] distance;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};
    static List<Pair> dusts = new ArrayList<>();
    static int sy, sx;
    static List<Integer> orders = new ArrayList<>();
    static boolean[] vis = new boolean[11];
    static int ans = Integer.MAX_VALUE;
    static boolean isFinish;
    public static void main(String[] args) throws Exception {

        // 모든 먼지에서 BFS를 통해 다른 먼지들, 시작 위치까지 거리를 구함
        // 다른 먼지까지 거리가 0인게 존재하면 -1 출력
        // 순열을 통해 먼지들, 시작 위치 사이의 순서를 구함. 11!
        // 최소값을 갱신

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            String[] s = br.readLine().split(" ");
            w = Integer.parseInt(s[0]);
            h = Integer.parseInt(s[1]);

            if(w == 0 && h == 0)    break;

            dusts.clear();
            distance = new int[11][h][w];
            ans = Integer.MAX_VALUE;

            board = new char[h][w];
            for(int i=0; i<h; i++) {
                String line = br.readLine();
                for(int j=0; j<w; j++) {
                    board[i][j] = line.charAt(j);
                    if(board[i][j] == '*') {
                        dusts.add(new Pair(i, j));
                    } else if(board[i][j] == 'o') {
                        sy = i;
                        sx = j;
                    }
                }
            }

            bfs(0, new Pair(sy, sx));

            for(int i=0; i<dusts.size(); i++) {
                bfs(i+1, dusts.get(i));
            }

            for(int i=0; i<=dusts.size()-1; i++) {
                for(int j=i; j<dusts.size(); j++) {
                    Pair other = dusts.get(j);
                    if(distance[i][other.y][other.x] == 401) {
                        System.out.println(-1);
                        isFinish = true;
                        break;
                    }
                }
                if(isFinish) {
                    break;
                }
            }

            if(isFinish) {
                isFinish = false;
                continue;
            }

//            printForDebug();

            dfs(0);

            System.out.println(ans);
        }


    }

    static void bfs(int idx, Pair dust) {

        for(int i=0; i<h; i++) {
            for(int j=0; j<w; j++) {
                distance[idx][i][j] = 20*20+1;
            }
        }

        boolean[][] vis = new boolean[h][w];
        Queue<Pair> q = new ArrayDeque<>();
        q.offer(new Pair(dust.y, dust.x));
        vis[dust.y][dust.x] = true;
        distance[idx][dust.y][dust.x] = 0;

        while(!q.isEmpty()) {

            Pair cur = q.poll();

            for(int dir=0; dir<4; dir++) {
                int ny = cur.y + dy[dir];
                int nx = cur.x + dx[dir];
                if(ny < 0 || ny >= h || nx < 0 || nx >= w)  continue;
                if(board[ny][nx] == 'x' || vis[ny][nx])    continue;
                if(distance[idx][cur.y][cur.x] + 1 >= distance[idx][ny][nx])  continue;
                distance[idx][ny][nx] = distance[idx][cur.y][cur.x] + 1;
                q.offer(new Pair(ny, nx));
            }
        }
    }

    static void dfs(int k) {

        if(k == dusts.size()) {

//            System.out.println(orders.toString());
            int sum = 0;

            int num = orders.get(0);
            Pair dust = dusts.get(num-1);

            sum += distance[0][dust.y][dust.x];

//            System.out.println(0 + " " + num + " " + sum);

            for(int i=1; i<dusts.size(); i++) {
                int from = orders.get(i-1);
                num = orders.get(i);
                Pair to = dusts.get(num-1);
                int d = distance[from][to.y][to.x];
                sum += d;
//                System.out.println(from + " " + num + " " + d);
            }
            ans = Math.min(ans, sum);
            return;
        }

        for(int i=1; i<=dusts.size(); i++) {
            if(vis[i])  continue;
            orders.add(i);
            vis[i] = true;
            dfs(k+1);
            orders.remove(orders.size()-1);
            vis[i] = false;
        }
    }

    static void printForDebug() {
        for(int a=0; a<10; a++) {
            for(int i=0; i<h; i++) {
                for(int j=0; j<w; j++) {
                    System.out.print(distance[a][i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }
}
