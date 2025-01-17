/*
 * @lc app=leetcode.cn id=329 lang=cpp
 *
 * [329] 矩阵中的最长递增路径
 */
#include <vector>
#include <iostream>
using namespace std;
// @lc code=start
using namespace std;
const int N = 205;
int dp[N][N]; // <i,j>的点出发的最长递增路径的长度
int d[4][2] = {{0, 1}, {0, -1}, {1,0}, {-1, 0}}; //四个方向
int n, m;
#define CHECK(nx, ny) ((nx >= 0 && nx <n) && (ny >= 0 && ny < m))
class Solution {
private:
    int dfs(int x, int y, vector<vector<int>>& matrix) {

        if (dp[x][y]) return dp[x][y];
        dp[x][y] = 1;
        for (int i=0;i<4;i++) {
            int nx = x + d[i][0]; int ny = y + d[i][1];
            if ((nx >= 0 && nx < n) && (ny >= 0 && ny < m)) {
                if (matrix[nx][ny] > matrix[x][y]) {
                    // 递增
                    if (!dp[nx][ny]) dp[nx][ny] = dfs(nx, ny, matrix);
                    dp[x][y] = max(dp[x][y], dp[nx][ny] + 1);
                }
            }
        }
        return dp[x][y];
    }

public:
    int longestIncreasingPath(vector<vector<int>>& matrix) {
        memset(dp, 0, sizeof(dp));
        int _max = 0;
        n = matrix.size();
        m = matrix[0].size();
        cout << n << " " << m << endl;
        for (int i=0;i<n;i++) {
            for (int j=0;j<m;j++) {
                dfs(i, j, matrix);
            }
        }
        for (int i=0;i<n;i++) {
            for (int j=0;j<m;j++) {
                _max = max(_max, dp[i][j]);
            }
        }
        return _max;
    }
};
// @lc code=end
int main (){
    vector<vector<int>> matrix = {{1,2}};
    Solution s;
    cout << s.longestIncreasingPath(matrix);
    return 0;
}

