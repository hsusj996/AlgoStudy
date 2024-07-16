package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.StringTokenizer;

public class Main_11657 {
    static  ArrayList<Node>[] graph;
    static int N, M, maxTime;
    static boolean flag;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        graph = new ArrayList[N+1];
        for(int i = 0;i<=N;i++){
            graph[i] = new ArrayList<>();
        }
        flag = false;
        maxTime = Integer.MIN_VALUE;
        for(int i = 0;i<M;i++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());
            graph[start].add(new Node(end,time));
        }


        long []dist = belman();
        for(int i =2;i<=N;i++){
            if(dist == null){
                System.out.println(-1);
                break;
            }
            if(dist[i] == Integer.MAX_VALUE) System.out.println(-1);
            else System.out.println(dist[i]);
        }
    }

    static long[] belman(){ // 벨만은 모든 경우 확인함
        long []dist = new long[N+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;

        for(int i = 1; i<N;i++){ // 1부터 N-1까지
            for(int j = 1;j<N+1;j++){ // 1부터 N까지
                if(dist[j] == Integer.MAX_VALUE) continue; // 갱신 안 되었으면 넘어가기
                for(Node nextNode : graph[j]){
                    if(dist[nextNode.index] > dist[j] + nextNode.weight){
                        dist[nextNode.index] = dist[j] + nextNode.weight;
                    }
                }
            }
        }

        for(int j = 1;j<N+1;j++){ // 1부터 N까지
            if(dist[j] == Integer.MAX_VALUE) continue; // 갱신 안 되었으면 넘어가기
            for(Node nextNode : graph[j]){
                if(dist[nextNode.index] > dist[j] + nextNode.weight){ // 만약 음수 사이클 발견시 null 값 출력
                    return null;
                }
            }
        }
/*
벨만 포드의 경우 N * N으로 최적해를 순서대로 찾고,
만약 다시 for문으로 탐색해보았을 때 최단 값이 다시 음수인 경우 사이클을 발생했다고 판단
 */
        return dist;
    }

    static class Node implements Comparable<Node>{
        int index, weight;

        public Node(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight-o.weight;
        }
    }
}
