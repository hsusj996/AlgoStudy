package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Solution_2931_MW {

    public static class Pair {
        int y, x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    public static class Block {
        int y, x, entranceDir, exitDir;

        public Block(int y, int x, int entranceDir, int exitDir) {
            this.y = y;
            this.x = x;
            this.entranceDir = entranceDir;
            this.exitDir = exitDir;
        }
    }

    static int r, c;
    static char[][] board;
    static Pair M;
    static Pair Z;
    static char[] blocks = {'|', '-', '+', '1', '2', '3', '4'};
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};
    static List<Pair> plusList = new ArrayList<>();
    static boolean[][][] plusValid;
    public static void main(String[] args) throws Exception {

        // 임의의 빈칸을 선택.
        // 해당 빈칸에 블록을 채움.
        // M->Z로 가스가 흐를 수 있는지 확인
        // 625C1 * 8 * 625

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");

        r = Integer.parseInt(s[0]);
        c = Integer.parseInt(s[1]);
        board = new char[r][c];
        for(int i=0; i<r; i++) {
            String line = br.readLine();
            for(int j=0; j<c; j++) {
                board[i][j] = line.charAt(j);
                if(board[i][j] == 'M') {
                    M = new Pair(i, j);
                } else if(board[i][j] == 'Z') {
                    Z = new Pair(i, j);
                } else if(board[i][j] == '+') {
                    plusList.add(new Pair(i, j));
                }
            }
        }

        // 1. 임의의 빈칸을 선택

        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                if(board[i][j] != '.')  continue;
                for(int k=0; k<7; k++) {
                    board[i][j] = blocks[k]; // 2. 빈칸을 블록으로 채움

                    if((i == M.y -1 && j == M.x) || (i == M.y + 1 && j == M.x) || (i == M.y && j == M.x - 1) || (i == M.y && j == M.x + 1)) {
                        int connectBlock = 0;
                        for(int dir=0; dir<4; dir++) {
                            int ny = M.y + dy[dir];
                            int nx = M.x + dx[dir];
                            if(ny < 0 || ny >= r || nx < 0 || nx >= c)  continue;
                            if(move(ny, nx, dir) != null) connectBlock++;
                        }
                        if(connectBlock > 1) {
                            board[i][j] = '.';
                            continue;
                        }
                    }

                    plusValid = new boolean[r][c][2];
                    if(bfs()) {
                        System.out.println((i+1) + " " + (j+1) + " " + board[i][j]);
                        return;
                    }
                }
                board[i][j] = '.';
            }
        }
    }

    // 3. M->Z로 가스가 흐를 수 있는지 확인
    public static boolean bfs() {

        Queue<Block> q = new ArrayDeque<>();
        for(int dir=0; dir<4; dir++) {
            int ny = M.y + dy[dir];
            int nx = M.x + dx[dir];
            if(ny < 0 || ny >= r || nx < 0 || nx >= c)  continue;
            if(board[ny][nx] == 'Z' || board[ny][nx] == '.')    continue;
            Block block = move(ny, nx, dir);
            // System.out.println(ny + " " +  nx + " " + board[ny][nx]);
            if(block == null)   continue;
            q.offer(block);
        }

        while(!q.isEmpty()) {

            Block cur = q.poll();
            int exitDir = cur.exitDir;
            int ny = cur.y + dy[exitDir];
            int nx = cur.x + dx[exitDir];
//            System.out.println(cur.y + " " + cur.x);
            if(ny < 0 || ny >= r || nx < 0 || nx >= c)  continue;
            if(board[ny][nx] == 'Z') {
                for(Pair p : plusList) {
                    if(!plusValid[p.y][p.x][0] || !plusValid[p.y][p.x][1]) {
                        return false;
                    }
                }
                return true;
            }
            if(board[ny][nx] == '.')    continue;
            Block nxt = move(ny, nx, exitDir);
            if(nxt == null) continue;
            q.offer(nxt);
        }
        return false;
    }

    // 들어오는 방향과 모양을 알면 나가는 방향을 알 수 있음
    public static Block move(int y, int x, int dir) {
        Block block = null;

        if(board[y][x] == '|') {
            if(dir == 0) {
                block = new Block(y, x, 0, 0);
            } else if(dir == 2) {
                block = new Block(y, x, 2, 2);
            }
        } else if(board[y][x] == '-') {
            if(dir == 1) {
                block = new Block(y, x, 1, 1);
            } else if(dir == 3) {
                block = new Block(y, x, 3, 3);
            }
        } else if(board[y][x] == '+') {
            if(dir == 0) {
                block = new Block(y, x, 0, 0);
                plusValid[y][x][0] = true;
            } else if(dir == 1) {
                block = new Block(y, x, 1, 1);
                plusValid[y][x][1] = true;
            } else if (dir == 2) {
                block = new Block(y, x, 2, 2);
                plusValid[y][x][0] = true;
            } else if (dir == 3) {
                block = new Block(y, x, 3, 3);
                plusValid[y][x][1] = true;
            }
        } else if(board[y][x] == '1') {
            if(dir == 0) {
                block = new Block(y, x, 0, 3);
            } else if(dir == 1) {
                block = new Block(y, x, 1, 2);
            }
        } else if(board[y][x] == '2') {
            if(dir == 2) {
                block = new Block(y, x, 2, 3);
            } else if(dir == 1) {
                block = new Block(y, x, 1, 0);
            }
        } else if(board[y][x] == '3') {
            if(dir == 3) {
                block = new Block(y, x, 3, 0);
            } else if(dir == 2) {
                block = new Block(y, x, 2, 1);
            }
        } else if(board[y][x] == '4') {
            if(dir == 3) {
                block = new Block(y, x, 3, 2);
            } else if(dir == 0) {
                block = new Block(y, x, 0, 1);
            }
        }
        return block;
    }
}
