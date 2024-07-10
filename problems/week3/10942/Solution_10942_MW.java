package problems;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution_10942_MW {

    static int n;
    static int[] nums;
    static int m;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        n = Integer.parseInt(br.readLine());

        nums = new int[n+1];

        String[] s = br.readLine().split(" ");

        for(int i=1; i<=n; i++) {
            nums[i] = Integer.parseInt(s[i-1]);
        }

        int[][] d = new int[n+1][n+1];

        for(int offset=0; offset<=n; offset++) {
            for(int st=0; st+offset<=n; st++) {
                int en = st+offset;
                if(st == en) { // 길이가 1이면 팰린드롬
                    d[st][en] = 1;
                } else if(nums[st] == nums[en]) { // 시작점과 마지막점이 같으면
                    if(offset == 1) { // 길이가 2이면 팰린드롬
                        d[st][en] = 1;
                    } else if(d[st+1][en-1] == 1) { // 길이가 2가 아니고, 시작점과 마지막점 사이가 팰린드롬이면
                        d[st][en] = 1;
                    }
                }
            }
        }

        m = Integer.parseInt(br.readLine());

        while(m-- > 0) {
            int st, en;
            s = br.readLine().split(" ");
            st = Integer.parseInt(s[0]);
            en = Integer.parseInt(s[1]);

            sb.append(d[st][en]).append("\n");
        }
        System.out.println(sb);
    }
}
