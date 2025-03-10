/*
 * @lc app=leetcode.cn id=64 lang=cpp
 *
 * [64] 最小路径和
 */

#include <vector>
#include <iostream>
using namespace std;
// @lc code=start
class Solution {
public:

    // vector<vector<int>> memo;
    // int dfs(vector<vector<int>>& grid, int x, int y) {
    //     if (x < 0 && y < 0) return 0;
        
    //     int& res = memo[x][y];
    //     if (res != -1) return res;
    //     res = 0;
    //     int left, up;
    //     if (x - 1 >= 0) left = dfs(grid, x - 1, y);
    //     if (y - 1 >= 0) up = dfs(grid, x, y - 1);
    //     res = min(up , left) + grid[x][y];
    //     return res;
    // }
    vector<vector<int>> f;

    int minPathSum(vector<vector<int>>& grid) {
        int m = grid.size();
        int n = grid[0].size();
        f.resize(m+1, vector<int>(n+1, 0));
        f[0][0] = grid[0][0];
        f[0][1] = f[0][0] + grid[0][1];
        f[1][0] = f[0][0] + grid[1][0];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                f[i+1][j+1] = min(f[i][j+1], f[i+1][j]) + grid[i][j];
            }
        }
        // memo.resize(m, vector<int>(n, -1));
        // return dfs(grid, m-1, n-1);
    }
};
// @lc code=end

