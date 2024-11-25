# P1020. [飞地的数量](https://leetcode.cn/problems/number-of-enclaves/description/)

- 一句话题解 : 这是一道洪水填充(连通性判断)的问题, 可以通过找到需要记录的点后, 再查找它四周的节点, 如果满足需求则再继续搜索, 这里因为是在leetcode中的写的, 额外需要注意边界问题

## DFS : 深度优先搜索处理

### 代码的结构

```cpp
int ans = 0; // 答案
int counter = 0 ; // 用于计数
int flag = 1; // 用于判断是不是有效的数据
int visited[505][505] = {0};
int d[4][2] = {{0,1},{0,-1}, {1,0}, {-1,0}};
void dfs(vector<vector<int>> grid, int x, int y){
    // 只有"1"节点会进入, 不需要判断是不是"1"节点
    counter++;
    visited[x][y] = 1;
    if (题目的条件) // 即什么时候这块地会计入/ 不计入
        flag = 0;
    for (下一层的所有情况)
		dfs();
}
int func(){
    // 矩阵是m*n的
    for (int i : m) {
        for (int j : n) {
        	if (grid[i][j] == 1 && !visited) {
                dfs();
                if (flag == 0)
                    flag = 1;
                else 
                    num += counter
                counter = 0;
            } 
        }
    }
}
```

### code

```cpp
/*
 * @lc app=leetcode.cn id=1020 lang=cpp
 *
 * [1020] 飞地的数量
 */
#include <vector>
#include <iostream>
using namespace std;

// @lc code=start
class Solution {
private:

    int visited[505][505] = {0};
    int counter = 0;
    int flag = 1;
    int d[4][2] = {{0,1}, {0,-1}, {1,0}, {-1,0}};

    void dfs(vector<vector<int>>& grid, int x, int y){
        printf("x : %d, y : %d, grid = %d \n", x, y, grid[x][y]);
        int m = grid.size(); int n = grid[0].size();
        this->counter++;
        visited[x][y] = 1;
        if (x == 0 || y == 0 || x == m-1 || y == n-1) {
            flag = 0;
            printf("happened clear !! x : %d, y : %d, grid = %d\n", x, y, grid[x][y]);
        }
        for (int i = 0; i < 4; i++) {
            int nx = x + d[i][0]; int ny = y + d[i][1];
            nx = max(0, nx); ny = max(0, ny);
            nx = min((m-1), nx); ny = min((n-1), ny);
            if (!visited[nx][ny] && grid[nx][ny] == 1){
                dfs(grid, nx, ny);
            }
        }
    }
public:
    int numEnclaves(vector<vector<int>>& grid) {
        int num = 0;
        int m = grid.size(); int n = grid[0].size();
        if (m <= 2 || n <= 2) return 0;
        for (int i = 1; i < m-1; i++) {
            for (int j = 1; j < n-1; j++) {
                if (!visited[i][j] && grid[i][j] == 1) {
                    dfs(grid, i, j);
                    if (flag == 0) {
                        counter = 0;
                        flag = 1;
                    } else {
                        num += counter;
                        counter = 0;
                        cout << "num :" << num << endl;
                    }
                }
            }
        }
        return num;
    }
};
// @lc code=end

int main() {
    Solution s;
    cout << "case 1 :" << endl;
    vector<vector<int>> grid = {
                {0,0,0,1,1,1,0,1,0,0},
                {1,1,0,0,0,1,0,1,1,1},
                {0,0,0,1,1,1,0,1,0,0},
                {0,1,1,0,0,0,1,0,1,0},
                {0,1,1,1,1,1,0,0,1,0},
                {0,0,1,0,1,1,1,1,0,1},
                {0,1,1,0,0,0,1,1,1,1},
                {0,0,1,0,0,1,0,1,0,1},
                {1,0,1,0,1,1,0,0,0,0},
                {0,0,0,0,1,1,0,0,0,1}};
    cout << s.numEnclaves(grid) << endl;
}
```

## BFS : 广度优先搜索

### 代码框架

```cpp
int bfs(vector<vector<int>> grid, int x, int y, int& val) {
    pair<int, int> p(x,y);
    queue<pair<int,int>> q;
    visited[x][y] = 1;
    q.push(p);
    while(!q.empty()) {
        queue<pair<int,int>> temp = q.front();
        if (nx == 0 || ny == 0 || nx == m-1 || ny == n-1) {
            flag = 0;
        q.pop();
        int x1 = temp.first; int y1 = temp.second;
       	for (int i = 0; i < 4; i++) {
            int nx = x1 + d[i][0]; int ny = y1 + d[i][1];
			nx = max(0, nx); ny = max(0, ny);
            nx = min((m-1), nx); ny = min((n-1), ny);
            q.push(newNode);
            visited = 1;
        }
    }
}
```

### code

```cpp
/*
 * @lc app=leetcode.cn id=1020 lang=cpp
 *
 * [1020] 飞地的数量
 */
#include <vector>
#include <iostream>
#include <queue>
#include <utility>
using namespace std;

// @lc code=start
class Solution {
private:

    int visited[505][505] = {0};
    int counter = 0;
    int flag = 1;
    int d[4][2] = {{0,1}, {0,-1}, {1,0}, {-1,0}};

    void bfs(vector<vector<int>>& grid, int x, int y) {
        int m = grid.size(); int n = grid[0].size();
        pair<int, int> cur(x,y);
        queue<pair<int, int>> que;
        que.push(cur);
        while(!que.empty()) {
            cur = que.front();
            visited[cur.first][cur.second] = 1;
            if (cur.first == 0 || cur.first == m-1 || cur.second == 0 || cur.second == n-1) {
                flag = 0;
                printf("Happened Clear : grid[%d][%d] = %d\n", cur.first, cur.second, grid[cur.first][cur.second]);
            }
            counter++;
            que.pop();
            for (int i = 0; i < 4; i++) {
                int nx = max(cur.first + d[i][0], 0); int ny = max(cur.second + d[i][1], 0);
                nx = min(nx, m-1); ny = min(ny, n-1);
                pair<int,int> p = make_pair(nx, ny);
                if (!visited[nx][ny] && grid[nx][ny] == 1) {
                    que.push(p);
                    visited[nx][ny] = 1;
                    printf("grid[%d][%d] = %d\n", nx, ny, grid[nx][ny]);
                }
            }
        }

    }
public:
    int numEnclaves(vector<vector<int>>& grid) {
        int num = 0;
        int m = grid.size(); int n = grid[0].size();
        if (m <= 2 || n <= 2) return 0;
        for (int i = 1; i < m-1; i++) {
            for (int j = 1; j < n-1; j++) {
                if (!visited[i][j] && grid[i][j] == 1) {
                    bfs(grid, i, j);
                    if (flag == 0) flag = 1;
                    else {
                        num += this->counter;
                        cout << "num :" << num << ", counter : " << this->counter << endl;
                    }
                    this->counter=0;
                }
            }
        }
        return num;
    }
};
// @lc code=end

int main() {
    Solution s;
    cout << "case 1 :" << endl;
    vector<vector<int>> grid = {
                {0,0,0,1,1,1,0,1,0,0},
                {1,1,0,0,0,1,0,1,1,1},
                {0,0,0,1,1,1,0,1,0,0},
                {0,1,1,0,0,0,1,0,1,0},
                {0,1,1,1,1,1,0,0,1,0},
                {0,0,1,0,1,1,1,1,0,1},
                {0,1,1,0,0,0,1,1,1,1},
                {0,0,1,0,0,1,0,1,0,1},
                {1,0,1,0,1,1,0,0,0,0},
                {0,0,0,0,1,1,0,0,0,1}};
    cout << s.numEnclaves(grid) << endl;
}
```



