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
