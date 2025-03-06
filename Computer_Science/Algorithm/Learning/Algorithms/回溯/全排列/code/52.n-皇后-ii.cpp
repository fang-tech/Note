/*
 * @lc app=leetcode.cn id=52 lang=cpp
 *
 * [52] N 皇后 II
 */

// @lc code=start
class Solution {
public:
    int ans;
    bool on_path[10];
    bool diag1[20];
    bool diag2[20];
    
    void dfs(int n, int i) {
        if (i == n) {
            ans++;
            return;
        }

    // i : row , j : col
        for (int j = 0; j < n; j++) {
            if (!on_path[j] && !diag1[j + i] && !diag2[j - i + n - 1]) {
                on_path[j] = true;
                diag1[j + i] = true;
                diag2[j - i + n - 1] = true;
                dfs(n, i + 1);
                on_path[j] = false;
                diag1[j + i] = false;
                diag2[j - i + n - 1] = false;
            }
        }
    }

    int totalNQueens(int n) {
        dfs(n, 0);
        return ans;
    }
};
// @lc code=end

