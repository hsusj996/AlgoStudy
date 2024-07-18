package baekjoon;

import java.util.*;
import java.io.*;

public class prob11404 {
  static class Bus {
    int start;
    int dest;
    int cost;

    public Bus(int start, int dest, int cost) {
      this.start = start;
      this.dest = dest;
      this.cost = cost;
    }
  }

  static final int INF = 100000000;
  static int N;
  static int M;
  static int[][] min_cost;
  static Bus[] bus_list;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    N = Integer.parseInt(br.readLine());
    M = Integer.parseInt(br.readLine());
    min_cost = new int[N + 1][N + 1];
    bus_list = new Bus[M];

    for (int i = 0; i < M; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      bus_list[i] = new Bus(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
          Integer.parseInt(st.nextToken()));
    }

    FloydInit();

    FloydWarshall();

    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        if (min_cost[i][j] == INF) {
          System.out.print("0 ");
        } else {
          System.out.print(min_cost[i][j] + " ");
        }
      }
      System.out.println();
    }
  }

  static void FloydInit() {
    for (int i = 1; i <= N; i++) {
      Arrays.fill(min_cost[i], INF);
    }

    for (int i = 0; i < M; i++) {
      int start = bus_list[i].start;
      int dest = bus_list[i].dest;
      int cost = bus_list[i].cost;

      min_cost[start][dest] = Math.min(min_cost[start][dest], cost);
    }

    for (int i = 1; i <= N; i++) {
      min_cost[i][i] = 0;
    }
  }

  static void FloydWarshall() {
    for (int round = 1; round <= N; round++) {
      for (int i = 1; i <= N; i++) {
        for (int j = 1; j <= N; j++) {
          min_cost[i][j] = Math.min(min_cost[i][j], min_cost[i][round] + min_cost[round][j]);
        }
      }
    }
  }
}
