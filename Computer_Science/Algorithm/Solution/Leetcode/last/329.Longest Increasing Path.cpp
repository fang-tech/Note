/*
 * @lc app=leetcode.cn id=329 lang=cpp
 *
 * [329] 矩阵中的最长递增路径
 */
#include <vector>
#include <iostream>
using namespace std;
// @lc code=start
const int N = 205;
int dp[N][M]; // <i,j>的点出发的最长递增路径的长度
int d[4][2] = {{0, 1}, {0, -1}, {1,0}, {-1, 0}}; //四个方向
int n, m;
#define CHECK(x, y) ((x >= 0 && x <n) && (y >= 0 && y < m))
class Solution {
private:
    void dfs(int x, int y, vector<vector<int>> matrix) {
        if (dp[x][y]) return;
        for (int i=0;i<4;i++) {
            int nx = x + d[i][0]; int ny = y + d[i][1];
            if (CHECK(x,y) && !dp[x][y]) {
                if (matrix[x][y] < matrix[nx][ny]) {
                    dp[nx][ny] = dp[x][y] + 1;
                    dfs(nx, ny, matrix);
                }
            }
        }
    }
public:
    int longestIncreasingPath(vector<vector<int>>& matrix) {
        n = matrix.size();
        m = matrix[0].size();
        dfs()
    }
};
// @lc code=end

