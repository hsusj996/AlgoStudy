import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Solution_1956_MW {

    static class Edge implements Comparable<Edge> {
        int vertex, weight;

        public Edge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }

    static int v, e;
    static List<Edge>[] adjList;
    static int[] dist;
    static boolean[] vis;
    static int ans = Integer.MAX_VALUE;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] s = br.readLine().split(" ");
        v = Integer.parseInt(s[0]);
        e = Integer.parseInt(s[1]);

        adjList = new ArrayList[v+1];
        for(int i=1; i<=v; i++) {
            adjList[i] = new ArrayList<>();
        }

        for(int i=0; i<e; i++) {
            int a, b, c;
            s = br.readLine().split(" ");
            a = Integer.parseInt(s[0]);
            b = Integer.parseInt(s[1]);
            c = Integer.parseInt(s[2]);

            adjList[a].add(new Edge(b, c));
        }

        for(int st=1; st<=v; st++) {

            PriorityQueue<Edge> pq = new PriorityQueue<>();
            dist = new int[v+1];
            Arrays.fill(dist, Integer.MAX_VALUE);
            vis = new boolean[v+1];

            pq.offer(new Edge(st, 0));
            dist[st] = 0;
            while(!pq.isEmpty()) {
                Edge cur = pq.poll();

                int minVertex = cur.vertex;

                if(vis[minVertex] && minVertex != st)  continue; // 시작 지점과 같은 경우 중복 허용

                vis[minVertex] = true;

                for(Edge e : adjList[minVertex]) {

                    if(e.vertex == st) { // 사이클을 이루는 경우
                        if(dist[st] == 0) { // 첫 사이클 경로일 경우
                            dist[st] = dist[minVertex] + e.weight; // 초기 사이클 경로 갱신
                        } else { // 이미 형성된 사이클 경로가 있을 경우
                            dist[st] = Math.min(dist[st], dist[minVertex] + e.weight); // 기존 사이클 길이와 비교해서 작은 것으로 갱신
                        }
                        ans = Math.min(ans, dist[st]); // 사이클 경로 최소값 갱신
                    }

                    if(dist[e.vertex] > dist[minVertex] + e.weight) { // 최단 경로 갱신
                        dist[e.vertex] = dist[minVertex] + e.weight;
                        pq.offer(new Edge(e.vertex, dist[e.vertex]));
                    }
                }
            }
        }

        if(ans == Integer.MAX_VALUE) { // 사이클이 형성되지 않았을 경우
            System.out.println(-1);
        } else {
            System.out.println(ans);
        }
    }
}
