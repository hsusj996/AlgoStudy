package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Solution_9370_MW {

    public static class Edge implements Comparable<Edge> {

        int num, dist;

        public Edge(int num, int dist) {
            this.dist = dist;
            this.num = num;
        }

        @Override
        public int compareTo(Edge o) {
            return this.dist - o.dist;
        }
    }

    static int n, m, t;
    static int st, g, h;
    static List<Edge>[] adjList;
    static List<Integer> destinations;
    static boolean[] vis;
    static int[] dist1;
    static int[] dist2;
    static int[] dist3;
    static List<Integer> ans;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws Exception {

        // s지점 출발, 목적지까지 우회하지 않고 최단거리로 이동
        // g와 h사이에 있는 도로 지나감
        // n, m, t : 교차로, 도로, 목적지 후보 개수
        // s, g, h : s는 예술가들의 출발지, g-h는 필수적으로 지나가야하는 도로

        // 1. 다익스트라
        // 2. 목적지인데 g, h 방문 안했다면, 정답에 추가하지 않음

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            String[] s = br.readLine().split(" ");
            n = Integer.parseInt(s[0]);
            m = Integer.parseInt(s[1]);
            t = Integer.parseInt(s[2]);

            s = br.readLine().split(" ");
            st = Integer.parseInt(s[0]);
            g = Integer.parseInt(s[1]);
            h = Integer.parseInt(s[2]);

            adjList = new ArrayList[n+1];

            for (int i = 0; i <= n; i++) {
                adjList[i] = new ArrayList<>();
            }

            int tmp = 0;
            for (int i = 0; i < m; i++) {
                s = br.readLine().split(" ");
                int a = Integer.parseInt(s[0]);
                int b = Integer.parseInt(s[1]);
                int w = Integer.parseInt(s[2]);
                adjList[a].add(new Edge(b, w));
                adjList[b].add(new Edge(a, w));
                if((a == g && b == h) || (a == h && b == g)) {
                    tmp = w;
                }
            }

            destinations = new ArrayList<>();
            for (int i = 0; i < t; i++) {
                int dest = Integer.parseInt(br.readLine());
                destinations.add(dest);
            }

            dist1 = dijkstra(st);
            dist2 = dijkstra(g);
            dist3 = dijkstra(h);

            ans = new ArrayList<>();
            for (int dest : destinations) {

                if(dist1[h] - dist1[g] == tmp && dist1[h] + dist3[dest] == dist1[dest]) {
                    ans.add(dest);
                } else if(dist1[g] - dist1[h] == tmp && dist1[g] + dist2[dest] == dist1[dest]) {
                    ans.add(dest);
                } else if ((dist1[dest] == (dist1[g] + tmp + dist3[dest])) || (dist1[dest] == (dist1[h] + tmp + dist2[dest]))) {
                    ans.add(dest);
                }
            }

            Collections.sort(ans);
            for(int num : ans) {
                sb.append(num).append(" ");
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    static int[] dijkstra(int start) {
        vis = new boolean[n+1];
        int[] dist = new int[n+1];

        Arrays.fill(dist, 50000*1000+1);
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(start, 0));
        dist[start] = 0;

        while (!pq.isEmpty()) {
            Edge minEdge = pq.poll();

            int minVertex = minEdge.num;

            if (vis[minVertex]) {
                continue;
            }

            vis[minVertex] = true;

            for (Edge edge : adjList[minVertex]) {
                if (dist[edge.num] < dist[minVertex] + edge.dist) {
                    continue;
                }
                dist[edge.num] = dist[minVertex] + edge.dist;
                pq.offer(new Edge(edge.num, dist[edge.num]));
            }
        }
        return dist;
    }
}