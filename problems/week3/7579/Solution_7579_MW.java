package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution_7579_MW {

    static int N, M;
    static int[] m;
    static int[] c;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        N = Integer.parseInt(s[0]);
        M = Integer.parseInt(s[1]);

        int[] m = new int[N+1];
        int[] c = new int[N+1];
        int[][] d = new int[N+1][10001]; // i번째 앱까지 고려하여 cost가 j일 때 확보한 메모리의 최대값

        s = br.readLine().split(" ");
        for(int i=1; i<=N; i++) {
            m[i] = Integer.parseInt(s[i-1]);
        }
        s = br.readLine().split( " ");
        int sum = 0;
        for(int i=1; i<=N; i++) {
            c[i] = Integer.parseInt(s[i-1]);
            sum += c[i];
        }

        for(int cost=0; cost<=sum; cost++) {
            for(int i=1; i<=N; i++) {
                d[i][cost] = d[i-1][cost]; // 현재 앱을 비활성화 하지 않는 경우

                if(cost >= c[i]) { // 비활성화 할 수 있는 경우
                    d[i][cost] = Math.max(d[i][cost], d[i-1][cost-c[i]] + m[i]);
                    // i번째 앱을 비활성화 하지 않는 것과 비활성화 하는 것을 비교하여 큰 값을 취함
                }

                if(d[i][cost] >= M) { // M보다 확보한 메모리가 커지면 cost 출력하고 종료 (최소 cost)
                    System.out.println(cost);
                    return;
                }
            }
        }
    }

}