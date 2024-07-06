package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class prob4991_2 {
    static final int[][] d = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };
    static final int GND = 0;
    static final int WALL = 1;
    static final int NODE = 2;

    static class xy {
        int bitState;
        int x;
        int y;

        public xy(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static StringTokenizer st = null;
    static StringBuilder sb = new StringBuilder();
    static int N, M;
    static int[][] board;
    static xy start;
    static List<xy> dirtyPoints;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());

            if (N == 0 && M == 0) {
                break;
            }

            board = new int[N][M];
            dirtyPoints = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                String s = br.readLine();
                for (int j = 0; j < M; j++) {
                    char c = s.charAt(j);
                    switch (c) {
                        case 'x':
                            board[i][j] = WALL;
                            break;
                        case 'o':
                            start = new xy(i, j);
                        case '.':
                            board[i][j] = GND;
                            break;
                        case '*':
                            board[i][j] = NODE;
                            dirtyPoints.add(new xy(i, j));
                        default:
                            break;
                    }
                }
            }

            sb.append(BFS(start)).append("\n");

        }
        System.out.println(sb.toString());
    }

    /**
     * 
     * @param start
     * @return 최소값 (단, 모든 곳에 대한 방문 여부가 false라면 -1)
     */
    private static int BFS(xy start) {
        int size = dirtyPoints.size();
        boolean[][][] visited = new boolean[1 << size][N][M];
        int depth = 0;
        Queue<xy> q = new ArrayDeque<>();
        q.add(start);
        visited[0][start.x][start.y] = true;

        while (q.size() > 0) {
            int qsize = q.size();
            depth++;

            while (qsize-- > 0) {
                xy cur = q.poll();

                for (int i = 0; i < 4; i++) {
                    int nx = cur.x + d[0][i];
                    int ny = cur.y + d[1][i];

                    if (IsOutBound(nx, ny) || visited[cur.bitState][nx][ny] || board[nx][ny] == WALL) {
                        continue;
                    }

                    xy next = new xy(nx, ny);
                    next.bitState = cur.bitState;

                    if (board[nx][ny] == NODE) {
                        // 몇번째 노드인지 찾기
                        int idx = -1;
                        for (int j = 0; j < size; j++) {
                            xy node = dirtyPoints.get(j);
                            if (node.x == nx && node.y == ny) {
                                idx = j;
                                break;
                            }
                        }
                        next.bitState = cur.bitState | (1 << idx);
                        if (next.bitState == ((1 << size) - 1)) {
                            return depth;
                        }
                    }

                    visited[next.bitState][nx][ny] = true;
                    q.add(next);
                }
            }

        }
        return -1;
    }

    private static boolean IsOutBound(int nx, int ny) {
        return nx < 0 || ny < 0 || nx >= N || ny >= M;
    }
}
