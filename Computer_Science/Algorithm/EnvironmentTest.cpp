#include <iostream>
#include <cstdio>
using namespace std;
char mat[8][8];
int visited[8][8];
int flag;
int a,b,c,d;
int dir[4][2] = {{0,1},{0,-1},{1,0},{-1,0}};
int n, m, t;
#define CHECK(x,y) (x >=0 && x <= n-1 && y >= 0 && y <= m-1)

void dfs(int x, int y, int time) {
    if (flag) return;
    if (map[x][y] == 'D') {
        if (time == t) flag = 1;
        return;
    }
    int tmp = t - time - abs(c-x) - abs(d-y);
    if (tmp < 0) return;

    for (int i = 0; i < 4; i++) {
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
    while (~scanf(%d %d %d)) {
        if (n == 0 && m == 0 && t == 0) break;
        memset(visited, 0, sizeof(visited));
        for (int i = 0; i < n ;i++){
            for (int j = 0; j < m; j++){
                cin>>mat[i][j];
                if (mat[i][j] == 'S') a=i, b=j;
                if (mat[i][j] == 'D') c=i, d=j;
                if (mat[i][j] == 'X') visited[i][j] = 1;
            }
        }
        int tmp = t - abs(c-a) - abs(d-b);
        if (tmp & 1) {puts("NO"); continue;}
        flag = 0;
        visited[a][b] = 1;
        dfs(a,b,0);
        if (flag)   puts("YES");
        else        puts("NO");
    }

    return 0;
}