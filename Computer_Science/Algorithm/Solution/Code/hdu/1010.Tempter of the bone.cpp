#include <iostream>
#include <cstring>
#include <cstdio>
using namespace std;
const int N = 8;
int n, m, t;
bool flag = 0;
char map[N][N] = {0};
int visited[N][N] = {0};
int a, b, c, d; // 记录起点和终点
int dir[4][2] = {{0,1},{0,-1},{1,0},{-1,0}};

#define CHECK(x,y) (x >=0 && x <= n-1 && y >= 0 && y <= m-1)

void dfs(int x, int y, int time) {
    if (flag) return;
    int distance = abs(c-x) + abs(d-y);
    if (map[x][y] == 'D') {
        if (time == t) flag = 1;
        return;
        }
    // 剪枝, 当前位置到终点位置的最短距离大于t - time, 则直接返回, 这种情况不可取
    if (distance > t - time) return;
    for (int i = 0; i < 4; i++){
        int nx = x + dir[i][0]; int ny = y + dir[i][1];
        if (CHECK(nx, ny) && !visited[nx][ny]) {
            visited[nx][ny] = 1;
            dfs(nx, ny, time+1);
            visited[nx][ny] = 0;
        }
    }
    return;
}

int main() {
    // freopen("input.txt", "r", stdin);
    while(~scanf("%d %d %d",&n,&m,&t)) {
        if (n == 0 && m == 0 && t == 0) break;
        memset(visited, 0, sizeof(visited));
        for (int i = 0; i < n; i++) {
            for (int j= 0; j < m; j++){
                cin>>map[i][j];
                if (map[i][j] == 'S') a = i, b = j;
                if (map[i][j] == 'D') c = i, d = j;
                if (map[i][j] == 'X') visited[i][j] = 1;
            }
        }
        int tmp =  t - abs(c-a) - abs(d-b);
        if (tmp % 2) {puts("NO"); continue;}
        visited[a][b] = 1;
        flag = 0;
        dfs(a, b, 0);
        if(flag) puts("YES");
        else puts("NO");
    }
    return 0;
}