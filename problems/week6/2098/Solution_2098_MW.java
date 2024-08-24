import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution_2098_MW {

    static int n;
    static int[][] w;
    static int[][] d;
    static final int INF = 1_000_000_000;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        w = new int[n+1][n+1];
        d = new int[1<<n][n+1];
        for(int i=1; i<=n; i++) {
            String[] s = br.readLine().split(" ");
            for(int j=1; j<=n; j++) {
                w[i][j] = Integer.parseInt(s[j-1]);
            }
        }

        // d[i][j] = 이미 방문한 도시들의 집합이 i이고, 현재 있는 도시가 j일 때, 방문하지 않은 나머지 도시들을 모두 방문한 뒤 출발 도시로 돌아올 때 드는 최소 비용
        // 1. 1번 도시에서 모든 도시를 순회하면서 테이블에 기록하기 (dfs + dp)
        // 2. 만약 이미 값이 있다면 더 이상 순회하지 않고 그 값을 이용

        System.out.println(dfs(1, 1));
    }

    // 이미 방문한 도시들의 집합이 visited이고 현재 cur을 방문했을 때,
    // 방문하지 않은 나머지 도시들을 모두 방문한 뒤 출발 도시로 돌아올 때 드는 최소 비용을 반환
    private static int dfs(int visited, int cur) {

        if(visited == (1<<n) - 1) { // 모든 도시를 방문했을 때 (기저)
            if(w[cur][1] == 0)  return INF;
            return w[cur][1];
        }

        if(d[visited][cur] != 0) { // 이전 방문 도시와 현재 방문한 도시에 대해 나머지 도시들을 모두 방문한 뒤 출발 도시로 돌아올 때 드는 최소 비용이 이미 구해져 있으면..
            return d[visited][cur];
        }

        d[visited][cur] = INF;
        for(int i=0; i<n; i++) { // 다음 도시를 방문
            if(w[cur][i+1] == 0)    continue; // 연결이 안되어 있으면
            if((visited & (1<<i)) != 0)  continue; // 이미 방문 했으면
            d[visited][cur] = Math.min(d[visited][cur], w[cur][i+1] + dfs(visited | (1<<i), i+1));
        }

        return d[visited][cur];
    }
}