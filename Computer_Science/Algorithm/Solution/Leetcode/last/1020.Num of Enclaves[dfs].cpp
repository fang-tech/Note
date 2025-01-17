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
