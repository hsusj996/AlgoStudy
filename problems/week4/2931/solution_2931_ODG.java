import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static int R,C;
    static char[][] arr;
    static int[][] visited;
    static int[] mos,zag;

    static int[] dx = {0,1,0,-1};
    static int[] dy = {-1,0,1,0};

    static int[][] shapeEntrance = {
            {1,0,1,0},
            {0,1,0,1},
            {1,1,1,1},
            {0,1,1,0},
            {1,1,0,0},
            {1,0,0,1},
            {0,0,1,1}
    };

    public static void main(String[] args) throws Exception {

        init();

        Queue<int[]> q = new ArrayDeque<>();

        for(int i=0;i<4;i++) {
            int gox = mos[1] + dx[i];
            int goy = mos[0] + dy[i];
            if(!isIn(goy, gox))continue;
            if(arr[goy][gox] == 'Z')continue;
            if(arr[goy][gox] != '.') {
                int idx = pipeShapeToIdx(arr[goy][gox]);
                if(shapeEntrance[idx][(i+2)%4] == 1) {
                    q.add(new int[] {goy,gox});
                    visited[goy][gox] =1;
                }
            }
        }

        visited[mos[0]][mos[1]] =1;

        int[] stolenPipePos = new int[2];


        while(!q.isEmpty()) {

            // 훔친 파이프 위치인 경우
            int[] curr = q.poll();
            if(arr[curr[0]][curr[1]] == '.') {
                stolenPipePos = new int[] {curr[0],curr[1]};
                break;
            }

            // BFS
            int idx = pipeShapeToIdx(arr[curr[0]][curr[1]]);
            if(idx == -1) {
                System.out.println(123);
            }

            for(int i=0;i<4;i++) {
                if(shapeEntrance[idx][i] == 0)continue;

                int gox = curr[1] + dx[i];
                int goy = curr[0] + dy[i];
                if(isIn(goy, gox) && visited[goy][gox] == 0) {
                    visited[goy][gox] = 1;
                    q.add(new int[] {goy,gox});
                }
            }
        }

        int[] check = new int[4];
        for(int i=0;i<4;i++) {
            int gox = stolenPipePos[1] + dx[i];
            int goy = stolenPipePos[0] + dy[i];
            if(isIn(goy, gox) && arr[goy][gox] != '.') {
                if(arr[goy][gox] =='M' || arr[goy][gox] == 'Z')continue;
                int idx = pipeShapeToIdx(arr[goy][gox]);
                if(shapeEntrance[idx][(i+2)%4] == 1) {
                    check[i] = 1;
                }
            }
        }

        for(int i=0;i<shapeEntrance.length;i++) {

            boolean ok = true;
            for(int j=0;j<4;j++) {
                if(shapeEntrance[i][j] != check[j])ok = false;
            }

            if(ok) {
                System.out.println((stolenPipePos[0]+1) +" "+(stolenPipePos[1]+1) + " " + idxToPipeShape(i));
                break;
            }
        }

    }

    static boolean isIn(int goy,int gox) {
        if(gox < 0 || gox >= C || goy < 0 || goy >= R) return false;
        return true;
    }

    static void init() throws Exception{
        StringTokenizer st = new StringTokenizer(in.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        arr = new char[R][C];
        visited = new int[R][C];
        for(int i=0;i<R;i++) {
            String tmp = in.readLine();
            for(int j=0;j<C;j++) {
                arr[i][j] = tmp.charAt(j);
                if(arr[i][j] == 'M') {
                    mos = new int[]{i,j};
                }
                else if(arr[i][j] == 'Z') {
                    zag = new int[] {i,j};
                }
            }
        }
    }

    static int pipeShapeToIdx(char c) {
        if(c == '|')return 0;
        if(c == '-')return 1;
        if(c == '+')return 2;
        if(c == '1')return 3;
        if(c == '2')return 4;
        if(c == '3')return 5;
        if(c == '4')return 6;
        else return -1;
    }

    static char idxToPipeShape(int idx) {
        if(idx == 0)return '|';
        if(idx == 1)return '-';
        if(idx == 2)return '+';
        if(idx == 3)return '1';
        if(idx == 4)return '2';
        if(idx == 5)return '3';
        if(idx == 6)return '4';
        else return 0;
    }


}