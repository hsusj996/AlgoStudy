package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution_6549_MW {

    static int n;
    static int[] h;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
                    String[] s = br.readLine().split(" ");
                    n = Integer.parseInt(s[0]);
                    if(n == 0)  break;
                    h = new int[n];
                    for(int i=0; i<n; i++) {
                        h[i] = Integer.parseInt(s[i+1]);
            }

            long ans = getMaxArea(0, n-1);

            System.out.println(ans);
        }
    }

    // [st, en]
    private static long getMaxArea(int st, int en) {

        if(st == en) {
            return h[st];
        }

        int mid = (st+en) / 2;

        long left = getMaxArea(st, mid); // 중간을 기준으로 왼쪽의 최대 넓이
        long right = getMaxArea(mid+1, en); // 중간을 기준으로 오른쪽 최대 넓이

        long mx = Math.max(left, right);

        mx = Math.max(mx, getMiddleArea(st, en)); // 중간을 포함하는 최대 넓이

        return mx;
    }

    private static long getMiddleArea(int st, int en) {

        int mid = (st + en) / 2;

        int lo = mid;
        int ho = mid;
        long height = h[mid];
        long area;
        long mx = h[mid];

        while(lo > st && ho < en) { // 좌/우 하나라도 끝에 도달할 때까지 반복

            if(h[lo-1] > h[ho+1]) {
                lo--;
                height = Math.min(height, h[lo]);
            } else {
                ho++;
                height = Math.min(height, h[ho]);
            }

            area = height * (ho - lo + 1);
            mx = Math.max(mx, area);
        }

        while(lo > st) { // 왼쪽 끝까지 도달하도록 함
            lo--;
            height = Math.min(height, h[lo]);
            area = height * (ho - lo + 1);
            mx = Math.max(mx, area);
        }

        while(ho < en) { // 오른쪽 끝까지 도달하도록 함
            ho++;
            height = Math.min(height, h[ho]);
            area = height * (ho - lo + 1);
            mx = Math.max(mx, area);
        }

        return mx;
    }
}
