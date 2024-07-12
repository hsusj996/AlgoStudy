package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class prob1039 {
    static class Node {
        int a;
        int b;
        int result;

        public Node(int a, int b, int result) {
            this.a = a;
            this.b = b;
            this.result = result;
        }
    }

    static boolean[][] visited;
    static int A, B;
    static int size;

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Scanner sc = new Scanner(System.in);
        A = sc.nextInt();
        B = sc.nextInt();
        size = String.valueOf(A).length();
        visited = new boolean[1_000_001][B + 1];

        if (String.valueOf(A).length() == 1) {
            System.out.println(-1);
            return;
        }

        System.out.println(bfs());
    }

    private static int bfs() {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(A);
        visited[A][0] = true;
        int depth = 0;

        while (++depth <= B) {
            int qsize = q.size();
            while (qsize-- > 0) {
                int cur = q.poll();

                for (int i = 0; i < size; i++) {
                    for (int j = i + 1; j < size; j++) {
                        int result = Swap(cur, i, j);

                        if (String.valueOf(result).length() != size || visited[result][depth]) {
                            continue;
                        }

                        visited[result][depth] = true;
                        q.add(result);
                    }
                }
            }
        }

        if(q.size() == 0){
            return -1;
        }

        int max = 0;
        for (int n : q) {
            max = Math.max(max, n);
        }

        return max;
    }

    private static int Swap(int n, int a, int b) {
        String N = String.valueOf(n);

        char[] cArr = N.toCharArray();
        char tmp = cArr[a];
        cArr[a] = cArr[b];
        cArr[b] = tmp;

        return Integer.parseInt(String.valueOf(cArr));
    }
}