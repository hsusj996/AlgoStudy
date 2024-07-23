package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Solution_11404_MW {

    public static class Pair implements Comparable<Pair> {
        int city, cost;

        public Pair(int city, int cost) {
            this.city = city;
            this.cost = cost;
        }

        @Override
        public int compareTo(Pair o) {
            return this.cost - o.cost;
        }
    }
    static int n;
    static int m;
    static List<Pair>[] adjList;
    static int[] dist;
    static boolean[] vis;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        adjList = new ArrayList[n+1];
        for(int i=1; i<=n; i++) {
            adjList[i] = new ArrayList<>();
        }

        for(int i=0; i<m; i++) {
            String[] s = br.readLine().split(" ");
            int u = Integer.parseInt(s[0]);
            int v = Integer.parseInt(s[1]);
            int w = Integer.parseInt(s[2]);
            adjList[u].add(new Pair(v, w));
        }

        for(int i=1; i<=n; i++) {
            dist = new int[n+1];
            vis = new boolean[n+1];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[i] = 0;
            PriorityQueue<Pair> pq = new PriorityQueue<>();
            pq.offer(new Pair(i, 0));

            while(!pq.isEmpty()) {
                Pair cur = pq.poll();
                int minVertex = cur.city;

                if(vis[minVertex])  continue;

                vis[minVertex] = true;

                for(Pair nxt : adjList[minVertex]) {
                    if(dist[nxt.city] > dist[minVertex] + nxt.cost) {
                        dist[nxt.city] = dist[minVertex] + nxt.cost;
                        pq.offer(new Pair(nxt.city, dist[nxt.city]));
                    }
                }
            }

            for(int j=1; j<=n; j++) {
                if(dist[j] == Integer.MAX_VALUE) {
                    sb.append(0).append(" ");
                } else {
                    sb.append(dist[j]).append(" ");
                }
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}
