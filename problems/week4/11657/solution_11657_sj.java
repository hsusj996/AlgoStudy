package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class prob11657 {
    static final int INF = 1_000_000_000;

    static class Edge {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

    }

    static int N, M;
    static long[] minDistance;
    static List<Edge> edgeList;
    static StringTokenizer st = null;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        edgeList = new ArrayList<>();
        minDistance = new long[N + 1];
        Arrays.fill(minDistance, INF);

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());

            edgeList.add(new Edge(A, B, C));
        }

        if (BellMan()) {
            System.out.println(-1);
        } else {
            for (int i = 2; i <= N; i++) {
                if (minDistance[i] == INF) {
                    sb.append(-1);
                } else {
                    sb.append(minDistance[i]);
                }
                sb.append("\n");
            }
        }

        System.out.println(sb.toString());
    }

    private static boolean BellMan() {
        minDistance[1] = 0;

        for (int i = 1; i < N; i++) {
            for (Edge cur : edgeList) {
                if(minDistance[cur.from] >= INF){
                    continue;
                }
                long dist = minDistance[cur.from] + cur.weight;

                minDistance[cur.to] = Math.min(minDistance[cur.to], dist);
            }
        }

        for (Edge cur : edgeList) {
            if(minDistance[cur.from] >= INF){
                continue;
            }
            long dist = minDistance[cur.from] + cur.weight;

            if (minDistance[cur.to] > dist) {
                return true;
            }
        }

        return false;
    }
}
