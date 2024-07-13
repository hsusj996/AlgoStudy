package baekjoon;

import java.util.*;

public class Main_9370 {
    public static int T, n, m, t, s, g, h;
    public static ArrayList<ArrayList<Edge>> graph;
    public static int[] d;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        T = sc.nextInt();
        for (int j = 0; j < T; j++){
            n = sc.nextInt();
            m = sc.nextInt();
            t = sc.nextInt();
            graph = new ArrayList<>();
            s = sc.nextInt();
            g = sc.nextInt();
            h = sc.nextInt();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }
            for (int i = 0; i < m; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                int d = sc.nextInt();
                graph.get(a).add(new Edge(b, d));
                graph.get(b).add(new Edge(a, d));
            }
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            int[] des = new int[t];
            for (int i = 0; i < t; i++){
                des[i] = sc.nextInt();
            }
            for (int d : des) {
                long res1 = dijkstra(s, g) + dijkstra(g, h) + dijkstra(h, d);
                long res2 = dijkstra(s, h) + dijkstra(h, g) + dijkstra(g, d);
                long res3 = dijkstra(s, d);
                if (Math.min(res1, res2) == res3) {
                    pq.add(d);
                }
            }
            while (!pq.isEmpty()) {
                System.out.print(pq.poll() + " ");
            }
            System.out.println();
        }
    }
    public static int dijkstra(int start, int end) {
        d = new int[n + 1];
        Arrays.fill(d, Integer.MAX_VALUE);
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(start, 0));
        d[start] = 0;
        while(!pq.isEmpty()) {
            Edge edge = pq.poll();
            int now = edge.index;
            int dist = edge.distance;
            if (d[now] < dist) continue;
            for (int i = 0; i < graph.get(now).size(); i++) {
                int cost = d[now] + graph.get(now).get(i).distance;
                if (cost < d[graph.get(now).get(i).index]) {
                    d[graph.get(now).get(i).index] = cost;
                    pq.offer(new Edge(graph.get(now).get(i).index, cost));
                }
            }
        }
        return d[end];
    }
    static class Edge implements Comparable<Edge>{
        int index, distance;
        public Edge(int index, int distance) {
            this.index = index;
            this.distance = distance;
        }
        @Override
        public int compareTo(Edge n){
            return distance - n.distance;
        }
    }
}
