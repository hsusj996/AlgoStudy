package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_17404_MW {

    static int n;
    static int[][] home;
    static int[][] d;
    public static void main(String[] args) throws IOException {

        // 1. 첫번째 집의 색에 따라 케이스 분류
        // 2. 이웃한 집의 색을 다르게 채웠을 때 최소 비용 DP 테이블 채우기 (n까지)
        // 3. 마지막 집의 색이 첫 번째 집의 색과 같을 경우 제외시키기

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        home = new int[n][3];
        d = new int[n][3];
        for(int i=0; i<n; i++) {
            String[] s = br.readLine().split(" ");
            for(int j=0; j<s.length; j++) {
                home[i][j] = Integer.parseInt(s[j]);
            }
        }

        // d[i][0]: i번째에 R색을 칠했을 때, i 번째까지 최소 비용
        // d[i][1]: i번째에 G색을 칠했을 때, i 번째까지 최소 비용
        // d[i][2]: i번째에 B색을 칠했을 때, i 번째까지 최소 비용
        int mn = Integer.MAX_VALUE;
        for(int st=0; st<3; st++) { // 첫 번째 집의 색에 따라 구하기
            for(int i=0; i<3; i++) { // ex) 첫 번째 집이 RED인 경우, 첫 번째 집이 GREEN, BLUE인 경우를 무한대로 처리
                if(st == i) {
                    d[0][st] = home[0][st];
                } else {
                    d[0][i] = 1000*1001;
                }
            }

            for(int i=1; i<n; i++) {
                d[i][0] = Math.min(d[i-1][1], d[i-1][2]) + home[i][0];
                d[i][1] = Math.min(d[i-1][0], d[i-1][2]) + home[i][1];
                d[i][2] = Math.min(d[i-1][0], d[i-1][1]) + home[i][2];
            }

            // 마지막 집의 색은 첫 번째 집과 이전 집 모두와 색이 달라야 함.
            // 마지막 집의 색과 첫 번째 집의 색이 같은 경우, 최대값으로 설정하여 선택되지 않도록 함
            d[n-1][st] = Integer.MAX_VALUE;

            for(int i=0; i<3; i++) {
                mn = Math.min(mn, d[n-1][i]);
            }
        }
        System.out.println(mn);
    }
}
