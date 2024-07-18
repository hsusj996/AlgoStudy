#include<iostream>
#include<queue>
#include<vector>
#include<cstring>

#define endl "\n"
#define MAX 26

using namespace std;

typedef struct {
    int x;
    int y;
    int dir;
} Gas;

int rows, cols, pipeCount, tempPipeCount;
char map[MAX][MAX];
bool visited[MAX][MAX];

int dx[] = { 0, 0, 1, -1 };
int dy[] = { 1, -1, 0, 0 };
char pipeTypes[] = { '|', '-', '+', '1', '2', '3', '4' };
bool pipeUsed[8];

pair<int, int> start, end;
vector<pair<int, int>> pipePositions;

void Input() {
    cin >> rows >> cols;
    for (int i = 1; i <= rows; i++) {
        for (int j = 1; j <= cols; j++) {
            cin >> map[i][j];
            if (map[i][j] == 'M') {
                start.first = i;
                start.second = j;
            }
            if (map[i][j] != 'M' && map[i][j] != 'Z' && map[i][j] != '.') {
                pipePositions.push_back(make_pair(i, j));
            }
        }
    }
}

int SetDirection(int direction, char pipeType) {
    int newDirection = -1;
    if (pipeType == '|') {
        if (direction == 2 || direction == 3) newDirection = direction;
    } else if (pipeType == '-') {
        if (direction == 0 || direction == 1) newDirection = direction;
    } else if (pipeType == '+') {
        newDirection = direction;
    } else if (pipeType == '1') {
        if (direction == 3) newDirection = 0;
        else if (direction == 1) newDirection = 2;
    } else if (pipeType == '2') {
        if (direction == 2) newDirection = 0;
        else if (direction == 1) newDirection = 3;
    } else if (pipeType == '3') {
        if (direction == 0) newDirection = 3;
        else if (direction == 2) newDirection = 1;
    } else if (pipeType == '4') {
        if (direction == 0) newDirection = 2;
        else if (direction == 3) newDirection = 1;
    }
    return newDirection;
}

bool CanUseAllPipes() {
    for (int i = 0; i < pipePositions.size(); i++) {
        int x = pipePositions[i].first;
        int y = pipePositions[i].second;
        if (visited[x][y] == false) return false;
    }
    return true;
}

pair<int, int> BFS() {
    queue<Gas> q;
    Gas startGas = { start.first, start.second, 0 };
    q.push(startGas);

    int nextX, nextY;
    bool answerFound = false;

    while (!q.empty()) {
        if (answerFound == true) break;
        int x = q.front().x;
        int y = q.front().y;
        int dir = q.front().dir;
        q.pop();

        if (map[x][y] == 'M') {
            for (int i = 0; i < 4; i++) {
                nextX = x + dx[i];
                nextY = y + dy[i];

                if (nextX >= 1 && nextY >= 1 && nextX <= rows && nextY <= cols) {
                    if (map[nextX][nextY] == 'Z') continue;
                    if (map[nextX][nextY] != '.') {
                        dir = SetDirection(i, map[nextX][nextY]);
                        if (dir != -1) {
                            q.push({ nextX, nextY, dir });
                            if (visited[nextX][nextY] == false) visited[nextX][nextY] = true;
                        }
                    }
                }
            }

            if (q.empty()) {
                for (int i = 0; i < 4; i++) {
                    memset(visited, false, sizeof(visited));
                    if (answerFound == true) break;
                    nextX = x + dx[i];
                    nextY = y + dy[i];

                    if (nextX >= 1 && nextY >= 1 && nextX <= rows && nextY <= cols) {
                        for (int j = 0; j < 7; j++) {
                            memset(visited, false, sizeof(visited));
                            map[nextX][nextY] = pipeTypes[j];
                            pair<int, int> temp;
                            temp = BFS();
                            if (temp.first == -1 && temp.second == -1) {
                                if (CanUseAllPipes() == true) {
                                    answerFound = true;
                                    cout << x << " " << y << " " << pipeTypes[j] << endl;
                                    exit(0);
                                }
                            }
                            map[nextX][nextY] = '.';
                        }
                    }
                }
            }
        } else {
            nextX = x + dx[dir];
            nextY = y + dy[dir];

            if (nextX >= 1 && nextY >= 1 && nextX <= rows && nextY <= cols) {
                if (map[nextX][nextY] == '.') return { nextX, nextY };
                if (map[nextX][nextY] == 'Z') return { -1, -1 };

                dir = SetDirection(dir, map[nextX][nextY]);
                if (dir == -1) return { nextX ,nextY };
                if (visited[nextX][nextY] == false) visited[nextX][nextY] = true;
                q.push({ nextX, nextY, dir });
            }
        }
    }
    return { -1, -1 };
}

void Solution() {
    pair<int, int> before, after;
    before = BFS();
    int x = before.first;
    int y = before.second;

    for (int i = 0; i < 7; i++) {
        map[x][y] = pipeTypes[i];
        memset(visited, false, sizeof(visited));
        after = BFS();
        if (after.first == -1 && after.second == -1) {
            if (CanUseAllPipes() == true) {
                cout << x << " " << y << " " << pipeTypes[i] << endl;
                return;
            }
        }
    }
}

void Solve() {
    Input();
    Solution();
}

int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    Solve();

    return 0;
}
