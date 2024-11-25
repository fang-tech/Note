#include <iostream>
#include <cstring>
using namespace std;
const int N = 1e5 + 5;
int n, m, t;
bool flag = 0;
char map[N][N] = {0};
int visited[N][N] = {0};
int a, b, c, d; // 记录起点和终点
int dir[][] = {{0,1},{0,-1},{1,0},{-1,0}};

#define CHECK(x,y) (x >=0 && x <= n-1 && y >= 0 && y <= m-1)

void dfs(int x, int y, int time) {
    if (flag) return;
    // 剪枝, 当前位置到终点位置的最短距离大于t - time, 则直接返回, 这种情况不可取
    int distance = abs(c-x) + abs(d-y);
    if (distance == 0) {flag = 1; return;}
    if (distance > t - time) return;
    for (int i = 0; i < 4; i++){
        int nx = x + dir[i][0]; int ny = y + dir[0][i];
        if (CHECK(nx, ny)) {
            visited[nx][ny] = 1;
            dfs(nx, ny, time+1);
            visited[nx][ny] = 0;
        }
    }
}

int main() {
    freopen("input.txt", "r", stdin);
    while(!scanf("%d %d %d",&n,&m,&t)) {
        if (n == 0 && m == 0 && t == 0) break;
        for (int i = 0; i < n; i++) {
            for (int j= 0; j < m; j++){
                cin>>map[i][j];
                if (map[i][j] == 'S') a = i, b =j;
                if (map[i][j] == 'D') c = i, d = j;
            }
        }
        int tmp =  t - abs(c-a) - abs(d-b);
        if (tmp % 1) {puts("NO"); continue;}
        memset(visited, 0, sizeof(visited));
        visited[a][b] = 1;
        dfs(a, b, 0);
        if(flag) puts("YES");
        else puts("NO");
    }
    return 0;
}