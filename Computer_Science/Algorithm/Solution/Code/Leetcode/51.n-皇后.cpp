/*
 * @lc app=leetcode.cn id=51 lang=cpp
 *
 * [51] N 皇后
 */

#include <string>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<vector<string>> ans;
    vector<int> col; // 全排列数组
    bool diag1[20]; // 记录所有已经出现过的r+c, 用于判断是不是在上斜对角线
    bool diag2[20]; // 记录所有已经出现过的r-c, 用于判断是不是在下斜对角线, 
                    // 因为会出现负数, 比如r = 1, c = 2, 同时需要将正数和负数区别开来, 所以计算出来的 + n - 1
    
    bool on_path[10]; // 记录这个数字有没有被选过

    void dfs(int n, int i) {
        if (i == n) {
            // 添加答案
            vector<string> path;
            for (int r = 0; r < n; r++){ // 第r行
                string s;
                // 添加.
                for (int i = 0; i < col[r]; i++) {
                    s += '.';
                }
                // 添加上Q
                s += 'Q';
                // 继续添加.
                for (int i = 0; i < n - col[r] - 1; i++) {
                    s += '.';
                }
                path.emplace_back(s);
            }
            ans.emplace_back(path);
            return;
        }

    // 从n个数中, 选下一个数字
    for (int j = 0; j < n; j++) {
        // 没有被选过才能选, 并且对角线上没有元素
        if (!on_path[j] && !diag2[i-j+n-1] && !diag1[i+j]) {
            // 第i行, 第j列, 有皇后
            col[i] = j;
            on_path[j] = true; // 第j个数字我们已经选过了
            diag2[i-j+n-1] = true; diag1[i+j] = true; // 记录对角线已经选过了
            dfs(n, i+1);
            on_path[j] = false; // 恢复现场
            diag2[i-j+n-1] = false; diag1[i+j] = false; // 记录对角线已经选过了
        }
    }
    }

    vector<vector<string>> solveNQueens(int n) {
        col.resize(n);
        dfs(n, 0);
        return ans;
    }
};
// @lc code=end

