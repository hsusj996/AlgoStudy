package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main_1039 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        String N = st.nextToken();
        int K = Integer.parseInt(st.nextToken());
        Queue<SwapNumber> Q = new LinkedList<>();
        Q.add(new SwapNumber(N, 0));
        int M = N.length();
        int ans = -1;
        Set<String>[] visit = new HashSet[K + 1];
        for (int i = 0; i <= K; i++) {
            visit[i] = new HashSet<>();
        }
        visit[0].add(N);

        while (!Q.isEmpty()) {
            SwapNumber temp = Q.poll();
            String nowN = temp.nowN;
            int nowK = temp.nowK;
            if (nowK == K) {
                ans = Math.max(Integer.parseInt(nowN), ans);
                continue;
            }

            for (int i = 0; i < M - 1; i++) {
                for (int j = i + 1; j < M; j++) {
                    if (i == 0 && nowN.charAt(j) == '0') continue;
                    StringBuilder sb = new StringBuilder(nowN);
                    swap(sb, i, j);
                    String swapedN = sb.toString();
                    if (visit[nowK + 1].contains(swapedN)) continue;
                    visit[nowK + 1].add(swapedN);
                    Q.add(new SwapNumber(swapedN, nowK + 1));
                }
            }
        }
        System.out.println(ans);
    }

    static class SwapNumber {
        String nowN;
        int nowK;

        public SwapNumber(String nowN, int nowK) {
            this.nowN = nowN;
            this.nowK = nowK;
        }
    }

    public static void swap(StringBuilder sb, int i, int j) {
        char temp = sb.charAt(i);
        sb.setCharAt(i, sb.charAt(j));
        sb.setCharAt(j, temp);
    }
}
