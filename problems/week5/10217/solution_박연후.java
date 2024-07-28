import java.io.*;
import java.util.*;

public class Main {

    static final int MAX_VAL = 1_000_0000;

    static StringTokenizer st;
    static StringBuilder sb;

    static class Edge implements Comparable<Edge> {

        int e;
        int c;
        int t;

        public Edge(int e, int c, int t) {
            this.e = e;
            this.c = c;
            this.t = t;
        }

        public int compareTo(Edge o) {
            return this.t - o.t;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int tc = Integer.parseInt(br.readLine());
        sb = new StringBuilder();

        while (tc-- > 0) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            int ans = MAX_VAL;
            List<List<Edge>> graph = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }

            int[][] dp = new int[n + 1][m + 1];
            for (int i = 0; i <= n; i++) {
                Arrays.fill(dp[i], MAX_VAL);
            }

            for (int i = 0; i < k; i++) {
                st = new StringTokenizer(br.readLine());

                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int t = Integer.parseInt(st.nextToken());

                graph.get(s).add(new Edge(e, c, t));
            }

            for (int i = 0; i <= n; i++) {
                Collections.sort(graph.get(i));
            }

            PriorityQueue<Edge> pq = new PriorityQueue<>();
            dp[1][0] = 0;
            pq.add(new Edge(1, 0, 0));

            while (!pq.isEmpty()) {
                Edge now = pq.poll();
                if (dp[now.e][now.c] < now.t || now.t > ans) {
                    continue;
                }
                for (Edge edge : graph.get(now.e)) {
                    if (edge.c + now.c > m) {
                        continue;
                    }
                    if (dp[edge.e][edge.c + now.c] > edge.t + now.t) {
                        dp[edge.e][edge.c + now.c] = edge.t + now.t;
                        pq.add(new Edge(edge.e, edge.c + now.c, edge.t + now.t));
                        if (edge.e == n) {
                            ans = Math.min(ans, edge.t + now.t);
                            break;
                        }
                    }
                }
            }

            sb.append(ans == MAX_VAL ? "Poor KCM" : ans);
        }

        System.out.println(sb);
    }

}