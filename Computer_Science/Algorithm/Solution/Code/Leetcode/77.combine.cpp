/*
 * @lc app=leetcode.cn id=77 lang=cpp
 *
 * [77] 组合
 */

#include <iostream>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    vector<int> path;
    vector<vector<int>> ans;
    /**
     * k : 剩余的数字个数
     * i : 能选的第一个数字
     */
    void dfs(int i, int n, int k){
        // 减枝优化, 如果接下来需要选的数字数量 > 还剩下的数字数量, 直接返回
        if (k > n - i + 1) return;

        if (k == 0) {
            ans.emplace_back(path);
            return;
        }

        // 从[i, n]中选一个数字出来
        for (int j = i; j <= n; j++) {
            path.push_back(j);
            dfs(j + 1, n, k - 1);
            path.pop_back(); // 恢复现场
        }
    }

    vector<vector<int>> combine(int n, int k) {
        dfs(1, n, k);
        return ans;
    }
};
// @lc code=end
int main() {
    Solution s;
    s.combine(4,2);
    return 0;
}

