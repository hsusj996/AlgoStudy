import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_3108_MW {

    public static class Rect {
        int x1, y1, x2, y2;

        public Rect(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    public static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int n;
    static Rect[] rects;
    static int[] p;
    static Point[] point = new Point[4];
    static boolean[] vis;
    public static void main(String[] args) throws IOException {

        // 1. 직사각형을 2개씩 뽑아서 서로 교점이 있으면 union
        // 2. 모두 확인한 후, 유일한 부모의 개수 세기

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        p = new int[n+1];
        vis = new boolean[n+1];
        rects = new Rect[n];
        for(int i=0; i<n; i++) {
            String[] s = br.readLine().split(" ");
            int x1 = Integer.parseInt(s[0]);
            int y1 = Integer.parseInt(s[1]);
            int x2 = Integer.parseInt(s[2]);
            int y2 = Integer.parseInt(s[3]);
            rects[i] = new Rect(x1, y1, x2, y2);
        }

        make_set();

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(i == j)  continue;
                if(check(i, j)) {
                    union(i+1, j+1);
                }
            }
        }

        for(int i=1; i<=n; i++) {
            int root = find(i);
            vis[root] = true;
        }

        int cnt = 0;
        for(int i=1; i<=n; i++) {
            if(vis[i])  {
                cnt++;
            }
        }

        if(cnt > 0) {
            for(int i=0; i<n; i++) {
                if(((rects[i].x1 <= 0 && rects[i].x2 >= 0 && (rects[i].y1 == 0 || rects[i].y2 == 0)) || (rects[i].y1 <= 0 && rects[i].y2 >= 0) && (rects[i].x1 == 0 || rects[i].x2 == 0))) {
                    cnt--;
                    break;
                }
            }
        }
        System.out.println(cnt);
    }

    /*
    교점이 생긴다면 true
    교점이 생기지 않는다면 false
     */
    public static boolean check(int a, int b) {
        Rect rect = rects[a];
        Rect cmp = rects[b];

        point[0] = new Point(cmp.x1, cmp.y1); // 왼쪽 위점
        point[1] = new Point(cmp.x2, cmp.y1); // 오른쪽 위점
        point[2] = new Point(cmp.x2, cmp.y2); // 오른쪽 아래점
        point[3] = new Point(cmp.x1, cmp.y2); // 왼쪽 아래점

        // 사각형이 위쪽
        if(point[3].y < rect.y1)    return false;

        // 사각형이 왼쪽
        if(point[1].x < rect.x1)    return false;

        // 사각형이 오른쪽
        if(point[0].x > rect.x2)    return false;

        // 사각형이 아래쪽
        if(point[0].y > rect.y2)    return false;

        // 사각형이 안쪽
        if(point[0].y > rect.y1 && point[0].x > rect.x1 && point[2].y < rect.y2 && point[2].x < rect.x2)    return false;

        // 사각형이 바깥쪽
        if(point[0].y < rect.y1 && point[0].x < rect.x1 && point[2].y > rect.y2 && point[2].x > rect.x2)    return false;

        return true;
    }

    public static void make_set() {
        for(int i=1; i<=n; i++) {
            p[i] = i;
        }
    }

    public static int find(int a) {
        if(p[a] == a)   return a;
        return p[a] = find(p[a]);
    }

    public static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if(rootA == rootB)  return;

        p[rootB] = rootA;
    }
}
