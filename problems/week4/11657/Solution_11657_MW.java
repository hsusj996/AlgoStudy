package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution_11657_MW {

    public static class Edge {
        int num, weight;

        public Edge(int num, int weight) {
            this.num = num;
            this.weight = weight;
        }
    }

    static int n, m;
    static long[] dist;
    static List<Edge>[] adjList;
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] s = br.readLine().split(" ");
        n = Integer.parseInt(s[0]);
        m = Integer.parseInt(s[1]);

        dist = new long[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        adjList = new ArrayList[n + 1];
        for(int i=1; i<=n; i++) {
            adjList[i] = new ArrayList<>();
        }

        for(int i=0; i<m; i++) {
            int a, b, c;
            s = br.readLine().split(" ");
            a = Integer.parseInt(s[0]);
            b = Integer.parseInt(s[1]);
            c = Integer.parseInt(s[2]);
            adjList[a].add(new Edge(b, c));
        }

        dist[1] = 0;
        boolean negativeCycle = false;
        for(int i=0; i<n; i++) {
            for(int j=1; j<=n; j++) {

                for(Edge edge : adjList[j]) {
                    int nxt = edge.num;
                    int weight = edge.weight;
                    if(dist[j] != Integer.MAX_VALUE && dist[nxt] > dist[j] + weight) {
                        dist[nxt] = dist[j] + weight;

                        if(i == n-1) {
                            negativeCycle = true;
                        }
                    }
                }
            }
        }

        if(negativeCycle) {
            System.out.println(-1);
            return;
        }
        for(int i=2; i<=n; i++) {
            if(dist[i] == Integer.MAX_VALUE) {
                System.out.println(-1);
            } else {
                System.out.println(dist[i]);
            }
        }
    }
}
