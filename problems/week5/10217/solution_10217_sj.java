package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class prob10217 {
    static final int INF = 100_000_000;

    static class Edge implements Comparable<Edge> {
        int to;
        int cost;
        int time;

        public Edge(int to, int cost, int time) {
            this.to = to;
            this.cost = cost;
            this.time = time;
        }

        @Override
        public int compareTo(Edge o) {
            if (this.time == o.time) {
                return this.cost - o.cost;
            }
            return this.time - o.time;
        }
    }

    static StringTokenizer st = null;
    static StringBuilder sb = new StringBuilder();
    static int N, M, K;
    static List<Edge>[] graph;
    static int[][] minTime;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            graph = new ArrayList[N + 1];
            for (int i = 1; i <= N; i++) {
                graph[i] = new ArrayList<>();
            }
            minTime = new int[M + 1][N + 1];
            for (int i = 1; i <= M; i++) {
                Arrays.fill(minTime[i], INF);
            }

            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());

                graph[u].add(new Edge(v, c, d));
            }

            dijkstra();
            int min = INF;
            for (int i = 1; i <= M; i++) {
                min = Math.min(min, minTime[i][N]);
            }
            sb.append(min == INF ? "Poor KCM" : min).append("\n");
        }
        System.out.println(sb.toString());
    }

    private static void dijkstra() {
        for(int i=1;i<=N;i++){
            Collections.sort(graph[i]);
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        minTime[0][1] = 0;

        pq.add(new Edge(1, 0, 0));

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();

            if (cur.to == N) {
                return;
            }

            for (Edge next : graph[cur.to]) {
                int time = cur.time + next.time;
                int cost = cur.cost + next.cost;

                if (cost > M) {
                    continue;
                }

                if (minTime[cost][next.to] > time) {
                    for (int j = cost + 1; j <= M; j++) {
                        if (minTime[j][next.to] <= time) {
                            break;
                        }
                        minTime[j][next.to] = time;
                    }
                    minTime[cost][next.to] = time;
                    pq.add(new Edge(next.to, cost, time));
                }
            }
        }
    }
}