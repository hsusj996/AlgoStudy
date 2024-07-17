import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static int W, H;
    static char[][] arr;
    static int[][][] visited;
    static int[] dx = { 0, 0, 1, -1 };
    static int[] dy = { 1, -1, 0, 0 };
    static PriorityQueue<Item> pq;
    static List<int[]> trashes;

    public static void main(String[] args) throws Exception {

        while (true) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());
            if (W == 0 && H == 0) break;


            // 쓰레기가 1개면 [2] -> [0]하고 [1]만 존재.
            // 쓰레기가 2개면 [4] -> [00][01][10][11] 존재.
            // 모든 쓰레기 방문 시 (1<<trashes.size())-1
            visited = new int[H][W][1 << trashes.size()];

            boolean isFind = false;
            // 다익스트라 돌린다.
            while (!pq.isEmpty()) {

                Item curr = pq.poll();

                if(visited[curr.y][curr.x][curr.bit]==1)continue;

                visited[curr.y][curr.x][curr.bit] = 1;

                if (curr.bit == (1 << trashes.size()) - 1) {
                    System.out.println(curr.moveCount);
                    isFind = true;
                    break;
                }

                for (int i = 0; i < 4; i++) {
                    int gox = dx[i] + curr.x;
                    int goy = dy[i] + curr.y;
                    if(isIn(goy, gox) && visited[goy][gox][curr.bit]==0) {
                        if(arr[goy][gox] >= '0' && arr[goy][gox] <= '9') {
                            pq.add(new Item(goy,gox,(curr.bit | (1 << arr[goy][gox]-'0')),curr.moveCount+1));
                        }
                        else {
                            pq.add(new Item(goy,gox,curr.bit,curr.moveCount+1));
                        }
                    }
                }

            }

            if (!isFind) {
                System.out.println("-1");
            }
        }
    }

    static boolean isIn(int goy, int gox) {
        if (gox < 0 || gox >= W || goy < 0 || goy >= H) return false;
        if(arr[goy][gox] == 'x')return false;
        return true;
    }

    static class Item implements Comparable<Item> {
        int y, x, bit, moveCount;

        public Item(int y, int x, int bit, int moveCount) {
            this.y = y;
            this.x = x;
            this.bit = bit;
            this.moveCount = moveCount;
        }

        public int compareTo(Item o) {
            return this.moveCount - o.moveCount;
        }
    }

    static void init() throws Exception{
        arr = new char[H][W];
        pq = new PriorityQueue<>();
        trashes = new ArrayList<>();

        int cnt = 0;
        for (int i = 0; i < H; i++) {
            String tmp = in.readLine();
            for (int j = 0; j < W; j++) {
                arr[i][j] = tmp.charAt(j);
                if (arr[i][j] == 'o') {
                    pq.add(new Item(i, j, 0, 0));
                } else if (arr[i][j] == '*') {
                    arr[i][j] = (char)('0'+cnt);
                    cnt++;
                    trashes.add(new int[] { i, j });
                }
            }
        }
    }
}