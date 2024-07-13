package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1981 {
    static int ans, N;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};
    static int[][] map;
    static int maxV, minV;

    public static void main(String[] args) throws IOException {
        // 모든 경우를 다 찾아야 함
        // 단 최대값 - 최솟값을 계속 갱신하며, 현재 ans(min_value)보다 크면 바로 return
        // dfs, 백트래킹
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        ans = Integer.MAX_VALUE;
        map = new int[N][N];
        maxV = 0;
        minV = 200;
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                maxV = Math.max(map[i][j], maxV);
                minV = Math.min(map[i][j], minV);
            }
        }

        //이분 탐색을 통해서 기준 수 찾기
        // 해당 수로 탐색 성공 => end = mid로 수정후 다시
        // 실패할 때 까지 ans 갱신

        System.out.println(binary(0,maxV - minV));
    }

    static int binary(int left, int right) {
        int mid;
        while (left<=right) {
            mid = (right + left) / 2;

            if(bfs(mid)) right = mid-1;
            else left = mid+1;
        }
        return left;
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    static boolean bfs(int target) {

        for (int aa = minV; aa <= maxV; aa++) {
            // 현재 mid(target)를 토대로 min값부터 min+target의 범위의 visit만 따로 처리하기
            boolean[][] visit = new boolean[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (aa <= map[i][j] && map[i][j] <= aa + target) visit[i][j] = false;
                    else visit[i][j] = true;
                }
            }
            if (visit[0][0] || visit[N - 1][N - 1]) {
                continue;
            }
            Queue<Pair> Q = new LinkedList<>();
            Q.add(new Pair(0, 0));
            visit[0][0] = true;
            while (!Q.isEmpty()) {
                Pair now = Q.poll();
                int nowX = now.x;
                int nowY = now.y;
                if(nowX == N-1 && nowY == N-1) return true;
                for (int i = 0; i < 4; i++) { // 최적화 하려면 pq에 넣고 현재 위치에서 가장 차이 나지 않는 경우
                    int nx = nowX + dx[i];
                    int ny = nowY + dy[i];
                    if (nx >= 0 && ny >= 0 && nx < N && ny < N) {
                        if (visit[nx][ny]) continue;
                        visit[nx][ny] = true;
                        Q.add(new Pair(nx,ny));
                    }
                }
            }
        }
        return false;
    }


}
