package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class prob1956 {
    static final int INF = 10_000_000;
    static int V, E;
    static StringTokenizer st = null;
    static int[][] graph;
    static int[][] minDistance;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        graph = new int[V + 1][V + 1];
        for(int i=1;i<=V;i++){
            Arrays.fill(graph[i], INF);
            graph[i][i] = 0;
        }

        for(int i=0;i<E;i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph[s][e] = cost;
        }

        floydWarshall();

        System.out.println(checkGraph());
    }
    private static int checkGraph() {
        int ret = INF;
        for(int i=1;i<=V;i++){
            for(int j=i+1;j<=V;j++){
                int cycleSum = graph[i][j] + graph[j][i];

                if(cycleSum < INF){
                    ret = Math.min(ret, cycleSum);
                }
            }
        }

        return ret == INF ? -1 : ret;
    }
    private static void floydWarshall() {
        for(int k=1;k<=V;k++){
            for(int i=1;i<=V;i++){
                for(int j=1;j<=V;j++){
                    graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }
    }
}
