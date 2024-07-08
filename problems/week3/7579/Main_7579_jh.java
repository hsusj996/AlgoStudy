package problems;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
public class Main_7579_jh {

        public static void main(String[] args) throws IOException {
            Scanner sc = new Scanner(System.in);
            int N = sc.nextInt();
            int M = sc.nextInt();
            int []memory = new int[N+1];
            int []cost = new int[N+1];
            int [][]DP = new int[N+1][10002];
            for(int i =1;i<=N;i++) {
                int m = sc.nextInt();
                memory[i] = m;
            }
            for(int i =1;i<=N;i++) {
                int c = sc.nextInt();
                cost[i] = c;
            }
            int result = 987654321;
            for(int i =1;i<=N;i++) { // 아이템
                for(int j=0;j<=10001;j++) { // 가능한 모든 무게
                    if(DP[i][j]>=M) {
                        result = Math.min(j, result);
                        continue;
                    }
                    if(cost[i]<=j) {
                        DP[i][j] = Math.max(DP[i-1][j],DP[i-1][j-cost[i]]+memory[i]);
                    }
                    else {
                        DP[i][j] = DP[i-1][j];
                    }
                    if(DP[i][j]>=M) {
                        result = Math.min(j, result);
                    }
                }
            }
            System.out.println(result);

        }
}
