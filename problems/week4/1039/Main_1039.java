package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1039 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        String N = st.nextToken();
        int K = Integer.parseInt(st.nextToken());
        Queue<swapNumber> Q = new LinkedList<>();
        Q.add(new swapNumber(N,0));
        int M = N.length();
        int ans = -1;
        boolean [][]visit = new boolean[1000001][11];
        visit[Integer.parseInt(N)][0] = true;
        while(!Q.isEmpty()){
            swapNumber temp = Q.poll();
            String nowN = temp.nowN;
            int nowK = temp.nowK;
            if(nowK == K){
                ans = Math.max(Integer.parseInt(nowN),ans);
                continue;
            }
            for(int i = 0;i<M;i++){
                for(int j = i+1;j<M;j++){
                    if(i == 0 && nowN.charAt(j) == '0') continue;
                    StringBuilder sb = new StringBuilder(nowN);
                    swap(sb,i,j);
                    String swapedN = sb.toString();
                    if(visit[Integer.parseInt(swapedN)][nowK+1]) continue;
                    visit[Integer.parseInt(swapedN)][nowK+1] = true;
                    Q.add(new swapNumber(sb.toString(),nowK+1));
                }
            }
        }
        System.out.println(ans);
    }
    static class swapNumber{
        String nowN;
        int nowK;

        public swapNumber(String nowN, int nowK) {
            this.nowN = nowN;
            this.nowK = nowK;
        }
    }

    public static void swap(StringBuilder sb, int i, int j){
        char temp = sb.charAt(i);
        sb.setCharAt(i, sb.charAt(j));
        sb.setCharAt(j, temp);
    }
}
