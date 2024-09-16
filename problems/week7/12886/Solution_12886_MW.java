import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Solution_12886_MW {

    public static class Pair {
        int a, b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    static int[] num = new int[3];
    static boolean[][] vis;
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        num[0] = Integer.parseInt(s[0]);
        num[1] = Integer.parseInt(s[1]);
        num[2] = Integer.parseInt(s[2]);
        int sum = Arrays.stream(num).sum();
        vis = new boolean[sum+1][sum+1];
        // 전체 합이 sum으로 일정하므로, 각각의 돌은 sum을 넘을 수 없음.
        // 최악의 시간 복잡도는 vis 배열이 모두 true가 될 때까지 탐색하는 것이므로, a, b, c가 모두 500이라고 가정할 때
        // 대략 1500 * 1500 = 2,250,000이 상한 값

        if(sum % 3 != 0) {
            System.out.println(0);
            return;
        }

        Queue<Pair> q = new ArrayDeque<>();
        for(int i=0; i<3; i++) {
            for(int j=i+1; j<3; j++) {
                int a = num[i];
                int b = num[j];
                int mx = Math.max(a, b);
                int mn = Math.min(a, b);

                if(vis[mn][mx]) continue;

                vis[mn][mx] = true;
                q.offer(new Pair(mn, mx));
            }
        }

        while(!q.isEmpty()) {
            Pair cur = q.poll();
            int a = cur.a;
            int b = cur.b;
            int c = sum - (cur.a + cur.b);

            if(a == b && b == c) {
                System.out.println(1);
                return;
            }

            // a, b
            int x = Math.min(a, b);
            int y = Math.max(a, b);
            y -= x;
            x *= 2;
            if(vis[x][y]) continue;
            vis[x][y] = true;
            q.offer(new Pair(x, y));

            // b, c
            x = Math.min(b, c);
            y = Math.max(b, c);
            y -= x;
            x *= 2;
            if(vis[x][y]) continue;
            vis[x][y] = true;
            q.offer(new Pair(x, y));

            // a, c
            x = Math.min(a, c);
            y = Math.max(a, c);
            y -= x;
            x *= 2;
            if(vis[x][y]) continue;
            vis[x][y] = true;
            q.offer(new Pair(x, y));
        }

        System.out.println(0);
    }
}
