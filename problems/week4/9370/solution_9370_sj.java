package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class prob9370 {
    static final int INF = 1_000_000_000;

    static class Edge implements Comparable<Edge> {
        int to;
        int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }

    }

    static int T;
    static int n, m, t, s, g, h;
    static List<Edge>[] graph;
    static int[] minDistance;
    static StringTokenizer st = null;
    static boolean[] isDest;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            t = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            g = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            graph = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                if ((a == g && b == h) || (a == h && b == g)) {
                    graph[a].add(new Edge(b, 2 * weight - 1));
                    graph[b].add(new Edge(a, 2 * weight - 1));
                }

                graph[a].add(new Edge(b, 2 * weight));
                graph[b].add(new Edge(a, 2 * weight));
            }

            isDest = new boolean[2001];
            for (int i = 0; i < t; i++) {
                isDest[Integer.parseInt(br.readLine())] = true;
            }

            dijkstra();

            for (int i = 1; i <= n; i++) {
                if (isDest[i] && minDistance[i] % 2 == 1) {
                    sb.append(i).append(" ");
                }
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    private static void dijkstra() {
        boolean[] visited = new boolean[n + 1];
        minDistance = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            minDistance[i] = INF;
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(s, 0));
        minDistance[s] = 0;

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();

            if (visited[cur.to]) {
                continue;
            }
            visited[cur.to] = true;

            for (Edge e : graph[cur.to]) {
                int distance = minDistance[cur.to] + e.weight;
                if (minDistance[e.to] >= distance) {
                    minDistance[e.to] = distance;
                    pq.add(new Edge(e.to, distance));
                }
            }
        }
    }
}
